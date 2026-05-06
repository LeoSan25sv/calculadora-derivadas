package calculadoraderivas;

import calculadoraderivas.funciones.Lineal;
import calculadoraderivas.funciones.Polinomica;
import calculadoraderivas.funciones.Potencia;
import calculadoraderivas.funciones.Racional;

import java.util.Scanner;

/**
 * Maneja toda la interacción con el usuario.
 * Separada de la lógica de negocio.
 *
 * @author Estudiante UCI
 * @version 2.1 (POO)
 */
public class InterfazUsuario {
    private final Scanner scanner;

    /**
     *
     * Constructor de la clase
     *
     */
    public InterfazUsuario() {
        this.scanner = new Scanner(System.in);
    }

    /**
     *
     * Metodo de instancia para mostrar menú
     *
     */
    public void mostrarMenu() {
        System.out.println("=== CALCULADORA DE DERIVADAS ===");
        System.out.println("Funciones Disponibles");
        System.out.println("1. f(x) = mx + n");
        System.out.println("2. f(x) = polinomio");
        System.out.println("3. f(x) = p(x)/q(x)");
        System.out.println("4. f(x) = u(x)^n\n");
    }

    /**
     * Metodo de instancia para tomar entrada de Usuario con la opción deseada
     * @return Número de la opcion deseada
     */
    public int leerOpcion(){
        System.out.print("Introduzca el número correspondiente a la opción deseada: ");
        return scanner.nextInt();
    }

    /**
     * Crea la función según la opción del usuario.
     *
     * @param opcion Opción del menú (1-4)
     * @return La función creada
     */
    public Funcion llamarFuncion(int opcion) {
        return switch (opcion) {
            case 1 -> funcionLineal();
            case 2 -> funcionPolinomica();
            case 3 -> funcionRacional();
            case 4 -> funcionPotencia();
            default -> null;
        };
    }

    /**
     * Muestra el resultado de la derivación.
     *
     * @param f Función original
     * @param df Derivada
     */
    public void mostrarResultado(Funcion f, Funcion df) {
        System.out.println("\n==============================");
        System.out.println("f(x) = " + f);

        if (df instanceof Potencia p) {
            System.out.println("f'(x) = " + p.derivadaToString());
        } else {
            System.out.println("f'(x) = " + df);
        }
    }

    // ========== MÉTODOS PRIVADOS ==========

    private Funcion funcionLineal() {
        System.out.println("\n--- DERIVAR FUNCIÓN LINEAL (f(x) = mx + n) ---\n");
        System.out.print("Introduzca m: ");
        int m = scanner.nextInt();
        System.out.print("Introduzca n: ");
        int n = scanner.nextInt();
        return new Lineal(m, n);
    }

    private Funcion funcionPolinomica() {
        System.out.println("\n--- DERIVAR FUNCIÓN POLINÓMICA ---\n");
        System.out.print("Número de términos: ");
        int numTerminos = scanner.nextInt();

        if (numTerminos <= 0) {
            System.out.println("Error: El número de términos debe ser positivo");
            return null;
        }

        int[][] terminos = new int[numTerminos][2];
        System.out.println("\nIntroduce cada término (constante exponente):");

        for (int i = 0; i < numTerminos; i++) {
            System.out.print("Término " + (i + 1) + ": ");
            terminos[i][0] = scanner.nextInt();
            terminos[i][1] = scanner.nextInt();
        }

        return new Polinomica(terminos);
    }

    private Funcion funcionRacional() {
        System.out.println("\n--- DERIVAR FUNCIÓN RACIONAL ---\n");

        System.out.println("---- Numerador p(x) ----");
        System.out.print("Número de términos: ");
        int numP = scanner.nextInt();

        if (numP <= 0) {
            System.out.println("Error: El número de términos debe ser positivo");
            return null;
        }

        int[][] pTerms = new int[numP][2];
        System.out.println("Introduce cada término (constante exponente):");
        for (int i = 0; i < numP; i++) {
            System.out.print("Término " + (i + 1) + ": ");
            pTerms[i][0] = scanner.nextInt();
            pTerms[i][1] = scanner.nextInt();
        }

        System.out.println("\n---- Denominador q(x) ----");
        System.out.print("Número de términos: ");
        int numQ = scanner.nextInt();

        if (numQ <= 0) {
            System.out.println("Error: El número de términos debe ser positivo");
            return null;
        }

        int[][] qTerms = new int[numQ][2];
        System.out.println("Introduce cada término (constante exponente):");
        for (int i = 0; i < numQ; i++) {
            System.out.print("Término " + (i + 1) + ": ");
            qTerms[i][0] = scanner.nextInt();
            qTerms[i][1] = scanner.nextInt();
        }

        return new Racional(pTerms, qTerms);
    }

    private Funcion funcionPotencia() {
        System.out.println("\n--- DERIVAR FUNCIÓN POTENCIA f(x) = u(x)^n ---\n");

        System.out.println("---- Base u(x) ----");
        System.out.print("Número de términos: ");
        int numTerminos = scanner.nextInt();

        if (numTerminos <= 0) {
            System.out.println("Error: El número de términos debe ser positivo");
            return null;
        }

        int[][] uTerms = new int[numTerminos][2];
        System.out.println("Introduce cada término (constante exponente):");
        for (int i = 0; i < numTerminos; i++) {
            System.out.print("Término " + (i + 1) + ": ");
            uTerms[i][0] = scanner.nextInt();
            uTerms[i][1] = scanner.nextInt();
        }

        System.out.print("\nExponente n: ");
        int n = scanner.nextInt();

        return new Potencia(uTerms, n);
    }
}
