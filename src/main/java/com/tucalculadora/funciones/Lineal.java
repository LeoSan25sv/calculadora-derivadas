package com.tucalculadora.funciones;


import com.tucalculadora.Funcion;
import com.tucalculadora.Polinomio;

/**
 * Representa una función lineal de la forma f(x) = mx + n.
 *
 * @param m pendiente
 * @param n intersección
 * @author Estudiante UCI
 * @version 2.1 (POO)
 */
public record Lineal(int m, int n) implements Funcion {
    /**
     * Constructor de función lineal.
     *
     * @param m Pendiente
     * @param n Intersección con el eje Y
     */
    public Lineal {
    }

    /**
     * Obtiene la pendiente.
     */
    @Override
    public int m() {
        return m;
    }

    /**
     * Obtiene la intersección.
     */
    @Override
    public int n() {
        return n;
    }

    @Override
    public Funcion derivar() {
        // La derivada de mx + n es m (una constante), por tanto, la representamos como un polinomio constante
        Polinomio constante = new Polinomio();
        constante.agregarTermino(m, 0);
        return new Polinomica(constante);
    }

    @Override
    public double evaluar(double x) {
        return m * x + n;
    }

    @Override
    public String toString() {
        if (n == 0) {
            return m + "x";
        } else if (n > 0) {
            return m + "x + " + n;
        } else {
            return m + "x - " + (-n);
        }
    }
}
