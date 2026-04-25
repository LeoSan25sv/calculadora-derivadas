/**
 * Calculadora de Derivadas - Versión Orientada a Objetos.
 *
 * <p>Esta es la clase principal que orquesta toda la aplicación.
 * Separa claramente la interfaz de usuario de la lógica de negocio.</p>
 *
 * @author Estudiante UCI
 * @version 2.0 (POO)
 */
public class CalculadoraDerivadas {

    public static void main(String[] args) {
        InterfazUsuario ui = new InterfazUsuario();

        ui.mostrarMenu();
        int opcion = ui.leerOpcion();

        if (opcion < 1 || opcion > 4) {
            System.out.println("Opción no válida");
            return;
        }

        Funcion f = ui.crearFuncion(opcion);

        if (f == null) {
            System.out.println("Error al crear la función");
            return;
        }

        Funcion df = f.derivar();
        ui.mostrarResultado(f, df);
    }
}
