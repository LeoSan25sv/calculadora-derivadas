package com.tucalculadora.obsoleto.funciones;


import com.tucalculadora.obsoleto.Funcion;

/**
 * Representa una función polinómica: f(x) = Σ a_i·x^i.
 *
 * @author Estudiante UCI
 * @version 2.1 (POO)
 */
public class Polinomica implements Funcion {
    private final Polinomio polinomio;

    /**
     * Constructor a partir de un polinomio.
     *
     * @param polinomio F que representa la función
     */
    public Polinomica(Polinomio polinomio) {
        this.polinomio = polinomio;
    }

    /**
     * Constructor a partir de array de términos.
     *
     * @param terms Array de [coeficiente, exponente]
     */
    public Polinomica(int[][] terms) {
        this.polinomio = new Polinomio(terms);
    }

    /**
     * Obtiene el polinomio subyacente.
     */
    public Polinomio getPolinomio() {
        return polinomio;
    }

    @Override
    public Funcion derivar() {
        return new Polinomica(polinomio.derivar());
    }

    @Override
    public double evaluar(double x) {
        return this.polinomio.evaluar(x);
    }

    @Override
    public String toString() {
        return polinomio.toString();
    }
}

