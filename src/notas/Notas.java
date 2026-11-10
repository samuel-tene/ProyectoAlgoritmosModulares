package notas;

import java.util.Locale;
import utils.Validaciones;

public class Notas {
    private static final int MAX_ESTUDIANTES = 25;
    private static final int CANTIDAD_NOTAS = 3;
    private static final int[] codigos = new int[MAX_ESTUDIANTES];
    private static final String[] nombres = new String[MAX_ESTUDIANTES];
    private static final double[][] notas = new double[MAX_ESTUDIANTES][CANTIDAD_NOTAS];
    private static final double[] asistencias = new double[MAX_ESTUDIANTES];
    private static int totalEstudiantes = 0;
    private static int totalClasesSemestre = 0;

    public static void ejecutar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = Validaciones.leerEnteroEnRango("Opción: ", 0, 4);
            if (opcion == 1) {
                registrarEstudiante();
            } else if (opcion == 2) {
                mostrarReporte();
            } else if (opcion == 3) {
                mostrarPromedioGrupo();
            } else if (opcion == 4) {
                mostrarMejorYPeor();
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Ver definitivas y estados");
        System.out.println("3. Promedio general del grupo");
        System.out.println("4. Mejor y peor promedio");
        System.out.println("0. Volver al menú principal");
    }

    private static boolean hayCupo() {
        return totalEstudiantes < MAX_ESTUDIANTES;
    }

    private static boolean existeCodigo(int codigo) {
        for (int i = 0; i < totalEstudiantes; i++) {
            if (codigos[i] == codigo) {
                return true;
            }
        }
        return false;
    }

    private static double calcularPorcentajeAsistencia(int clasesAsistidas, int clasesTotales) {
        return clasesAsistidas * 100.0 / clasesTotales;
    }

    private static double calcularDefinitiva(int pos) {
        double suma = 0.0;
        for (int j = 0; j < CANTIDAD_NOTAS; j++) {
            suma += notas[pos][j];
        }
        double promedio = suma / CANTIDAD_NOTAS;
        return Math.round(promedio * 100.0) / 100.0;
    }

    private static String determinarEstado(double definitiva, double asistencia) {
        if (asistencia < 70.0) {
            return "Reprueba";
        }
        if (definitiva >= 3.0) {
            return "Aprobado";
        }
        if (definitiva >= 2.0) {
            return "Habilita";
        }
        return "Reprueba";
    }

    private static void registrarEstudiante() {
        if (!hayCupo()) {
            System.out.println("No hay cupo (25 estudiantes)");
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
        double[] notasEstudiante = new double[CANTIDAD_NOTAS];
        for (int j = 0; j < CANTIDAD_NOTAS; j++) {
            notasEstudiante[j] = Validaciones.leerDoubleEnRango(
                    "Nota " + (j + 1) + ": ", 0.0, 5.0);
        }

        if (totalClasesSemestre == 0) {
            totalClasesSemestre = Validaciones.leerEnteroMinimo(
                    "Número de clases del semestre: ", 1);
        }
        int clasesAsistidas = Validaciones.leerEnteroEnRango(
                "Clases a las que asistió: ", 0, totalClasesSemestre);

        codigos[totalEstudiantes] = codigo;
        nombres[totalEstudiantes] = nombre;
        for (int j = 0; j < CANTIDAD_NOTAS; j++) {
            notas[totalEstudiantes][j] = notasEstudiante[j];
        }
        asistencias[totalEstudiantes] = calcularPorcentajeAsistencia(
                clasesAsistidas, totalClasesSemestre);
        totalEstudiantes++;
        System.out.println("Estudiante registrado");
    }

    private static void mostrarReporte() {
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados");
            return;
        }
        for (int i = 0; i < totalEstudiantes; i++) {
            double definitiva = calcularDefinitiva(i);
            String estado = determinarEstado(definitiva, asistencias[i]);
            System.out.println(String.format(Locale.US, "%d | %s | %.2f | %.1f%% | %s",
                    codigos[i], nombres[i], definitiva, asistencias[i], estado));
        }
    }

    private static double calcularPromedioGrupo() {
        double suma = 0.0;
        for (int i = 0; i < totalEstudiantes; i++) {
            suma += calcularDefinitiva(i);
        }
        return suma / totalEstudiantes;
    }

    private static void mostrarPromedioGrupo() {
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados");
            return;
        }
        System.out.println(String.format(Locale.US, "Promedio general del grupo: %.2f",
                calcularPromedioGrupo()));
    }

    private static int posicionMejor() {
        int posicion = 0;
        for (int i = 1; i < totalEstudiantes; i++) {
            if (calcularDefinitiva(i) > calcularDefinitiva(posicion)) {
                posicion = i;
            }
        }
        return posicion;
    }

    private static int posicionPeor() {
        int posicion = 0;
        for (int i = 1; i < totalEstudiantes; i++) {
            if (calcularDefinitiva(i) < calcularDefinitiva(posicion)) {
                posicion = i;
            }
        }
        return posicion;
    }

    private static void mostrarMejorYPeor() {
        if (totalEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados");
            return;
        }
        int mejor = posicionMejor();
        int peor = posicionPeor();
        System.out.println(String.format(Locale.US, "Mejor promedio: %s (%.2f)",
                nombres[mejor], calcularDefinitiva(mejor)));
        System.out.println(String.format(Locale.US, "Peor promedio: %s (%.2f)",
                nombres[peor], calcularDefinitiva(peor)));
    }
}
