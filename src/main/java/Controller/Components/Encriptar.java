package Controller.Components;

import java.util.HashMap;
import java.util.Map;

public class Encriptar {
    private  boolean resultado = true;
    public HashMap<Character, Integer> alfabetoNumero() {
        HashMap<Character, Integer> hashmapAlfabeto = new HashMap<>();
        char[] alphabet = "abcdefghijklmnñopqrstuvwxyz".toCharArray();

        for (int i = 0; i < alphabet.length; i++) {
            hashmapAlfabeto.put(alphabet[i], i);
        }

        return hashmapAlfabeto;
    }

    public int[] palabraMatriz(String mensaje, int[][] clave) {
        HashMap<Character, Integer> valorNumerico = alfabetoNumero(); //Obtener el hashmap de letras a numeros
        int letraNumero;

        int[] matrizNumerica = new int[mensaje.length()]; //Definir un arreglo numerico

        for (int i = 0; i < mensaje.length(); i++) { //Obtener el valor numerico de la palabra y agregarlo a un nuevo arreglo
            letraNumero = valorNumerico.get(Character.toLowerCase(mensaje.charAt(i)));
            matrizNumerica[i] = letraNumero;
        }

        return matrizNumerica;
    }

    public int[] multiplicarMatriz(String mensaje, int[][] clave) {
        int[] matrizNumerica = palabraMatriz(mensaje, clave); //Almacena el valor numerico de la palabra
        int[] productos = new int[matrizNumerica.length]; //Almacena los productos al multiplicar la clave por la palabra
        int producto = 0;
        int recorre_columnas = 0;
        int cantProductos = 0;
        int recorre_numeros;

        for (int i = 0; i < clave.length; i++) {
            recorre_numeros = 0;

            while (recorre_numeros < matrizNumerica.length) {
                recorre_columnas = 0;
                producto = 0;
                while (recorre_columnas < clave[0].length) {
                    producto = producto + clave[i][recorre_columnas] * matrizNumerica[recorre_numeros];
                    recorre_numeros++;
                    recorre_columnas++;
                }

                productos[cantProductos] = producto;
                cantProductos++;
            }
        }

        return productos;
    }

    public char letraCorrespondiente(int value) {
        HashMap<Character, Integer> alfabeto = alfabetoNumero();
        char letra = '0';

        for (Map.Entry<Character, Integer> entry : alfabeto.entrySet()) {
            if (entry.getValue().equals(value)) {
                letra = entry.getKey();
                return letra;
            }
        }

        return letra;
    }

    public String encriptar(String mensaje, int[][] clave) {
        mensaje = mensaje.replace(" ", ""); //Eliminar todos los espacios de la palabra

        while (mensaje.length() % clave[0].length != 0) { //Completar la palabra con X
            mensaje = mensaje + "x";
        }

        int[] productos = multiplicarMatriz(mensaje, clave);
        String[] palabraEncriptada = new String[productos.length];

        for (int i = 0; i < palabraEncriptada.length; i++) {
            palabraEncriptada[i] = Character.toUpperCase(letraCorrespondiente(Math.floorMod(productos[i], 27))) + "";
        }

        return ordenarResultado(mensaje, clave, palabraEncriptada);
    }

    public String ordenarResultado(String mensaje, int[][] clave, String[] palabraEncriptada){
        String[][] mensajeOrdenado = new String[clave[0].length][mensaje.length() / clave[0].length];
        String resultado = "";

        int medidaPalabraEncriptada = 0;
        for (int j = 0; j < mensajeOrdenado.length; j++) { //Ordenar la palabra dentro de una matriz para imprimir
            for (int k = 0; k < mensajeOrdenado[0].length; k++) {
                mensajeOrdenado[j][k] = palabraEncriptada[medidaPalabraEncriptada];
                medidaPalabraEncriptada++;
            }
        }

        //Imprimir la palabra
        for (int m = 0; m < mensajeOrdenado[0].length; m++) {
            for (int n = 0; n < mensajeOrdenado.length; n++) {
                resultado = resultado + mensajeOrdenado[n][m];
            }
        }

        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public boolean getResultado() {
        return resultado;
    }
}
