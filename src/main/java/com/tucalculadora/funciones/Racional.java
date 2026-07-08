package com.tucalculadora.funciones;


import com.tucalculadora.Funcion;
import com.tucalculadora.Polinomio;
import com.tucalculadora.derivadas.DxRacional;

/**
 * Representa una función racional: f(x) = p(x)/q(x).
 *
 * @author Estudiante UCI
 */
public class Racional implements Funcion {
    private final Polinomio numerador;
    private final Polinomio denominador;

    /**
     * Constructor de función racional.
     *
     * @param numerador polinomio superior
     * @param denominador polinomio inferior
     */
    public Racional(Polinomio numerador, Polinomio denominador) {
        this.numerador = numerador;
        this.denominador = denominador;

        // Validar denominador no cero
        if (denominador.esCero()) {
            throw new IllegalArgumentException("El denominador no puede ser cero");
        }
    }

    /**
     * Constructor a partir de arrays de términos.
     *
     * @param pTerms Términos del numerador [coef, exp]
     * @param qTerms Términos del denominador [coef, exp]
     */
    public Racional(int[][] pTerms, int[][] qTerms) {
        this(new Polinomio(pTerms), new Polinomio(qTerms));
    }

    public Polinomio getNumerador() { return numerador; }
    public Polinomio getDenominador() { return denominador; }

    @Override
    public DxRacional derivar() {
        // Regla del cociente: (q·p' - p·q') / q²
        Polinomio dp = numerador.derivar();
        Polinomio dq = denominador.derivar();

        Polinomio term1 = denominador.multiplicar(dp);
        Polinomio term2 = numerador.multiplicar(dq);
        Polinomio nuevoNumerador = term1.restar(term2);
        Polinomio nuevoDenominador = denominador.multiplicar(denominador);

        return new DxRacional(nuevoNumerador, nuevoDenominador);
    }

    @Override
    public double evaluar(double x) {
        return this.numerador.evaluar(x) / this.denominador.evaluar(x);
    }

    @Override
    public String toString() {
        return "(" + numerador + ") / (" + denominador + ")";
    }
}
