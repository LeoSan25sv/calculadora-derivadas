package com.tucalculadora.derivadas;

import com.tucalculadora.Funcion;
import com.tucalculadora.Polinomio;

public class DxRacional implements Funcion {
    private Polinomio numerador;
    private Polinomio denominador;

    public DxRacional(Polinomio numerador, Polinomio denominador){
        this.numerador = numerador;
        this.denominador = denominador;
    }

    @Override
    public Funcion derivar() {
        throw new UnsupportedOperationException(
                "Segunda derivada de función racional aún no implementada"
        );
    }

    @Override
    public double evaluar(double x) {
        double den = this.denominador.evaluar(x);
        if (den == 0) {
            throw new ArithmeticException("División por cero en derivada de función racional");
        }
        return this.numerador.evaluar(x) / den;
    }

    @Override
    public String toString() {
        return "(" + numerador + ") / (" + denominador + ")";
    }
}
