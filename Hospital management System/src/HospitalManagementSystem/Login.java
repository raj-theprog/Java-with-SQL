package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

    public class Login {
        private Connection connection;
        private Scanner scanner;

        public Login(Connection connection, Scanner scanner) {
            this.connection = connection;
            this.scanner = scanner;
        }

        public String authenticateUser() {
            System.out.print("Enter Username: ");
            String username = scanner.next();
            System.out.print("Enter Password: ");
            String password = scanner.next();

            String query = "SELECT role FROM users WHERE username=? AND password=?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    System.out.println("Login Successful! Welcome, " + role.toUpperCase());
                    return role;
                } else {
                    System.out.println("Invalid credentials! Try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }