/**
 * Representa una función lineal de la forma f(x) = mx + n.
 *
 * @author Estudiante UCI
 * @version 2.0 (POO)
 */
public class Lineal implements Funcion {
    private final int m;  // pendiente
    private final int n;  // intersección

    /**
     * Constructor de función lineal.
     *
     * @param m Pendiente
     * @param n Intersección con el eje Y
     */
    public Lineal(int m, int n) {
        this.m = m;
        this.n = n;
    }

    /**
     * Obtiene la pendiente.
     */
    public int getM() { return m; }

    /**
     * Obtiene la intersección.
     */
    public int getN() { return n; }

    @Override
    public Funcion derivar() {
        // La derivada de mx + n es m (una constante)
        // La representamos como un polinomio constante
        Polinomio constante = new Polinomio();
        constante.añadirTermino(m, 0);
        return new Polinomica(constante);
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
