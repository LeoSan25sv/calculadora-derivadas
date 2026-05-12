package calculadoraderivas.funciones;

import calculadoraderivas.Funcion;
import calculadoraderivas.Polinomio;
import calculadoraderivas.derivadas.DxPotencia;

/**
 * Representa una función potencia: f(x) = [u(x)]^n.
 *
 * @author Estudiante UCI
 */
public class Potencia implements Funcion {
    private final Polinomio base;
    private final int exponente;
    /**
     * Constructor de función potencia.
     *
     * @param base Polinomio base u(x)
     * @param exponente Exponente n (entero positivo)
     */
    public Potencia(Polinomio base, int exponente) {
        this.base = base;
        this.exponente = exponente;

        if (exponente <= 0) {
            throw new IllegalArgumentException("El exponente debe ser positivo");
        }
    }

    /**
     * Constructor a partir de array de términos.
     *
     * @param uTerms Términos de la base [coef, exp]
     * @param n Exponente
     */
    public Potencia(int[][] uTerms, int n) {
        this(new Polinomio(uTerms), n);
    }

    public Polinomio getBase() { return base; }
    public int getExponente() { return exponente; }

    @Override
    public DxPotencia derivar() {
        // Regla de la cadena + potencia: n·u^(n-1)·u'
        Polinomio du = base.derivar();
        return new DxPotencia(base,du,exponente);
    }

    @Override
    public double evaluar(double x) {
        return Math.pow(this.base.evaluar(x), exponente);
    }

    /**
     * Devuelve una representación de la función f(x) = u(x)^n
     */
    @Override
    public String toString() {
        if (exponente == 1) {
            return "(" + base + ")";
        }
        return "(" + base + ")^" + exponente;
    }
}