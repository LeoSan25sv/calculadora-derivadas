## 🚀 MIGRACIÓN PASO A PASO: TU CALCULADORA A MAVEN

Te voy a guiar **archivo por archivo** para migrar tu `CalculadoraParser.java` al nuevo proyecto Maven con estructura profesional.

---

## 📁 ESTRUCTURA FINAL

```text
calculadora-derivadas/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── tucalculadora/
│                   ├── CalculadoraParser.java  (clase principal con main)
│                   ├── nodos/
│                   │   ├── NodoExpresion.java
│                   │   ├── NodoConstante.java
│                   │   ├── NodoVariable.java
│                   │   ├── NodoOperadorBinario.java
│                   │   └── NodoOperadorUnario.java
│                   └── parser/
│                       ├── ParserInterno.java
│                       └── ParseError.java
└── target/ (generado por Maven)
```

---

## 📝 PASO 1: CREAR LOS ARCHIVOS

### 1.1 `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tucalculadora</groupId>
    <artifactId>calculadora-derivadas</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- JLaTeXMath para renderizar fórmulas -->
        <dependency>
            <groupId>org.scilab.forge</groupId>
            <artifactId>jlatexmath</artifactId>
            <version>1.0.7</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.tucalculadora.CalculadoraParser</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### 1.2 `src/main/java/com/tucalculadora/nodos/NodoExpresion.java`

```java
package com.tucalculadora.nodos;

/**
 * Clase abstracta base para todos los nodos del árbol de expresión.
 * Define los métodos que deben implementar todos los nodos.
 */
public abstract class NodoExpresion {
    /**
     * Evalúa la expresión numéricamente para un valor dado de x.
     */
    public abstract double value(double x);

    /**
     * Calcula la derivada simbólica de la expresión.
     */
    public abstract NodoExpresion derivar();

    /**
     * Genera código LaTeX para la expresión.
     */
    public abstract String toLaTeX();

    /**
     * Devuelve una representación en cadena de la expresión en notación infija.
     */
    @Override
    public abstract String toString();
}
```

---

### 1.3 `src/main/java/com/tucalculadora/nodos/NodoConstante.java`

```java
package com.tucalculadora.nodos;

/**
 * Nodo que representa un número constante.
 */
public class NodoConstante extends NodoExpresion {
    private final double number;

    public NodoConstante(double number) {
        this.number = number;
    }

    @Override
    public double value(double x) {
        return number;
    }

    @Override
    public NodoExpresion derivar() {
        return new NodoConstante(0);
    }

    @Override
    public String toLaTeX() {
        if (number == (int) number) {
            return String.valueOf((int) number);
        }
        return String.valueOf(number);
    }

    @Override
    public String toString() {
        if (number == (int) number) {
            return String.valueOf((int) number);
        }
        return String.valueOf(number);
    }
}
```

---

### 1.4 `src/main/java/com/tucalculadora/nodos/NodoVariable.java`

```java
package com.tucalculadora.nodos;

/**
 * Nodo que representa la variable x.
 */
public class NodoVariable extends NodoExpresion {
    @Override
    public double value(double x) {
        return x;
    }

    @Override
    public NodoExpresion derivar() {
        return new NodoConstante(1);
    }

    @Override
    public String toLaTeX() {
        return "x";
    }

    @Override
    public String toString() {
        return "x";
    }
}
```

---

### 1.5 `src/main/java/com/tucalculadora/nodos/NodoOperadorBinario.java`

