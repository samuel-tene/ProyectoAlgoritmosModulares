package utils;

import java.util.Scanner;

public class Validaciones {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número entero");
            }
        }
    }

    public static int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero >= min && numero <= max) {
                return numero;
            }
            System.out.println("Debe estar entre " + min + " y " + max);
        }
    }

    public static int leerEnteroMinimo(String mensaje, int min) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero >= min) {
                return numero;
            }
            System.out.println("Debe ser mayor o igual a " + min);
        }
    }

    public static double leerDoubleEnRango(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine();
            try {
                double numero = Double.parseDouble(entrada.trim());
                if (numero >= min && numero <= max) {
                    return numero;
                }
                System.out.println("Debe estar entre " + min + " y " + max);
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número válido");
            }
        }
    }

    public static double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = SCANNER.nextLine();
            try {
                double numero = Double.parseDouble(entrada.trim());
                if (numero > 0) {
                    return numero;
                }
                System.out.println("Debe ser mayor a 0");
            } catch (NumberFormatException e) {
                System.out.println("Debe ser un número válido");
            }
        }
    }

    public static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine();
            if (texto.trim().isEmpty()) {
                System.out.println("No puede estar vacío");
            } else {
                return texto.trim();
            }
        }
    }
}
