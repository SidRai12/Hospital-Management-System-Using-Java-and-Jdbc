package Hospital_management;

import java.sql.*;
import java.util.Scanner;
public class Appoinment {
	 
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/HOSPITAL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

  
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

   
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

   
    public static void disconnect() throws SQLException {
        if (resultSet != null) resultSet.close();
        if (statement != null) statement.close();
        if (connection != null) connection.close();
    }

 
    public static void createAppointment() throws SQLException {
        connect();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

      
        String checkSql = "SELECT COUNT(*) FROM APPOIMENTS WHERE DID = ? AND APP_DATE = ?";
        statement = connection.prepareStatement(checkSql);
        statement.setInt(1, doctorId);
        statement.setString(2, appointmentDate);
        resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        if (count > 0) {
            System.out.println("Sorry, the doctor already has an appointment on the same day.");
            disconnect();
            return;
        }

        
        String sql = "INSERT INTO APPOIMENTS (PID, DID, APP_DATE) VALUES (?, ?, ?)";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, patientId);
        statement.setInt(2, doctorId);
        statement.setString(3, appointmentDate);
        statement.executeUpdate();
        disconnect();
        System.out.println("Appointment created successfully.");
    }


    
    public static void readAppointments() throws SQLException {
        connect();
        String sql = "SELECT * FROM APPOIMENTS";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("ID");
            int patientId = resultSet.getInt("PID");
            int doctorId = resultSet.getInt("DID");
            String appointmentDate = resultSet.getString("APP_DATE");
            System.out.println("Appointment ID: " + appointmentId + ", Patient ID: " + patientId +
                    ", Doctor ID: " + doctorId + ", Appointment Date: " + appointmentDate);
        }
        disconnect();
    }

 
    public static void updateAppointment() throws SQLException {
        connect();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Appointment ID to update: ");
        int appointmentId = scanner.nextInt();
        System.out.print("Enter New Appointment Date (YYYY-MM-DD): ");
        String newAppointmentDate = scanner.next();
        String sql = "UPDATE APPOIMENTS SET APP_DATE = ? WHERE ID = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, newAppointmentDate);
        statement.setInt(2, appointmentId);
        statement.executeUpdate();
        disconnect();
        System.out.println("Appointment updated successfully.");
    }

    public static void deleteAppointment() throws SQLException {
        connect();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Appointment ID to delete: ");
        int appointmentId = scanner.nextInt();
        String sql = "DELETE FROM APPOIMENTS WHERE ID = ?";
        statement = connection.prepareStatement(sql);
        statement.setInt(1, appointmentId);
        statement.executeUpdate();
        disconnect();
        System.out.println("Appointment deleted successfully.");
    }

  
    public static void Appoinment() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Create Appointment");
                System.out.println("2. Read Appointments");
                System.out.println("3. Update Appointment");
                System.out.println("4. Delete Appointment");
                System.out.println("5. Exit");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createAppointment();
                        break;
                    case 2:
                        readAppointments();
                        PaitentDetails();
                        break;
                    case 3:
                        updateAppointment();
                        break;
                    case 4:
                        deleteAppointment();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void PaitentDetails() throws SQLException
    { 
    	  connect();
          Scanner scanner = new Scanner(System.in);
          System.out.print("Enter Appointment ID to search the Paitent Details: ");
          int appointmentId = scanner.nextInt();
          String sqlQuery = "SELECT P.Pname, P.age, P.Gender, P.Contact, D.Dname " +
                  "FROM PAITENT P " +
                  "INNER JOIN APPOIMENTS A ON P.ID = A.PID " +
                  "INNER JOIN DOCTOR D ON A.id = D.id " +
                  "WHERE A.ID = ?";
          statement = connection.prepareStatement(sqlQuery);
          statement.setInt(1, appointmentId);
          
          ResultSet resultSet =  statement.executeQuery();

          while (resultSet.next()) {
              String patientName = resultSet.getString("Pname");
              int age = resultSet.getInt("age");
              String gender = resultSet.getString("Gender");
              String contact = resultSet.getString("Contact");
              String doctorName = resultSet.getString("Dname");

              System.out.println("Patient Name: " + patientName);
              System.out.println("Age: " + age);
              System.out.println("Gender: " + gender);
              System.out.println("Contact: " + contact);
              System.out.println("Doctor Name: " + doctorName);
          }
          disconnect();
        Appoinment();
    	 
    }
    public static void main(String[] args) {
		
	}

}
