/**
 * Representa una función polinómica: f(x) = Σ a_i·x^i.
 *
 * @author Estudiante UCI
 * @version 2.0 (POO)
 */
public class Polinomica implements Funcion {
    private final Polinomio polinomio;

    /**
     * Constructor a partir de un Polinomio.
     *
     * @param polinomio Polinomio que representa la función
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
    public String toString() {
        return polinomio.toString();
    }
}

