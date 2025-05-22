import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/stock_controller_db";
        String username = "root";
        String password = "claudio";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}