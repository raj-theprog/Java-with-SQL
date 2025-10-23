package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Billing {
    private Connection connection;
    private Scanner scanner;

    public Billing(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add a new bill
    public void addBill() {
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Bill Amount: ");
        double amount = scanner.nextDouble();

        String query = "INSERT INTO bills(patient_id, amount) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, patientId);
            ps.setDouble(2, amount);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Bill Added Successfully!");
            } else {
                System.out.println("Failed to add bill!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View bills for a patient
    public void viewBills() {
        System.out.print("Enter Patient ID to view bills: ");
        int patientId = scanner.nextInt();

        String query = "SELECT * FROM bills WHERE patient_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            System.out.println("+----+-----------+--------+---------------------+");
            System.out.println("| ID | PatientID | Amount | Bill Date           |");
            System.out.println("+----+-----------+--------+---------------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Timestamp date = rs.getTimestamp("bill_date");

                System.out.printf("| %-2d | %-9d | %-6.2f | %-19s |\n", id, patientId, amount, date);
            }

            System.out.println("+----+-----------+--------+---------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
