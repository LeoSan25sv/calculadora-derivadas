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
    public String toLaTeX() {
        String baseStr = base.toLaTeX();
        String expStr = exponente.toLaTeX();

        // Si el exponente es 1, no mostrar ^1
        if (exponente instanceof NodoConstante && ((NodoConstante) exponente).number == 1) {
            return baseStr;
        }

        // Si el exponente es 0, x^0 = 1
        if (exponente instanceof NodoConstante && ((NodoConstante) exponente).number == 0) {
            return "1";
        }

        // Si la base es simple (variable o número), no poner paréntesis
        if (base instanceof NodoVariable || base instanceof NodoConstante) {
            return baseStr + "^{" + expStr + "}";
        }

        // Si la base es compleja, poner paréntesis
        return "(" + baseStr + ")^{" + expStr + "}";
    }

    @Override
    public String toString() {
        return "( " + base + " ^ " + exponente + " )";
    }

    @Override
    public void printStackCommands() {
        base.printStackCommands();
        exponente.printStackCommands();
        System.out.println("  Operator ^");
    }
}