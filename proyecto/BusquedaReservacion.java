import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BusquedaReservacion {
    private static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al sistema de búsqueda de reservaciones");

        try {
            connection = getConnection();

            System.out.print("Ingrese el código de la reservación a buscar: ");
            String codigo = scanner.nextLine();

            // Buscar la reservación en la base de datos
            buscarReservacion(codigo);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            closeConnection();
            scanner.close();
        }
    }

    private static void buscarReservacion(String codigo) throws SQLException {
        String query = "SELECT * FROM reservaciones WHERE codigo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Detalles de la reservación:");
                System.out.println("Código: " + rs.getString("codigo"));
                System.out.println("Nombre del cliente: " + rs.getString("nombre_cliente"));
                System.out.println("Fecha: " + rs.getString("fecha"));
                System.out.println("Hora: " + rs.getString("hora"));
                System.out.println("Número de personas: " + rs.getInt("numero_personas"));
            } else {
                System.out.println("No se encontró una reservación con el código proporcionado.");
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/hotel";
        String username = "root";
        String password = "12345";
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
