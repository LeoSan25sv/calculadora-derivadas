package calculadoraderivas.funciones;


import calculadoraderivas.Funcion;
import calculadoraderivas.Polinomio;

/**
 * Representa una función racional: f(x) = p(x)/q(x).
 *
 * @author Estudiante UCI
 * @version 2.1 (POO)
 */
public class Racional implements Funcion {
    private final Polinomio numerador;
    private final Polinomio denominador;

    /**
     * Constructor de función racional.
     *
     * @param numerador Funciones.derivadas.Polinomio del numerador
     * @param denominador Funciones.derivadas.Polinomio del denominador
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
    public Funcion derivar() {
        // Regla del cociente: (q·p' - p·q') / q²
        Polinomio dp = numerador.derivar();
        Polinomio dq = denominador.derivar();

        Polinomio term1 = denominador.multiplicar(dp);
        Polinomio term2 = numerador.multiplicar(dq);
        Polinomio nuevoNumerador = term1.restar(term2);
        Polinomio nuevoDenominador = denominador.multiplicar(denominador);

        return new Racional(nuevoNumerador, nuevoDenominador);
    }

    @Override
    public String toString() {
        return "(" + numerador + ") / (" + denominador + ")";
    }
}
