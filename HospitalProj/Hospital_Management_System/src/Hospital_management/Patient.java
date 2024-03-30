package Hospital_management;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
   

    public Patient(Connection connection) {
        this.connection = connection;
    }

   
    public void addPatient() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter patient name:");
            String name = scanner.nextLine();
            System.out.println("Enter patient age:");
            int age = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("Enter patient gender:");
            String gender = scanner.nextLine();
            System.out.println("Enter patient contact:");
            String contact = scanner.nextLine();

            String insertQuery = "INSERT INTO PAITENT (PNAME, AGE, GENDER, contact) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, contact);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Patient added successfully!");
                    main(null);
                } else {
                    System.out.println("Failed to add patient.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error adding patient: " + e.getMessage());
            addPatient();
        }
    }

   
    public void updatePatient() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter patient ID to update:");
            int patientId = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("Enter new patient name:");
            String name = scanner.nextLine();
            System.out.println("Enter new patient age:");
            int age = scanner.nextInt();
            scanner.nextLine(); 
            System.out.println("Enter new patient gender:");
            String gender = scanner.nextLine();
            System.out.println("Enter new patient contact:");
            String contact = scanner.nextLine();

            String updateQuery = "UPDATE PAITENT SET PNAME = ?, AGE = ?, GENDER = ?, contact = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, gender);
                preparedStatement.setString(4, contact);
                preparedStatement.setInt(5, patientId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Patient information updated successfully!");
                    main(null);
         
                } else {
                    System.out.println("Failed to update patient information.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating patient: " + e.getMessage());
            updatePatient();
        }
    }

  
    public void deletePatient() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter patient ID to delete:");
            int patientId = scanner.nextInt();

            String deleteQuery = "DELETE FROM PAITENT WHERE ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, patientId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Patient deleted successfully!");
                    main(null);
                } else {
                    System.out.println("Failed to delete patient.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            deletePatient();
        }
    }

    public void viewPatients() {
        String selectQuery = "SELECT ID, PNAME, AGE, GENDER, contact FROM PAITENT";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            System.out.println("ID\tPNAME\tAGE\tGENDER\tCONTACT");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("PNAME");
                int age = resultSet.getInt("AGE");
                String gender = resultSet.getString("GENDER");
                String contact = resultSet.getString("contact");
                System.out.println(id + "\t" + name + "\t" + age + "\t" + gender + "\t" + contact);
            }
        } catch (Exception e) {
            System.err.println("Error viewing patients: " + e.getMessage());
            viewPatients();
        }
       
    }
    public static void main(String[] args) {
        try {
           Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HOSPITAL", "root", "root");
            Patient patient = new Patient(connection);
        

          Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\nSelect the operation you want to perform:");
                System.out.println("1. Add Patient");
                System.out.println("2. Update Patient");
                System.out.println("3. Delete Patient");
                System.out.println("4. View Patients");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.updatePatient();
                        break;
                    case 3:
                        patient.deletePatient();
                        break;
                    case 4:
                        patient.viewPatients();
                        break;
                    case 5:
                        System.out.println("Exiting Hospital Management System...");
                        System.out.println("Thank you for using the Hospital Management System.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } while (choice != 5);

           connection.close();
          
        }catch(SQLException e)
        {
        	System.out.println("Issue while establishing the connection");
        }
        catch (InputMismatchException e) {
            System.out.println("Input must be a number.");
        }
    }
}
