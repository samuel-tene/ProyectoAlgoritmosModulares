package fabrica;

import java.util.Locale;
import utils.Validaciones;

public class Fabrica {
    private static final int MAX_OPERARIOS = 10;
    private static final int DIAS = 5;
    private static final double PORCENTAJE_BAJA = 0.8;
    private static final String[] NOMBRES_DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"
    };
    private static final int[] codigos = new int[MAX_OPERARIOS];
    private static final String[] nombres = new String[MAX_OPERARIOS];
    private static final int[][] produccion = new int[MAX_OPERARIOS][DIAS];
    private static int totalOperarios = 0;

    public static void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validaciones.leerEnteroEnRango("Opción: ", 0, 6);
            if (opcion == 1) {
                registrarOperario();
            } else if (opcion == 2) {
                ingresarProduccion();
            } else if (opcion == 3) {
                mostrarProduccionSemanal();
            } else if (opcion == 4) {
                mostrarMejorOperario();
            } else if (opcion == 5) {
                mostrarPromedioGeneral();
            } else if (opcion == 6) {
                mostrarDiasBajaProduccion();
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("1. Registrar operario");
        System.out.println("2. Ingresar producción diaria");
        System.out.println("3. Producción semanal por operario");
        System.out.println("4. Mejor operario de la semana");
        System.out.println("5. Promedio general de producción");
        System.out.println("6. Días con baja producción");
        System.out.println("0. Volver al menú principal");
    }

    private static boolean hayCupo() {
        return totalOperarios < MAX_OPERARIOS;
    }

    private static boolean existeCodigo(int codigo) {
        for (int i = 0; i < totalOperarios; i++) {
            if (codigos[i] == codigo) {
                return true;
            }
        }
        return false;
    }

    private static int buscarOperario(int codigo) {
        for (int i = 0; i < totalOperarios; i++) {
            if (codigos[i] == codigo) {
                return i;
            }
        }
        return -1;
    }

    private static void registrarOperario() {
        if (!hayCupo()) {
            System.out.println("No hay cupo (10 operarios)");
            return;
        }

        int codigo;
        do {
            codigo = Validaciones.leerEnteroMinimo("Código: ", 1);
            if (existeCodigo(codigo)) {
                System.out.println("El código ya existe");
            }
        } while (existeCodigo(codigo));

        String nombre = Validaciones.leerTexto("Nombre: ");
        codigos[totalOperarios] = codigo;
        nombres[totalOperarios] = nombre;
        totalOperarios++;
        System.out.println("Operario registrado");
    }

    private static void ingresarProduccion() {
        if (totalOperarios == 0) {
            System.out.println("No hay operarios registrados");
            return;
        }

        int codigo = Validaciones.leerEntero("Código del operario: ");
        int pos = buscarOperario(codigo);
        if (pos == -1) {
            System.out.println("Operario no existe");
            return;
        }

        for (int d = 0; d < DIAS; d++) {
            produccion[pos][d] = Validaciones.leerEnteroMinimo(
                    "Producción del " + NOMBRES_DIAS[d] + ": ", 0);
        }
        System.out.println("Producción registrada");
    }

    private static int calcularTotalSemanal(int pos) {
        int total = 0;
        for (int d = 0; d < DIAS; d++) {
            total += produccion[pos][d];
        }
        return total;
    }

    private static void mostrarProduccionSemanal() {
        if (totalOperarios == 0) {
            System.out.println("No hay operarios registrados");
            return;
        }
        for (int i = 0; i < totalOperarios; i++) {
            System.out.println(codigos[i] + " | " + nombres[i] + " | "
                    + calcularTotalSemanal(i) + " unidades");
        }
    }

    private static int posicionMejor() {
        int posicion = 0;
        for (int i = 1; i < totalOperarios; i++) {
            if (calcularTotalSemanal(i) > calcularTotalSemanal(posicion)) {
                posicion = i;
            }
        }
        return posicion;
    }

    private static void mostrarMejorOperario() {
        if (totalOperarios == 0) {
            System.out.println("No hay operarios registrados");
            return;
        }
        int mejor = posicionMejor();
        System.out.println("Mejor operario de la semana: " + nombres[mejor]
                + " (código " + codigos[mejor] + ") con "
                + calcularTotalSemanal(mejor) + " unidades");
    }

    private static double calcularPromedioGeneral() {
        double suma = 0.0;
        for (int i = 0; i < totalOperarios; i++) {
            suma += calcularTotalSemanal(i);
        }
        return suma / (totalOperarios * DIAS);
    }

    private static void mostrarPromedioGeneral() {
        if (totalOperarios == 0) {
            System.out.println("No hay operarios registrados");
            return;
        }
        System.out.println(String.format(Locale.US, "Promedio general: %.2f unidades por operario por día",
                calcularPromedioGeneral()));
    }

    private static double calcularPromedioDia(int dia) {
        double suma = 0.0;
        for (int i = 0; i < totalOperarios; i++) {
            suma += produccion[i][dia];
        }
        return suma / totalOperarios;
    }

    private static void mostrarDiasBajaProduccion() {
        if (totalOperarios == 0) {
            System.out.println("No hay operarios registrados");
            return;
        }

        double promedioGeneral = calcularPromedioGeneral();
        double limite = promedioGeneral * PORCENTAJE_BAJA;
        System.out.println(String.format(Locale.US, "Promedio general: %.2f", promedioGeneral));
        System.out.println(String.format(Locale.US, "Límite de baja producción: %.2f", limite));

        int diasBaja = 0;
        for (int d = 0; d < DIAS; d++) {
            double promedioDia = calcularPromedioDia(d);
            if (promedioDia < limite) {
                System.out.println(String.format(Locale.US, "%s: %.2f",
                        NOMBRES_DIAS[d], promedioDia));
                diasBaja++;
            }
        }
        if (diasBaja == 0) {
            System.out.println("No hay días con baja producción");
        }
    }
}
