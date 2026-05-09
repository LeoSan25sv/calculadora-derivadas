package calculadoraderivas.funciones;

import calculadoraderivas.Funcion;
import calculadoraderivas.Polinomio;
import calculadoraderivas.derivadas.DxPotencia;

/**
 * Representa una función potencia: f(x) = [u(x)]^n.
 *
 * @author Estudiante UCI
 * @version 2.1 (POO)
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
    public String toString() {
        if (exponente == 1) {
            return "(" + base + ")";
        }
        return "(" + base + ")^" + exponente;
    }

    /**
     * Devuelve la representación de la derivada en formato texto.
     * Esto es necesario porque la derivada tiene la forma 'n·u^(n-1)·u'
     */
    public String derivadaToString() {
        Polinomio du = base.derivar();
        int nuevoExp = exponente - 1;

        StringBuilder sb = new StringBuilder();
        sb.append(exponente);

        if (nuevoExp == 1) {
            sb.append("·(").append(base).append(")");
        } else if (nuevoExp > 1) {
            sb.append("·(").append(base).append(")^").append(nuevoExp);
        }

        sb.append("·(").append(du).append(")");
        return sb.toString();
    }
}