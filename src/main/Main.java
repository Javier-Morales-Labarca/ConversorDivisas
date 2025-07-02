package main;

import java.io.IOException;
import java.util.Scanner;



public class Main {
    static Scanner scanner = new Scanner(System.in);


    // Lista de divisas
    static String[] divisas = {
            "USD (Dolar).",
            "EUR (Euro).",
            "CLP (Peso Chileno).",
            "ARS (Peso Argentino).",
            "PEN (Sol Peruano).",
            "BOB (Peso Boliviano).",
            "UYU (Peso Uruguayo).",
            "PYG (Guaraní Paraguayo).",
            "BRL (Real Brasileño).",
            "COP (Peso Colombiano).",
            "VES (Bolivar Venezolano).",
            "MXN (Peso Mexicano).",
            "CAD (Dolar Canadiense)",

    };



    public static void main(String[] args) {
        int opcion;

        do{
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    mostrarDivisas();
                    break;
                case 2:
                    convertirMoneda();
                    break;
                case 3:
                    System.out.println("Saliendo del conversor, gracias por usarlo.");
                    break;
                default:
                    System.out.println("Opción inválida.");


            }
        }while (opcion != 3);
    }


    private static void convertirMoneda() {
        mostrarDivisas();

        System.out.println("Seleccione Divisa de Origen: ");
        int origenIndex = scanner.nextInt() -1;
        System.out.println("Seleccione Divisa de Destino: ");
        int destinoIndex = scanner.nextInt() -1;

        if (origenIndex < 0 || origenIndex >= divisas.length || destinoIndex < 0 || destinoIndex >= divisas.length) {
            System.out.println("Selección inválida.");
            return;
        }
        System.out.println("Ingrese cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        String origen = divisas[origenIndex].substring(0, 3);
        String destino = divisas[destinoIndex].substring(0, 3);

        try{
            double tasa = ApiConversor.obtenerTasa(origen, destino);
            double resultado = cantidad * tasa;

            System.out.printf("%.2f %s equivale a %.2f %s\n", cantidad, origen, resultado, destino);

         } catch (IOException | InterruptedException e) {
            System.out.println("Error al obtener tasa de cambio: " + e.getMessage());
        }
    }


        private static void mostrarDivisas(){
            System.out.println("\n***** DIVISAS DISPONIBLES *****");
            for (int i = 0; i < divisas.length; i++) {
                System.out.println((i + 1) + ". " + divisas[i]);
            }
        }



    private static void mostrarMenu() {
        System.out.println("\n***** CONVERSOR DE DIVISAS *****");
        System.out.println("1. Lista de Divisas.");
        System.out.println("2. Seleccione Divisas.");
        System.out.println("3. Salir.");
        System.out.println("Seleccione una Opción: ");
    }

}








