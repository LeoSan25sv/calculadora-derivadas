package com.tucalculadora;

/**
 * Interfaz que define el contrato para todas las funciones derivables.
 *
 * @author Estudiante UCI
 */
public interface Funcion {
    /**
     * Calcula la derivada de la función.
     *
     * @return Una nueva función que es la derivada de esta
     */
    Funcion derivar();

    /**
     * Evalua en un punto
     */
    double evaluar(double x);

    /**
     * Devuelve una representación en String de la función.
     *
     * @return String que representa la función
     */
    String toString();
}