import cafeteria.Cafeteria;
import fabrica.Fabrica;
import notas.Notas;
import utils.Validaciones;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Cafetería");
            System.out.println("2. Control de notas");
            System.out.println("3. Control de producción");
            System.out.println("0. Salir");
            int opcion = Validaciones.leerEnteroEnRango("Opción: ", 0, 3);
            if (opcion == 1) {
                Cafeteria.ejecutar();
            } else if (opcion == 2) {
                Notas.ejecutar();
            } else if (opcion == 3) {
                Fabrica.ejecutar();
            } else {
                break;
            }
        }
    }
}
