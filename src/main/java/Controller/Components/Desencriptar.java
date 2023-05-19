package Controller.Components;

import Jama.LUDecomposition;
import Jama.Matrix;

import java.util.HashMap;

public class Desencriptar {

    private boolean matrizValida;

    public Matrix inversa(double[][] matrizDouble){
        Matrix matrix = new Matrix(matrizDouble); //Objeto Matrix recibe una matriz de doubles
        Matrix inversa = new Matrix(matrizDouble);
        LUDecomposition lu = matrix.lu();

        if (lu.det() == 0) { // Verificar si la matriz es singular
            System.out.println("La matriz es singular.");
        } else {
            inversa = matrix.inverse(); //Obtiene la matriz inversa de la matriz especificada
        }

        return inversa;
    }

    public double determinante(double[][] matrizDouble){
        Matrix matrix = new Matrix(matrizDouble); //Recibe una matriz de doubles
        double determinante = matrix.det(); //Obtiene el determinante en decimal

        return determinante;
    }

    public int[][] factDeterminante(int[][] matrizEntera){
        double[][] matrizDouble = matrizDouble(matrizEntera);

        if(validez(matrizEntera)) {
            HashMap<Integer, Integer> alfabeto = inversoMultiplicativo();
            int modulo;
            int inverso;
            double determinante = determinante(matrizDouble); //Obtener el determinante
            Matrix inversa = inversa(matrizDouble); //Obtener la matriz inversa

            double[][] matrizInversa = inversa.getArray(); //Convertir la matriz inversa en una matriz de doubles

            int[][] factorizacion = new int[matrizInversa.length][matrizInversa[0].length]; //Matriz para almacenar la factoriazacion del determinante


            for (int i = 0; i < matrizInversa.length; i++) { //Realizar la factorizacion
                for (int j = 0; j < matrizInversa[i].length; j++) {
                    modulo = (int) Math.round(matrizInversa[i][j] / (1 / determinante));

                    if(modulo < 0){
                        modulo = 27 + modulo;
                    } else if(modulo > 0){
                        modulo = modulo % 27;
                    }

                    if (determinante < 0) {
                        inverso = alfabeto.get((int) Math.round(-modificarModulo(determinante))); //Inverso del determinante
                        factorizacion[i][j] = -1 * (inverso) * modulo; //Matriz 1
                    } else {
                        inverso = alfabeto.get((int) Math.round(modificarModulo(determinante)));
                        factorizacion[i][j] = (inverso) * modulo; //Matriz 1
                    }
                }

            }
            return factorizacion;
        }
        return matrizEntera;
    }

    public double[][] matrizDouble(int[][] matrizEntera) {
        double[][] matrizDouble = new double[matrizEntera.length][matrizEntera[0].length]; //Matriz double
        for (int i = 0; i < matrizEntera.length; i++) { //Castear todos los enteros en la nueva matriz
            for (int j = 0; j < matrizEntera[i].length; j++) {
                matrizDouble[i][j] = (double) matrizEntera[i][j];
            }
        }
        return matrizDouble;
    }

    public boolean validez(int[][] matrizEntera){
        Matrix matrix = new Matrix(matrizDouble(matrizEntera)); //Objeto Matrix recibe una matriz de doubles
        LUDecomposition lu = matrix.lu();
        int determinante = (int) modificarModulo(determinante(matrizDouble(matrizEntera))); ;

        if (lu.det() == 0 || determinante % 3 == 0) { // Verificar si la matriz es singular
            return false;
        } else {
            return true;
        }
    }

    public double modificarModulo(double determinante){
        int division;

        if(determinante < -26){
            division = (int) (determinante / 27);
            determinante = determinante - (27 * division);

        } else if(determinante > 26) {
            determinante =  determinante % 27;
        }

        return determinante;
    }

    public HashMap<Integer, Integer> inversoMultiplicativo(){
        HashMap<Integer, Integer> hashmapInversos = new HashMap<>();
        hashmapInversos.put(1,1);
        hashmapInversos.put(2,14);
        hashmapInversos.put(4,7);
        hashmapInversos.put(5,11);
        hashmapInversos.put(7,4);
        hashmapInversos.put(8,17);
        hashmapInversos.put(10,19);
        hashmapInversos.put(11,5);
        hashmapInversos.put(13,25);
        hashmapInversos.put(14,2);
        hashmapInversos.put(16,22);
        hashmapInversos.put(17,8);
        hashmapInversos.put(19,10);
        hashmapInversos.put(20,23);
        hashmapInversos.put(22,16);
        hashmapInversos.put(23,20);
        hashmapInversos.put(25,20);
        hashmapInversos.put(26,26);

        return hashmapInversos;
    }

    public String desencriptar(String mensaje, int[][] clave){

        Encriptar encriptar = new Encriptar();
        int[] productos = encriptar.multiplicarMatriz(mensaje, factDeterminante(clave));
        String[] palabraEncriptada = new String[productos.length];

        for (int i = 0; i < palabraEncriptada.length; i++){
            if (productos[i] < 0){
                palabraEncriptada[i] = Character.toUpperCase(encriptar.letraCorrespondiente(Math.floorMod(productos[i], 27))) + "";
            } else {
                palabraEncriptada[i] = Character.toUpperCase(encriptar.letraCorrespondiente(Math.floorMod(productos[i], 27))) + "";
            }
        }

        Encriptar encriptar1 = new Encriptar();
        return encriptar1.ordenarResultado(mensaje, clave, palabraEncriptada);
    }

}
