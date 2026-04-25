/**
 * Representa un término de un polinomio: coeficiente * x^exponente
 *
 * @author Estudiante UCI
 * @version 2.0 (POO)
 */
public class Termino {
    private int coeficiente;
    private final int exponente;

    /**
     * Constructor de un término.
     *
     * @param coeficiente Coeficiente del término (puede ser 0)
     * @param exponente Exponente del término (no negativo)
     */
    public Termino(int coeficiente, int exponente) {
        this.coeficiente = coeficiente;
        this.exponente = exponente;
    }

    // ========== GETTERS ==========
    public int getCoeficiente() { return coeficiente; }
    public int getExponente() { return exponente; }

    // ========== SETTER ==========
    public void setCoeficiente(int coeficiente) { this.coeficiente = coeficiente; }

    /**
     * Verifica si el término es cero.
     */
    public boolean esCero() {
        return coeficiente == 0;
    }

    /**
     * Aplica la regla de la potencia para derivar el término.
     *
     * @return Un nuevo término que es la derivada de este
     */
    public Termino derivar() {
        if (exponente == 0) {
            return new Termino(0, 0);  // Derivada de constante es 0
        }
        return new Termino(coeficiente * exponente, exponente - 1);
    }

    /**
     * Suma otro término a este (solo si tienen el mismo exponente).
     *
     * @param otro Término a sumar
     * @return true si se pudo sumar, false si exponentes diferentes
     */
    public boolean sumar(Termino otro) {
        if (this.exponente != otro.exponente) {
            return false;
        }
        this.coeficiente += otro.coeficiente;
        return true;
    }

    @Override
    public String toString() {
        if (coeficiente == 0) return "";

        if (exponente == 0) {
            return String.valueOf(coeficiente);
        }

        if (exponente == 1) {
            if (coeficiente == 1) return "x";
            if (coeficiente == -1) return "-x";
            return coeficiente + "x";
        }

        if (coeficiente == 1) return "x^" + exponente;
        if (coeficiente == -1) return "-x^" + exponente;

        return coeficiente + "x^" + exponente;
    }
}
