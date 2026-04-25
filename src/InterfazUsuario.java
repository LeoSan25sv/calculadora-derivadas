import java.util.Scanner;

/**
 * Maneja toda la interacción con el usuario.
 * Separada de la lógica de negocio.
 *
 * @author Estudiante UCI
 * @version 2.0 (POO)
 */
public class InterfazUsuario {
    private final Scanner scanner;

    public InterfazUsuario() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el menú principal.
     */
    public void mostrarMenu() {
        System.out.println("=== CALCULADORA DE DERIVADAS ===");
        System.out.println("Funciones Disponibles");
        System.out.println("1. f(x) = mx + n\n");
        System.out.println("2. f(x) = polinomio\n");
        System.out.println("3. f(x) = p(x)/q(x)\n");
        System.out.println("4. f(x) = u(x)^n\n");
    }

    /**
     * Lee la opción del usuario.
     *
     * @return Opción seleccionada (1-4)
     */
    public int leerOpcion() {
        System.out.print("Introduzca el número correspondiente: ");
        return scanner.nextInt();
    }

    /**
     * Crea la función según la opción del usuario.
     *
     * @param opcion Opción del menú (1-4)
     * @return La función creada
     */
    public Funcion crearFuncion(int opcion) {
        switch (opcion) {
            case 1:
                return crearFuncionLineal();
            case 2:
                return crearFuncionPolinomica();
            case 3:
                return crearFuncionRacional();
            case 4:
                return crearFuncionPotencia();
            default:
                System.out.println("Opción no válida");
                return null;
        }
    }

    /**
     * Muestra el resultado de la derivación.
     *
     * @param f Función original
     * @param df Derivada
     */
    public void mostrarResultado(Funcion f, Funcion df) {
        System.out.println("\n==========================================================");
        System.out.println("f(x) = " + f);

        if (df instanceof Potencia) {
            Potencia p = (Potencia) df;
            System.out.println("f'(x) = " + p.derivadaToString());
        } else {
            System.out.println("f'(x) = " + df);
        }
    }

    // ========== MÉTODOS PRIVADOS DE CREACIÓN ==========

    private Funcion crearFuncionLineal() {
        System.out.println("\n--- DERIVAR FUNCIÓN LINEAL (f(x) = mx + n) ---\n");
        System.out.print("Introduzca m: ");
        int m = scanner.nextInt();
        System.out.print("Introduzca n: ");
        int n = scanner.nextInt();
        return new Lineal(m, n);
    }

    private Funcion crearFuncionPolinomica() {
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

    private Funcion crearFuncionRacional() {
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

    private Funcion crearFuncionPotencia() {
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
