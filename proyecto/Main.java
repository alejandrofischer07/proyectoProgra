import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al sistema de reservación de habitaciones");

        try {
            connection = getConnection();

            int opcion;
            do {
                System.out.println("\nMenú de opciones:");
                System.out.println("1. Ingreso de reservación");
                System.out.println("2. Búsqueda de reservación");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        ingresarReservacion(scanner);
                        break;
                    case 2:
                        buscarReservacion(scanner);
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                }
            } while (opcion != 3);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            closeConnection();
            scanner.close();
        }
    }

    private static void ingresarReservacion(Scanner scanner) {
    }

    private static void buscarReservacion(Scanner scanner) {
        }

    private static Connection getConnection() throws SQLException {
        String host = "127.0.0.1";
        int port = 3306;
        String database = "hotel";
        String username = "tu_usuario";
        String password = "tu_contraseña";
        String url = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Conexión establecida correctamente.");
        return connection;
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
