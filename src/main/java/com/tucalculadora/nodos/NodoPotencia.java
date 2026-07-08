package com.tucalculadora.nodos;

public class NodoPotencia extends NodoExpresion {
    NodoExpresion base;
    NodoExpresion exponente;

    public NodoPotencia(NodoExpresion base, NodoExpresion exponente) {
        this.base = base;
        this.exponente = exponente;
    }

    @Override
    public double value(double x) {
        return Math.pow(base.value(x), exponente.value(x));
    }

    @Override
    public NodoExpresion derivar() {
        // Regla de la potencia: (u^n)' = n * u^(n-1) * u'
        NodoExpresion n = exponente;
        NodoExpresion u = base;
        NodoExpresion uPrima = u.derivar();

        // n * u^(n-1) * u'
        NodoExpresion nMenosUno = new NodoOperadorBinario('-', n, new NodoConstante(1));
        NodoExpresion uElevado = new NodoPotencia(u, nMenosUno);
        NodoExpresion termino1 = new NodoOperadorBinario('*', n, uElevado);
        NodoExpresion resultado = new NodoOperadorBinario('*', termino1, uPrima);

        return resultado;
    }

    @Override
    public NodoExpresion simplificar() {
        NodoExpresion baseSimp = base.simplificar();
        NodoExpresion expSimp = exponente.simplificar();

        // Si exponente es 0 → 1
        if (expSimp instanceof NodoConstante && ((NodoConstante) expSimp).number == 0) {
            return new NodoConstante(1);
        }

        // Si exponente es 1 → base
        if (expSimp instanceof NodoConstante && ((NodoConstante) expSimp).number == 1) {
            return baseSimp;
        }

        // Si base es 0 → 0 (excepto 0^0 que es indefinido)
        if (baseSimp instanceof NodoConstante && ((NodoConstante) baseSimp).number == 0) {
            return new NodoConstante(0);
        }

        // Si base y exponente son constantes → calcular
        if (baseSimp instanceof NodoConstante && expSimp instanceof NodoConstante) {
            double baseNum = ((NodoConstante) baseSimp).number;
            double expNum = ((NodoConstante) expSimp).number;
            return new NodoConstante(Math.pow(baseNum, expNum));
        }

        return new NodoPotencia(baseSimp, expSimp);
    }

    @Override
    void printStackCommands() {
        base.printStackCommands();
        exponente.printStackCommands();
        System.out.println("  Operator ^");
    }

    @Override
    public String toString() {
        return "( " + base + " ^ " + exponente + " )";
    }
}