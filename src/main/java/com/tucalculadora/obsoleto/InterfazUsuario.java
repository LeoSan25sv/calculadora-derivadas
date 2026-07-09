package com.tucalculadora.obsoleto;

import com.tucalculadora.obsoleto.funciones.Lineal;
import com.tucalculadora.obsoleto.funciones.Polinomica;
import com.tucalculadora.obsoleto.funciones.Potencia;
import com.tucalculadora.obsoleto.funciones.Racional;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Maneja toda la interacción con el usuario.
 * Separada de la lógica de negocio.
 *
 * @author Estudiante UCI
 */
public class InterfazUsuario {
    private final Scanner scanner;

    /**
     *
     * Constructor de una InterfazUsuario
     *
     */
    public InterfazUsuario() {
        this.scanner = new Scanner(System.in);
    }

    /**
     *
     * Metodo para mostrar menú principal de la calculadora
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
     * Metodo para tomar entrada de Usuario con la opción deseada
     * @return Número de la opción deseada
     */
    public int leerOpcion() {
        try {
            int option = leerEntero("Introduzca el número correspondiente a la opción deseada: ");
            if (option < 1 || option > 4)
                throw new IllegalArgumentException("Opción fuera de rango");
            else return option;
        } catch (IllegalArgumentException e) {
            System.out.println("Intentalo nuevamente");
            return leerOpcion();
        }
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
        System.out.println("=====================");
    }

    /**
     * Muestra un menú con opciones secundarias para continuar con el programa
     */
    public void menuSecundario(){
        System.out.println("Ahora si lo desea, puede: ");
        System.out.println("1. Evaluar f(x)");
        System.out.println("2. Evaluar f'(x)");
        System.out.println("3. Seguir derivando");
        System.out.println("0. Terminar");
    }

    /**
     * Ejecuta la opción deseada por el usuario
     *
     * @param opcion (0-3), 0 para cerrar el programa
     * @param f función para evaluar o derivar
     * @param df derivada para evaluar o seguir derivando
     */
    public void llamarOpcionesSecundarias(int opcion, Funcion f, Funcion df) {
        switch (opcion){
            case 1 -> evaluarFuncion(f);
            case 2 -> evaluarDerivada(df);
            case 3 -> derivadasSucecivas(df);
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
        int[][] terminos = llenarValores();
        return new Polinomica(terminos);
    }

    private Funcion funcionRacional() {
        System.out.println("\n--- DERIVAR FUNCIÓN RACIONAL f(x) = p(x) / q(x) ---\n");

        System.out.println("---- Paso 1: Construir Numerador p(x) ----");
        int[][] pTerms = llenarValores();

        System.out.println("\n---- Paso 2: Construir Denominador q(x) ----");
        int[][] qTerms = llenarValores();

        return new Racional(pTerms, qTerms);
    }
    private Funcion funcionPotencia() {
        System.out.println("\n--- DERIVAR FUNCIÓN POTENCIA f(x) = u(x)^n ---\n");

        System.out.println("---- Paso 1: Introducir función interna u(x) ----");
        int[][] uTerms = llenarValores();

        System.out.print("\n---- Paso 2: Introducir exponente n ----");
        int n = scanner.nextInt();
        scanner.nextLine();

        return new Potencia(uTerms, n);
    }

    // ========== MÉTODOS AUXILIARES ==========
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

    private void evaluarFuncion(Funcion funcion) {
        System.out.println("Para evaluar f(x)");
        System.out.print("Introduzca x = ");
        double x = scanner.nextDouble(); scanner.nextLine();
        try {
            double resultado = funcion.evaluar(x);
            System.out.println("f(" + x + ") = " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void evaluarDerivada(Funcion derivada) {
        System.out.println("Para evaluar f'(x)?");
        System.out.print("Introduzca x = ");
        double x = scanner.nextDouble(); scanner.nextLine();
        try {
            double resultado = derivada.evaluar(x);
            System.out.println("f'(" + x + ") = " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void derivadasSucecivas(Funcion f){
        System.out.println("--- Derivación Sucesiva ---");
        int orden = leerEntero("¿Qué orden de derivada? ");
        Funcion derivadaActual = f;
        for (int i = 1; i < orden; i++) {
            derivadaActual = derivadaActual.derivar();
        }
        System.out.println("f^(" + orden + ")(x) = " + derivadaActual);
    }

    private int[][] llenarValores(){
        ArrayList<Integer> constantes = new ArrayList<>();
        ArrayList<Integer> exponentes = new ArrayList<>();

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

                constantes.add(cons);
                exponentes.add(exp);

            } catch (NumberFormatException e) {
                System.out.println("Error: " + termino + " no es válido. Usa números.");
            }
        }
        // Verificar que al menos hay un término
        if (constantes.isEmpty()) {
            throw new IllegalStateException("No se ingresaron términos válidos");
        }

        int[][] terms = new int[constantes.size()][2];
        for (int i = 0; i < constantes.size(); i++) {
            terms[i][0] = constantes.get(i);
            terms[i][1] = exponentes.get(i);
        }
        return terms;
    }
}
