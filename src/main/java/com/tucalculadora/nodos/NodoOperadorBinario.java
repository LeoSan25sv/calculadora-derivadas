package com.tucalculadora.nodos;

public class NodoOperadorBinario extends NodoExpresion {
    public char operador;
    public NodoExpresion izquierdo;
    NodoExpresion derecho;

    public NodoOperadorBinario(char operador, NodoExpresion izquierdo, NodoExpresion derecho) {
        this.operador = operador;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    @Override
    public double value(double x) {
        double izq = izquierdo.value(x);
        double der = derecho.value(x);

        return switch (operador) {
            case '+' -> izq + der;
            case '-' -> izq - der;
            case '*' -> izq * der;
            case '/' -> izq / der;
            default -> Double.NaN;
        };
    }

    @Override
    public NodoExpresion derivar() {
        return switch (operador) {
            // SUMA: (u + v)' = u' + v'
            case '+' -> new NodoOperadorBinario(operador, izquierdo.derivar(), derecho.derivar());
            // RESTA: (u - v)' = u' - v'
            case '-' -> new NodoOperadorBinario(operador, izquierdo.derivar(), derecho.derivar());
            // PRODUCTO: (u * v)' = u'v + uv'
            case '*' -> {
                NodoExpresion u = izquierdo;
                NodoExpresion v = derecho;
                NodoExpresion du = izquierdo.derivar();
                NodoExpresion dv = derecho.derivar();

                NodoExpresion termino1 = new NodoOperadorBinario('*', du, v);
                NodoExpresion termino2 = new NodoOperadorBinario('*', u, dv);

                yield new NodoOperadorBinario('+', termino1, termino2);
            }
            // COCIENTE: (u / v)' = (u'v - uv') / v²
            case '/' -> {
                NodoExpresion u = izquierdo;
                NodoExpresion v = derecho;
                NodoExpresion du = izquierdo.derivar();
                NodoExpresion dv = derecho.derivar();

                // numerador = u'v - uv'
                NodoExpresion termino1 = new NodoOperadorBinario('*', du, v);
                NodoExpresion termino2 = new NodoOperadorBinario('*', u, dv);
                NodoExpresion numerador = new NodoOperadorBinario('-', termino1, termino2);

                // denominador = v²
                NodoExpresion denominador = new NodoOperadorBinario('*', v, v);

                yield new NodoOperadorBinario('/', numerador, denominador) {
                };
            }
            default -> throw new UnsupportedOperationException("Operador no soportado");
        };
    }

    @Override
    public NodoExpresion simplificar() {
        NodoExpresion left = izquierdo.simplificar();
        NodoExpresion right = derecho.simplificar();

        // 1. Si ambos son números, operar directamente
        if (left instanceof NodoConstante && right instanceof NodoConstante) {
            double leftNum = ((NodoConstante) left).number;
            double rightNum = ((NodoConstante) right).number;
            return switch (operador) {
                case '+' -> new NodoConstante(leftNum + rightNum);
                case '-' -> new NodoConstante(leftNum - rightNum);
                case '*' -> new NodoConstante(leftNum * rightNum);
                case '/' ->
                        rightNum == 0 ? new NodoOperadorBinario('/', left, right) : new NodoConstante(leftNum / rightNum);
                default -> new NodoOperadorBinario(operador, left, right);
            };
        }

        // 2. Identidades algebraicas
        if (operador == '+' && right instanceof NodoConstante && ((NodoConstante) right).number == 0) return left;
        if (operador == '+' && left instanceof NodoConstante && ((NodoConstante) left).number == 0) return right;
        if (operador == '-' && right instanceof NodoConstante && ((NodoConstante) right).number == 0) return left;
        if (operador == '-' && left instanceof NodoConstante && ((NodoConstante) left).number == 0) {
            return new NodoOperadorUnario(right);
        }
        if (operador == '*' && right instanceof NodoConstante && ((NodoConstante) right).number == 1) return left;
        if (operador == '*' && left instanceof NodoConstante && ((NodoConstante) left).number == 1) return right;
        if (operador == '*' && right instanceof NodoConstante && ((NodoConstante) right).number == 0)
            return new NodoConstante(0);
        if (operador == '*' && left instanceof NodoConstante && ((NodoConstante) left).number == 0)
            return new NodoConstante(0);
        if (operador == '/' && right instanceof NodoConstante && ((NodoConstante) right).number == 1) return left;
        if (operador == '/' && left instanceof NodoConstante && ((NodoConstante) left).number == 0)
            return new NodoConstante(0);

        // 3. Combinar términos semejantes (x + x → 2*x)
        if (operador == '+' && left instanceof NodoVariable && right instanceof NodoVariable) {
            return new NodoOperadorBinario('*', new NodoConstante(2), new NodoVariable());
        }

        // 4. Si no se aplica ninguna regla, devolver con hijos simplificados
        return new NodoOperadorBinario(operador, left, right);
    }

    @Override
    public String toString() {
        return "( " + izquierdo.toString() + " " + operador + " " + derecho.toString() + " )";
    }

    @Override
    public String toLaTeX() {
        String izq = izquierdo.toLaTeX();
        String der = derecho.toLaTeX();

        return switch (operador) {
            case '+' -> izq + " + " + der;
            case '-' -> izq + " - " + der;
            case '*' -> {
                // Si es número * variable, no mostrar punto de multiplicación
                if (izquierdo instanceof NodoConstante && derecho instanceof NodoVariable) {
                    yield izq + der;  // "2x" en lugar de "2*x"
                }
                // Si es variable * número, mostrar con punto
                if (izquierdo instanceof NodoVariable && derecho instanceof NodoConstante) {
                    yield izq + " \\cdot " + der;  // "x · 2"
                }
                yield izq + " \\cdot " + der;      // "x · y"
            }
            case '/' -> "\\frac{" + izq + "}{" + der + "}";
            default -> izq + " " + operador + " " + der;
        };
    }

    @Override
    void printStackCommands() {
        izquierdo.printStackCommands();
        derecho.printStackCommands();
        System.out.println("  Operator " + operador);
    }
}