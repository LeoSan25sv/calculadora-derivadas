package com.tucalculadora.parser;

import java.util.Scanner;
import com.tucalculadora.nodos.*;

public class Parser {
    // ========== ATRIBUTOS ==========
    private final Scanner scanner;

    // ========== CONSTRUCTOR ==========
    public Parser() {
        this.scanner = new Scanner(System.in);
    }

    // ========== CLASE PARSER INTERNO ==========
    static class ParserInterno {
        private final String input;
        private int posicion;

        ParserInterno(String input) {
            this.input = input;
            this.posicion = 0;
        }

        // MÉTODOS AUXILIARES

        /**
         * Devuelve el carácter actual sin consumirlo.
         * Si estamos al final, devuelve '\0'.
         */
        private char peek() {
            if (posicion >= input.length()) {
                return '\0';
            }
            return input.charAt(posicion);
        }

        /**
         * Salta espacios en blanco y tabuladores.
         */
        private void skipBlanks() {
            while (peek() == ' ' || peek() == '\t') {
                posicion++;
            }
        }

        /**
         * Devuelve el siguiente carácter y avanza la posición.
         */
        private char getAnyChar() {
            char c = peek();
            posicion++;
            return c;
        }

        /**
         * Lee un número double.
         * Ejemplos: 3, 3.14, .5, 2.
         */
        private double getDouble() {
            StringBuilder sb = new StringBuilder();
            while (Character.isDigit(peek()) || peek() == '.') {
                sb.append(getAnyChar());
            }
            return Double.parseDouble(sb.toString());
        }


        // MÉTODOS DEL PARSER
        NodoExpresion expressionTree() throws ParseError {
            skipBlanks();

            NodoExpresion term = termTree();
            skipBlanks();

            while (peek() == '+' || peek() == '-') {
                char op = getAnyChar();
                NodoExpresion next = termTree();
                term = new NodoOperadorBinario(op, term, next);
                skipBlanks();
            }
            return term;
        }

        NodoExpresion termTree() throws ParseError {
            skipBlanks();

            NodoExpresion factor = factorTree();
            skipBlanks();

            while (peek() == '*' || peek() == '/') {
                char op = getAnyChar();
                NodoExpresion next = factorTree();
                factor = new NodoOperadorBinario(op, factor, next);
                skipBlanks();
            }
            return factor;
        }

        NodoExpresion factorTree() throws ParseError {
            skipBlanks();

            NodoExpresion factor;

            // 1. Signo negativo unario
            if (peek() == '-') {
                getAnyChar();
                factor = new NodoOperadorUnario(factorTree());
            }
            // 2. Número
            else if (Character.isDigit(peek()) || peek() == '.') {
                factor = new NodoConstante(getDouble());
            }
            // 3. Variable x
            else if (peek() == 'x') {
                getAnyChar();
                factor = new NodoVariable();
            }
            // 4. Expresión entre paréntesis
            else if (peek() == '(') {
                getAnyChar();
                factor = expressionTree();
                if (peek() != ')') {
                    throw new ParseError("Falta ')'");
                }
                getAnyChar();
            }
            else {
                throw new ParseError("Factor inválido: " + peek());
            }

            // 5. POTENCIA (se aplica AL factor que acabamos de leer)
            if (peek() == '^') {
                getAnyChar();
                NodoExpresion exponente = factorTree();  // Recursión para exponente
                factor = new NodoPotencia(factor, exponente);
            }

            return factor;
        }

        // ========== METODOS PRIVADOS ==========
        private double extraerCoeficiente(NodoExpresion nodo) {
            if (nodo instanceof NodoVariable) {
                return 1;
            }
            if (nodo instanceof NodoOperadorBinario bin) {
                if (bin.operador == '*' && bin.izquierdo instanceof NodoConstante) {
                    return ((NodoConstante) bin.izquierdo).number;
                }
            }
            return 0;
        }
    }

    // ========== METODO PRINCIPAL ==========
    public void ejecutar() {
        System.out.println("=== CALCULADORA DE DERIVADAS ===");
        System.out.println("Ejemplo: x * x");
        System.out.println("Ejemplo: (x + 2) * (x - 1)");
        System.out.println("Línea vacía para terminar.\n");

        while (true) {
            System.out.print("f(x) = ");
            String linea = scanner.nextLine().trim();

            if (linea.isEmpty()) {
                break;
            }

            // Validación de paréntesis
            int balance = 0;
            for (int i = 0; i < linea.length(); i++) {
                char c = linea.charAt(i);
                if (c == '(') balance++;
                if (c == ')') balance--;
            }
            if (balance < 0) {
                System.out.println("  Error: Paréntesis ')' sin abrir");
                continue;
            }
            if (balance > 0) {
                System.out.println("  Error: Falta cerrar paréntesis");
                continue;
            }

            try {
                // 1. Parsear
                ParserInterno parser = new ParserInterno(linea);
                NodoExpresion arbol = parser.expressionTree();

                // 2. Mostrar la función original
                System.out.println("  f(x)  = " + arbol.toString());

                // 3. Derivar
                NodoExpresion derivada = arbol.derivar();

                // 4. Simplificar derivada
                NodoExpresion derivadaSimplificada = derivada.simplificar();

                // 5. Mostrar la derivada
                System.out.println("  f'(x) = " + derivada);
                System.out.println("  f'(x) = " + derivadaSimplificada.toString());

                // 6. Evaluar la derivada en x=2
                System.out.println("  f'(2) = " + derivada.value(2));

                System.out.println();

            } catch (ParseError e) {
                System.out.println("  Error: " + e.getMessage());
                System.out.println();
            }
        }

        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    public static void main(String[] args) {
        new Parser().ejecutar();
    }
}

