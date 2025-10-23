package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;
public class prescription {

        private Connection connection;
        private Scanner scanner;

        public prescription(Connection connection, Scanner scanner) {
            this.connection = connection;
            this.scanner = scanner;
        }

    public void addPrescription() {
        System.out.print("Enter Patient ID: ");
        int pid = scanner.nextInt();
        System.out.print("Enter Doctor ID: ");
        int did = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter Medicines: ");
        String medicines = scanner.nextLine();
        System.out.print("Enter Dosage: ");
        String dosage = scanner.nextLine();

        String query = "INSERT INTO prescriptions(patient_id, doctor_id, medicines, dosage) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, pid);
            ps.setInt(2, did);
            ps.setString(3, medicines);
            ps.setString(4, dosage);

            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Prescription Added Successfully!");
            else System.out.println("Failed to add prescription!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPrescriptions() {
        System.out.print("Enter Patient ID to view prescriptions: ");
        int patientId = scanner.nextInt();

        String query = "SELECT * FROM prescriptions WHERE patient_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            System.out.println("+----+-----------+----------------------------+---------------------+");
            System.out.println("| ID | PatientID | Medicines                  | Prescription Date   |");
            System.out.println("+----+-----------+----------------------------+---------------------+");

            while (rs.next()) {
                int id = rs.getInt("id");
                String medicines = rs.getString("medicines");
                Timestamp date = rs.getTimestamp("prescription_date");

                System.out.printf("| %-2d | %-9d | %-26s | %-19s |\n", id, patientId, medicines, date);
            }

            System.out.println("+----+-----------+----------------------------+---------------------+");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
