package calculadoraderivas;

import java.util.*;

/**
 * Representa un polinomio como una suma de términos.
 * Los términos se agrupan automáticamente por exponente.
 *
 * @author Estudiante UCI
 * @version 2.1 (POO)
 */
public class Polinomio {
    private final Map<Integer, Integer> terminos;  // exponente → coeficiente

    /**
     * Constructor: crea un polinomio vacío.
     */
    public Polinomio() {
        terminos = new TreeMap<>(Collections.reverseOrder());
    }

    /**
     * Constructor: crea un polinomio a partir de un array de términos.
     *
     * @param terms Array de [coeficiente, exponente]
     */
    public Polinomio(int[][] terms) {
        this();
        for (int[] t : terms) {
            agregarTermino(t[0], t[1]);
        }
    }

    /**
     * Constructor: crea un polinomio a partir de un término.
     *
     * @param t Término inicial
     */
    public Polinomio(Termino t) {
        this();
        agregarTermino(t);
    }

    // ========== MÉTODOS PÚBLICOS ==========

    /**
     * Añade un término al polinomio (agrupa términos semejantes).
     *
     * @param coeficiente Coeficiente del término
     * @param exponente Exponente del término
     */
    public void agregarTermino(int coeficiente, int exponente) {
        if (coeficiente == 0) return;

        terminos.merge(exponente, coeficiente, Integer::sum);

        // Eliminar si el coeficiente se anuló
        if (terminos.get(exponente) == 0) {
            terminos.remove(exponente);
        }
    }

    /**
     * Añade un término al polinomio.
     *
     * @param termino Término a añadir
     */
    public void agregarTermino(Termino termino) {
        agregarTermino(termino.getCoeficiente(), termino.getExponente());
    }

    /**
     * Verifica si el polinomio es cero.
     */
    public boolean esCero() {
        return terminos.isEmpty();
    }

    /**
     * Calcula la derivada del polinomio.
     *
     * @return Un nuevo polinomio con la derivada
     */
    public Polinomio derivar() {
        Polinomio derivada = new Polinomio();

        for (Map.Entry<Integer, Integer> entry : terminos.entrySet()) {
            int exp = entry.getKey();
            int coef = entry.getValue();

            if (exp != 0) {
                derivada.agregarTermino(coef * exp, exp - 1);
            }
        }

        return derivada;
    }

    /**
     * Suma este polinomio con otro.
     *
     * @param otro Funciones.derivadas.Polinomio a sumar
     * @return Nuevo polinomio resultado de la suma
     */
    public Polinomio sumar(Polinomio otro) {
        Polinomio resultado = new Polinomio();

        // Añadir términos de este polinomio
        for (Map.Entry<Integer, Integer> entry : this.terminos.entrySet()) {
            resultado.agregarTermino(entry.getValue(), entry.getKey());
        }

        // Añadir términos del otro polinomio
        for (Map.Entry<Integer, Integer> entry : otro.terminos.entrySet()) {
            resultado.agregarTermino(entry.getValue(), entry.getKey());
        }

        return resultado;
    }

    /**
     * Resta otro polinomio de este (this - otro).
     *
     * @param otro Funciones.derivadas.Polinomio a restar
     * @return Nuevo polinomio resultado de la resta
     */
    public Polinomio restar(Polinomio otro) {
        Polinomio resultado = new Polinomio();

        for (Map.Entry<Integer, Integer> entry : this.terminos.entrySet()) {
            resultado.agregarTermino(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<Integer, Integer> entry : otro.terminos.entrySet()) {
            resultado.agregarTermino(-entry.getValue(), entry.getKey());
        }

        return resultado;
    }

    /**
     * Multiplica este polinomio por otro.
     *
     * @param otro Funciones.derivadas.Polinomio multiplicador
     * @return Nuevo polinomio producto
     */
    public Polinomio multiplicar(Polinomio otro) {
        Polinomio resultado = new Polinomio();

        for (Map.Entry<Integer, Integer> e1 : this.terminos.entrySet()) {
            for (Map.Entry<Integer, Integer> e2 : otro.terminos.entrySet()) {
                resultado.agregarTermino(
                        e1.getValue() * e2.getValue(),
                        e1.getKey() + e2.getKey()
                );
            }
        }

        return resultado;
    }

    /**
     * Multiplica el polinomio por un escalar.
     *
     * @param escalar Número por el que multiplicar
     * @return Nuevo polinomio escalado
     */
    public Polinomio multiplicarPorEscalar(int escalar) {
        Polinomio resultado = new Polinomio();

        for (Map.Entry<Integer, Integer> entry : terminos.entrySet()) {
            resultado.agregarTermino(entry.getValue() * escalar, entry.getKey());
        }

        return resultado;
    }

    @Override
    public String toString() {
        if (terminos.isEmpty()) return "0";

        StringBuilder sb = new StringBuilder();
        boolean primero = true;

        for (Map.Entry<Integer, Integer> entry : terminos.entrySet()) {
            int coef = entry.getValue();
            int exp = entry.getKey();

            if (coef == 0) continue;

            // Manejo de signos
            if (primero) {
                if (coef < 0) sb.append("-");
                primero = false;
            } else {
                sb.append(coef > 0 ? " + " : " - ");
            }

            int absCoef = Math.abs(coef);

            if (exp == 0) {
                sb.append(absCoef);
            } else if (exp == 1) {
                if (absCoef == 1) {
                    sb.append("x");
                } else {
                    sb.append(absCoef).append("x");
                }
            } else {
                if (absCoef == 1) {
                    sb.append("x^").append(exp);
                } else {
                    sb.append(absCoef).append("x^").append(exp);
                }
            }
        }

        return sb.toString();
    }
}