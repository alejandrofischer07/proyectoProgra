import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    private static Connection c;
    private static CallableStatement cstm;
    private static ResultSet rs;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido al sistema");
        System.out.print("Ingresa tu usuario: ");
        String user = sc.next();

        System.out.print("Ingresa tu contraseña: ");
        String password = sc.next();

        try {
            c = getConnection(user, password);
            while (true) {
                mostrarMenu();
                String opcion = sc.next();

                switch (opcion) {
                    case "1":
                        ingresarReservacion(sc);
                        break;
                    case "2":
                        buscarReservacion(sc);
                        break;
                    case "3":
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        sc.close();
                        c.close();
                        return;
                    default:
                        System.out.println("Opción no válida, por favor intente nuevamente.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostrarMenu() {
        System.out.println("\nMenú de Gestión de Reservaciones");
        System.out.println("1. Ingreso de reservación");
        System.out.println("2. Búsqueda de reservación");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void ingresarReservacion(Scanner sc) {
        System.out.print("Ingrese el código de reservación: ");
        String codigo = sc.next();
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = sc.next();
        System.out.print("Ingrese la fecha de la reservación (AAAA-MM-DD): ");
        String fecha = sc.next();
        System.out.print("Ingrese la hora de la reservación (HH:MM): ");
        String hora = sc.next();
        int numeroPersonas = 0;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("Ingrese el número de personas: ");
            if (sc.hasNextInt()) {
                numeroPersonas = sc.nextInt();
                validInput = true;
            } else {
                System.out.println("Entrada inválida. Por favor ingrese un número entero.");
                sc.next(); // Descarta la entrada no válida
            }
        }

        BeanReservacion reservacion = new BeanReservacion(codigo, nombre, fecha, hora, numeroPersonas);

        try {
            cstm = c.prepareCall("{CALL insertarReservacion(?, ?, ?, ?, ?)}");
            cstm.setString(1, reservacion.getCodigo());
            cstm.setString(2, reservacion.getNombre());
            cstm.setDate(3, java.sql.Date.valueOf(reservacion.getFecha()));
            cstm.setTime(4, java.sql.Time.valueOf(reservacion.getHora() + ":00"));
            cstm.setInt(5, reservacion.getNumeroPersonas());
            cstm.execute();
            System.out.println("Reservación con código " + codigo + " ha sido ingresada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al ingresar la reservación: " + e.getMessage());
        }
    }

    private static void buscarReservacion(Scanner sc) {
        System.out.print("Ingrese el código de la reservación a buscar: ");
        String codigo = sc.next();
        try {
            cstm = c.prepareCall("{CALL buscarReservacion(?)}");
            cstm.setString(1, codigo);
            rs = cstm.executeQuery();
            if (rs.next()) {
                BeanReservacion reservacion = new BeanReservacion(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getDate("fecha").toString(),
                    rs.getTime("hora").toString(),
                    rs.getInt("numero_personas")
                );
                System.out.println("\nDetalles de la Reservación");
                System.out.println("Código: " + reservacion.getCodigo());
                System.out.println("Nombre del Cliente: " + reservacion.getNombre());
                System.out.println("Fecha: " + reservacion.getFecha());
                System.out.println("Hora: " + reservacion.getHora());
                System.out.println("Número de Personas: " + reservacion.getNumeroPersonas());
            } else {
                System.out.println("No se encontró una reservación con el código " + codigo + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la reservación: " + e.getMessage());
        }
    }

    public static Connection getConnection(String p_user, String p_password) throws SQLException {
        String host = "127.0.0.1";
        String user = p_user;
        String password = p_password;
        int port = 3306;
        String database = "hotel";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Librería cargada correctamente.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador: " + e.getMessage());
        }

        String url = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
        return DriverManager.getConnection(url, user, password);
    }
}
