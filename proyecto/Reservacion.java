import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Reservacion {
    private static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de reservación de habitaciones");

        try {
            connection = getConnection();

            System.out.println("Ingrese los detalles de la reservación:");
            System.out.print("Código de la reservación: ");
            String codigo = scanner.nextLine();
            System.out.print("Nombre del cliente: ");
            String nombreCliente = scanner.nextLine();
            System.out.print("Fecha de la reservación (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();
            System.out.print("Hora de la reservación (HH:MM): ");
            String hora = scanner.nextLine();
            System.out.print("Número de personas: ");
            int numeroPersonas = scanner.nextInt();

            // Insertar la reservación en la base de datos
            insertarReservacion(codigo, nombreCliente, fecha, hora, numeroPersonas);
            System.out.println("Reservación realizada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            closeConnection();
            scanner.close();
        }
    }

    private static void insertarReservacion(String codigo, String nombreCliente, String fecha, String hora, int numeroPersonas) throws SQLException {
        String query = "INSERT INTO reservaciones (codigo, nombre_cliente, fecha, hora, numero_personas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, codigo);
            pstmt.setString(2, nombreCliente);
            pstmt.setString(3, fecha);
            pstmt.setString(4, hora);
            pstmt.setInt(5, numeroPersonas);
            pstmt.executeUpdate();
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel";
        String username = "tu_usuario";
        String password = "tu_contraseña";
        return DriverManager.getConnection(url, username, password);
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