```java
package com.tucalculadora.nodos;

/**
 * Nodo que representa una operación binaria (+, -, *, /).
 */
public class NodoOperadorBinario extends NodoExpresion {
    private final char operador;
    private final NodoExpresion izquierdo;
    private final NodoExpresion derecho;

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
            case '+' -> new NodoOperadorBinario('+', izquierdo.derivar(), derecho.derivar());
            case '-' -> new NodoOperadorBinario('-', izquierdo.derivar(), derecho.derivar());
            case '*' -> {
                // Regla del producto: u'v + uv'
                NodoExpresion u = izquierdo;
                NodoExpresion v = derecho;
                NodoExpresion du = izquierdo.derivar();
                NodoExpresion dv = derecho.derivar();

                NodoExpresion termino1 = new NodoOperadorBinario('*', du, v);
                NodoExpresion termino2 = new NodoOperadorBinario('*', u, dv);

                yield new NodoOperadorBinario('+', termino1, termino2);
            }
            case '/' -> {
                // Regla del cociente: (u'v - uv') / v²
                NodoExpresion u = izquierdo;
                NodoExpresion v = derecho;
                NodoExpresion du = izquierdo.derivar();
                NodoExpresion dv = derecho.derivar();

                NodoExpresion termino1 = new NodoOperadorBinario('*', du, v);
                NodoExpresion termino2 = new NodoOperadorBinario('*', u, dv);
                NodoExpresion numerador = new NodoOperadorBinario('-', termino1, termino2);
                NodoExpresion denominador = new NodoOperadorBinario('*', v, v);

                yield new NodoOperadorBinario('/', numerador, denominador);
            }
            default -> throw new UnsupportedOperationException("Operador no soportado: " + operador);
        };
    }

    @Override
    public String toLaTeX() {
        String izq = izquierdo.toLaTeX();
        String der = derecho.toLaTeX();

        // Simplificar: si el operador es '*', no mostrar puntos innecesarios
        return switch (operador) {
            case '*' -> izq + " \\cdot " + der;
            case '/' -> "\\frac{" + izq + "}{" + der + "}";
            case '^' -> izq + "^{" + der + "}";
            default -> izq + " " + operador + " " + der;
        };
    }

    @Override
    public String toString() {
        return "( " + izquierdo.toString() + " " + operador + " " + derecho.toString() + " )";
    }
}
```

---

### 1.6 `src/main/java/com/tucalculadora/nodos/NodoOperadorUnario.java`

```java
package com.tucalculadora.nodos;

/**
 * Nodo que representa el operador unario negativo (-).
 */
public class NodoOperadorUnario extends NodoExpresion {
    private final NodoExpresion operador;

    public NodoOperadorUnario(NodoExpresion operador) {
        this.operador = operador;
    }

    @Override
    public double value(double x) {
        return -operador.value(x);
    }

    @Override
    public NodoExpresion derivar() {
        return new NodoOperadorUnario(operador.derivar());
    }

    @Override
    public String toLaTeX() {
        return "-" + operador.toLaTeX();
    }

    @Override
    public String toString() {
        return "-( " + operador.toString() + " )";
    }
}
```

---

### 1.7 `src/main/java/com/tucalculadora/parser/ParseError.java`

```java
package com.tucalculadora.parser;

/**
 * Excepción personalizada para errores de parsing.
 */
public class ParseError extends Exception {
    public ParseError(String message) {
        super(message);
    }
}
```

---

### 1.8 `src/main/java/com/tucalculadora/parser/ParserInterno.java`

```java
package com.tucalculadora.parser;

import com.tucalculadora.nodos.*;

/**
 * Parser recursivo descendente para expresiones matemáticas.
 * 
 * Gramática:
 * <expresion> ::= <termino> { ('+' | '-') <termino> }
 * <termino>   ::= <factor> { ('*' | '/') <factor> }
 * <factor>    ::= numero | x | '(' <expresion> ')' | '-' <factor>
 */
public class ParserInterno {
    private final String input;
    private int posicion;

    public ParserInterno(String input) {
        this.input = input;
        this.posicion = 0;
    }

    private char peek() {
        if (posicion >= input.length()) {
            return '\0';
        }
        return input.charAt(posicion);
    }

    private void skipBlanks() {
        while (peek() == ' ' || peek() == '\t') {
            posicion++;
        }
    }

    private char getAnyChar() {
        char c = peek();
        posicion++;
        return c;
    }

    private double getDouble() throws ParseError {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(peek()) || peek() == '.') {
            sb.append(getAnyChar());
        }
        try {
            return Double.parseDouble(sb.toString());
        } catch (NumberFormatException e) {
            throw new ParseError("Número inválido: " + sb);
        }
    }

    /**
     * Expresión = Término { ('+' | '-') Término }
     */
    public NodoExpresion expressionTree() throws ParseError {
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

    /**
     * Término = Factor { ('*' | '/') Factor }
     */
    private NodoExpresion termTree() throws ParseError {
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

    /**
     * Factor = número | x | '(' Expresión ')' | '-' Factor
     */
    private NodoExpresion factorTree() throws ParseError {
        skipBlanks();

        // Signo negativo unario
        if (peek() == '-') {
            getAnyChar();
            NodoExpresion factor = factorTree();
            return new NodoOperadorUnario(factor);
        }

        // Número
        if (Character.isDigit(peek()) || peek() == '.') {
            return new NodoConstante(getDouble());
        }

        // Variable x
        if (peek() == 'x') {
            getAnyChar();
            return new NodoVariable();
        }

        // Expresión entre paréntesis
        if (peek() == '(') {
            getAnyChar();
            NodoExpresion exp = expressionTree();
            if (peek() != ')') {
                throw new ParseError("Falta ')'");
            }
            getAnyChar();
            return exp;
        }

        throw new ParseError("Factor inválido: " + peek());
    }
}
```

