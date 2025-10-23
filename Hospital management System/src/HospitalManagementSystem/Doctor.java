package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

    public class Doctor {
        private Connection connection;
        private Scanner scanner;

        public Doctor(Connection connection){
            this.connection = connection;
            this.scanner = new Scanner(System.in);

        }

        public void viewDoctors(){
            String query = "select * from doctors";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Doctors: ");
                System.out.println("+------------+--------------------+------------------+");
                System.out.println("| Doctor Id  | Name               | Specialization   |");
                System.out.println("+------------+--------------------+------------------+");
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                    System.out.println("+------------+--------------------+------------------+");
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        // ✅ Update doctor details
        public void updateDoctor() {
            System.out.print("Enter Doctor ID to update: ");
            int id = scanner.nextInt();

            if (!getDoctorById(id)) {
                System.out.println("Doctor not found!");
                return;
            }

            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new specialization: ");
            String newSpecialization = scanner.nextLine();

            String query = "UPDATE doctors SET name=?, specialization=? WHERE id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, newName);
                ps.setString(2, newSpecialization);
                ps.setInt(3, id);

                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("Doctor Updated Successfully!");
                else
                    System.out.println("Update Failed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // ✅ Delete doctor by ID
        public void deleteDoctor() {
            System.out.print("Enter Doctor ID to delete: ");
            int id = scanner.nextInt();

            String query = "DELETE FROM doctors WHERE id=?";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("Doctor Deleted Successfully!");
                else
                    System.out.println("Doctor not found!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public boolean getDoctorById(int id){
            String query = "SELECT * FROM doctors WHERE id = ?";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    return true;
                }else{
                    return false;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }
    }
