package calculadoraderivas.derivadas;


import calculadoraderivas.Funcion;
import calculadoraderivas.Polinomio;

public class DxPotencia implements Funcion {
    final Polinomio base;
    final Polinomio du;
    final int nuevoExponente;
    final int exponente;

    /**
     * Metodo para derivar una función exponencial
     * @param base se refiere a la función interna u
     * @param du se refiere a la derivada de la función interna: dy/dx
     * @param exponente exponente de la función exponencial
     */
    public DxPotencia(Polinomio base, Polinomio du, int exponente) {
        this.base = base;
        this.du = du;
        this.nuevoExponente = exponente - 1;
        this.exponente = exponente;
    }

    @Override
    public Funcion derivar() {
        throw new UnsupportedOperationException(
                "Segunda derivada de función potencia aún no implementada"
        );
    }

    @Override
    public double evaluar(double x) {
        double valorNumerico = exponente * Math.pow(this.base.evaluar(x), nuevoExponente) * this.du.evaluar(x);
        return valorNumerico;
    }


    /**
     * Devuelve la representación de la derivada en formato texto.
     * Esto es necesario porque la derivada tiene la forma 'n·u^(n-1)·u'
     */
    @Override
    public String toString(){
        return exponente + "[( " + base + ")^" + nuevoExponente + "] · (" + du + ")";
    }
}