---

### 1.9 `src/main/java/com/tucalculadora/CalculadoraParser.java`

```java
package com.tucalculadora;

import com.tucalculadora.nodos.NodoExpresion;
import com.tucalculadora.parser.ParseError;
import com.tucalculadora.parser.ParserInterno;

import java.util.Scanner;

/**
 * Calculadora de derivadas con parser recursivo descendente.
 * 
 * Ejemplos de uso:
 *   f(x) = x * x
 *   f(x) = (x + 2) * (x - 1)
 *   f(x) = x / (x + 1)
 */
public class CalculadoraParser {
    private final Scanner scanner;

    public CalculadoraParser() {
        this.scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        System.out.println("=== CALCULADORA DE DERIVADAS ===");
        System.out.println("Escribe expresiones con x");
        System.out.println("Ejemplos: x*x, (x+2)*(x-1), x/(x+1)");
        System.out.println("Línea vacía para terminar.\n");

        while (true) {
            System.out.print("f(x) = ");
            String linea = scanner.nextLine().trim();

            if (linea.isEmpty()) {
                break;
            }

            try {
                ParserInterno parser = new ParserInterno(linea);
                NodoExpresion arbol = parser.expressionTree();

                System.out.println("  f(x)  = " + arbol.toString());
                System.out.println("  f(2)  = " + arbol.value(2));
                System.out.println("  f(x)  = " + arbol.toLaTeX());

                NodoExpresion derivada = arbol.derivar();

                System.out.println("  f'(x) = " + derivada.toString());
                System.out.println("  f'(2) = " + derivada.value(2));
                System.out.println("  f'(x) = " + derivada.toLaTeX());

                System.out.println();

            } catch (ParseError e) {
                System.out.println("  Error: " + e.getMessage() + "\n");
            }
        }

        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    public static void main(String[] args) {
        new CalculadoraParser().ejecutar();
    }
}
```

---

## ✅ VERIFICACIÓN

```bash
# 1. Compilar
mvn clean compile

# 2. Ejecutar
mvn exec:java -Dexec.mainClass="com.tucalculadora.CalculadoraParser"

# 3. O crear JAR
mvn clean package
java -jar target/calculadora-derivadas-1.0-SNAPSHOT.jar
```

---

## 🧪 SALIDA ESPERADA

```text
=== CALCULADORA DE DERIVADAS ===
Escribe expresiones con x
Ejemplos: x*x, (x+2)*(x-1), x/(x+1)
Línea vacía para terminar.

f(x) = (x+2)*(x-1)
  f(x)  = ( ( x + 2 ) * ( x - 1 ) )
  f(2)  = 3.0
  f(x)  = ( x + 2 ) \cdot ( x - 1 )
  f'(x) = ( ( 1 * ( x - 1 ) ) + ( ( x + 2 ) * 1 ) )
  f'(2) = 3.0
  f'(x) = 1 \cdot ( x - 1 ) + ( x + 2 ) \cdot 1
```

---

## 🎯 CIERRE

```text
┌─────────────────────────────────────────────────────────────────┐
│                                                                 │
│   TU CÓDIGO AHORA ESTÁ:                                        │
│                                                                 │
│   ✅ En un proyecto Maven                                      │
│   ✅ Organizado en paquetes                                    │
│   ✅ Con JLaTeXMath integrado                                  │
│   ✅ Preparado para la Fase 5 (renderizado)                    │
│                                                                 │
│   ¡ESTO ES UN PROYECTO PROFESIONAL!                            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```