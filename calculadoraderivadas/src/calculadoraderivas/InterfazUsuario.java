package calculadoraderivas;

import calculadoraderivas.derivadas.DxPotencia;
import calculadoraderivas.funciones.Lineal;
import calculadoraderivas.funciones.Polinomica;
import calculadoraderivas.funciones.Potencia;
import calculadoraderivas.funciones.Racional;

import java.util.ArrayList;
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
    public int leerOpcion() {
        return leerEntero("Introduzca el número correspondiente a la opción deseada: ");
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
            case 2 -> funcionPolinomio();
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
        System.out.println("\n=====RESULTADO=====");
        System.out.println("f(x) = " + f);
        System.out.println("f'(x) = " + df);
        System.out.println("====================");
    }

    public void evaluar(Funcion funcion, Funcion derivada){
        System.out.println("Quieres evaluar f(x) o f'(x)?");
        int opcion = leerEntero("1 para f(x), 2 para f'(x), 0 para cancelar");
        switch (opcion){
            case 1 -> evaluarFuncion(funcion);
            case 2 -> evaluarDerivada(derivada);
            case 0 -> System.out.println("GRACIAS POR USAR NUESTRA CALCULADORA DE DERIVADAS!!!");
        }
    }

    // ========== MÉTODOS PRIVADOS ==========

    private Funcion funcionLineal() {
        System.out.println("\n--- DERIVAR FUNCIÓN LINEAL (f(x) = mx + n) ---\n");
        int m = leerEntero("Introduzca m: ");
        int n = leerEntero("Introduzca n: ");
        return new Lineal(m, n);
    }

    private Funcion funcionPolinomio() {
        System.out.println("\n--- DERIVAR POLINOMIO ---\n");

        int numTerminos = leerEntero("Número de términos: ");

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
        int numP = leerEntero("Número de términos: ");

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

        System.out.println("---- Paso 1: Introducir función interna u(x) ----");

        ArrayList<Integer> constante = new ArrayList<>();
        ArrayList<Integer> exponente = new ArrayList<>();

        System.out.println("Introduce cada término (constante exponente):");
        System.out.println("Para terminar introduzca: (0 0)");

        while (true) {
            String termino = scanner.nextLine().trim(); //representa a_n ^n

            if (termino.equals("0 0")) break;

            int espacioIndex = termino.indexOf(' ');
            if (espacioIndex == -1) {
                System.out.println("Formato incorrecto. Usa: constante exponente");
                continue;
            }

            try {
                String consStr = termino.substring(0, espacioIndex);
                String expStr = termino.substring(espacioIndex + 1);

                int cons = Integer.parseInt(consStr);
                int exp = Integer.parseInt(expStr);

                if (cons == 0 && exp == 0) break;

                constante.add(cons);
                exponente.add(exp);

            } catch (NumberFormatException e) {
                System.out.println("Error: " + termino + " no es válido. Usa números.");
            }
        }

        // Verificar que al menos hay un término
        if (constante.isEmpty()) {
            throw new IllegalStateException("No se ingresaron términos válidos");
        }

        int[][] uTerms = new int[constante.size()][2];
        for (int i = 0; i < constante.size(); i++) {
            uTerms[i][0] = constante.get(i);
            uTerms[i][1] = exponente.get(i);
        }

        System.out.print("\n---- Paso 2: Introducir exponente n ----");
        int n = scanner.nextInt();
        scanner.nextLine(); // ← limpiar el buffer DESPUÉS de leer el número

        return new Potencia(uTerms, n);
    }

    // metodos auxiliares
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        int valor = scanner.nextInt();
        scanner.nextLine();  // ← LIMPIEZA AUTOMÁTICA
        return valor;
    }

    private double leerDouble(String mensaje) {
        System.out.print(mensaje);
        double valor = scanner.nextDouble();
        scanner.nextLine();  // ← LIMPIEZA AUTOMÁTICA
        return valor;
    }

    private void evaluarDerivada(Funcion derivada) {
        System.out.print("Introduzca x = ");
        double x = scanner.nextDouble(); scanner.nextLine();
        try {
            double resultado = derivada.evaluar(x);
            System.out.println("f'(" + x + ") = " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void evaluarFuncion(Funcion funcion) {
        System.out.print("Introduzca x = ");
        double x = scanner.nextDouble(); scanner.nextLine();
        try {
            double resultado = funcion.evaluar(x);
            System.out.println("f(" + x + ") = " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
