import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static Connection c;
    private static CallableStatement cstm;
    private static ResultSet rs;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido al sistema");
        System.out.println("Ingresa tu usuario:");
        String user = sc.next();

        System.out.println("Ingresa tu contraseña:");
        String password = sc.next();
        sc.close();
    
        List<BeanUser> listUser = new ArrayList<>();

        listUser = getUser(user, password);
        listUser.forEach(u -> System.out.println(u.getName())); // corregido de lisUser a listUser y System.out.println
    }

    public static List<BeanUser> getUser(String p_user, String p_password) {
        List<BeanUser> listUser = new ArrayList<>();
        try {
            c = getConnection(p_user, p_password);
            cstm = c.prepareCall("SELECT * FROM user");
            rs = cstm.executeQuery();

            while (rs.next()) {
                BeanUser beanUser = new BeanUser();
                beanUser.setId(rs.getInt("id")); // corregido BeanUser.setId a beanUser.setId
                beanUser.setName(rs.getString("name")); // corregido rs,getString a rs.getString

                listUser.add(beanUser);
                // Aquí puedes realizar operaciones con los datos obtenidos del ResultSet y crear objetos BeanUser
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstm != null) cstm.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
        return listUser;
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
