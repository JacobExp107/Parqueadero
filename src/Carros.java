import java.util.ArrayList;
import java.util.Scanner;

class Carro {
    private String placa;
    private int horaEntrada;

    public Carro(String placa, int horaEntrada) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
    }

    public String getPlaca() {
        return placa;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }
}

class Puesto {
    private int numero;
    private Carro carro;

    public Puesto(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }
}

class Parqueadero {
    private ArrayList<Puesto> puestos;
    private double tarifaPorHora;
    private int horaActual;

    public Parqueadero(double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
        this.horaActual = 6; // El parqueadero abre a las 6:00
        this.puestos = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            puestos.add(new Puesto(i));
        }
    }

    public void ingresarCarro(Carro carro, int numeroPuesto) {
        Puesto puesto = puestos.get(numeroPuesto - 1);
        if (puesto.getCarro() == null) {
            puesto.setCarro(carro);
        } else {
            System.out.println("El puesto está ocupado.");
        }
    }

    public void darSalidaCarro(String placa) {
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null && carro.getPlaca().equals(placa)) {
                int horasParqueado = horaActual - carro.getHoraEntrada();
                double monto = horasParqueado * tarifaPorHora;
                System.out.println("Monto a pagar: " + monto);
                puesto.setCarro(null);
                return;
            }
        }
        System.out.println("Carro no encontrado.");
    }

    public double darTiempoPromedio() {
        int sumaHoras = 0;
        int cantidadCarros = 0;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null) {
                sumaHoras += (horaActual - carro.getHoraEntrada());
                cantidadCarros++;
            }
        }
        return cantidadCarros == 0 ? 0 : (double) sumaHoras / cantidadCarros;
    }

    public Carro darCarroMasHoras() {
        Carro carroMasHoras = null;
        int maxHoras = -1;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null) {
                int horasParqueado = horaActual - carro.getHoraEntrada();
                if (horasParqueado > maxHoras) {
                    maxHoras = horasParqueado;
                    carroMasHoras = carro;
                }
            }
        }
        return carroMasHoras;
    }

    public boolean hayCarroMasDeOchoHoras() {
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null && (horaActual - carro.getHoraEntrada()) > 8) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Carro> darCarrosMasDeTresHorasParqueados() {
        ArrayList<Carro> carros = new ArrayList<>();
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null && (horaActual - carro.getHoraEntrada()) > 3) {
                carros.add(carro);
            }
        }
        return carros;
    }

    public boolean hayCarrosPlacaIgual() {
        ArrayList<String> placas = new ArrayList<>();
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null) {
                if (placas.contains(carro.getPlaca())) {
                    return true;
                }
                placas.add(carro.getPlaca());
            }
        }
        return false;
    }

    public int contarCarrosQueComienzanConPlacaPB() {
        int contador = 0;
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null && carro.getPlaca().startsWith("PB")) {
                contador++;
            }
        }
        return contador;
    }

    public boolean hayCarroCon24Horas() {
        for (Puesto puesto : puestos) {
            Carro carro = puesto.getCarro();
            if (carro != null && (horaActual - carro.getHoraEntrada()) >= 24) {
                return true;
            }
        }
        return false;
    }

    public int desocuparParqueadero() {
        int contador = 0;
        for (Puesto puesto : puestos) {
            if (puesto.getCarro() != null) {
                puesto.setCarro(null);
                contador++;
            }
        }
        return contador;
    }

    public String metodo1() {
        int cantidadPB = contarCarrosQueComienzanConPlacaPB();
        boolean hay24Horas = hayCarroCon24Horas();
        return "Cantidad de carros con placa PB: " + cantidadPB + " – Hay carro parqueado por 24 o más horas: " + (hay24Horas ? "Sí" : "No");
    }

    public String metodo2() {
        int cantidadSacados = desocuparParqueadero();
        return "Cantidad de carros sacados: " + cantidadSacados;
    }

    public int getHoraActual() {
        return horaActual;
    }

    public void avanzarReloj(int horas) {
        this.horaActual += horas;
        if (this.horaActual > 21) {
            this.horaActual = 21;
        }
    }

    public void cambiarTarifa(double nuevaTarifa) {
        this.tarifaPorHora = nuevaTarifa;
    }
}

public class Carros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parqueadero parqueadero = new Parqueadero(5.0); // Tarifa por hora es 5.0

        int opcion;
        do {
            System.out.println("\n--- Menú Parqueadero ---");
            System.out.println("1. Ingresar un carro al parqueadero.");
            System.out.println("2. Dar salida a un carro del parqueadero.");
            System.out.println("3. Informar los ingresos del parqueadero.");
            System.out.println("4. Consultar la cantidad de puestos disponibles.");
            System.out.println("5. Avanzar el reloj del parqueadero.");
            System.out.println("6. Cambiar la tarifa del parqueadero.");
            System.out.println("7. Salir.");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la placa del carro: ");
                    String placa = scanner.next();
                    System.out.print("Ingrese la hora de entrada (6-21): ");
                    int horaEntrada = scanner.nextInt();
                    System.out.print("Ingrese el número del puesto (1-40): ");
                    int numeroPuesto = scanner.nextInt();
                    parqueadero.ingresarCarro(new Carro(placa, horaEntrada), numeroPuesto);
                    break;

                case 2:
                    System.out.print("Ingrese la placa del carro: ");
                    String placaSalida = scanner.next();
                    parqueadero.darSalidaCarro(placaSalida);
                    break;

                case 3:
                    System.out.println("Ingresos del parqueadero: ");
                    System.out.println("Tiempo promedio en el parqueadero: " + parqueadero.darTiempoPromedio() + " horas.");
                    break;

                case 4:
                    System.out.println("Cantidad de puestos disponibles: ");
                    System.out.println("Carros con más de 3 horas: " + parqueadero.darCarrosMasDeTresHorasParqueados().size());
                    break;

                case 5:
                    System.out.print("Ingrese las horas a avanzar: ");
                    int horas = scanner.nextInt();
                    parqueadero.avanzarReloj(horas);
                    System.out.println("Hora actual: " + parqueadero.getHoraActual() + ":00");
                    break;

                case 6:
                    System.out.print("Ingrese la nueva tarifa por hora: ");
                    double nuevaTarifa = scanner.nextDouble();
                    parqueadero.cambiarTarifa(nuevaTarifa);
                    System.out.println("Nueva tarifa por hora: " + nuevaTarifa);
                    break;

                case 7:
                    System.out.println("Saliendo del programa.");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 7);

        scanner.close();
    }
}
