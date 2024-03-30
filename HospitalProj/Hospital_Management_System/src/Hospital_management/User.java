package Hospital_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;

public class User {

    private static String url = "jdbc:mysql://localhost:3306/HOSPITAL";
    private static String user = "root";
    private static String pass = "root";
	 private static Connection connection;

	   
	    static {
	        try {
	            connection = DriverManager.getConnection(url, user, pass);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 public static boolean userLogin() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter username:");
	        String username = scanner.nextLine();
	        System.out.println("Enter password:");
	        String password = scanner.nextLine();

	        String selectQuery = "SELECT * FROM users WHERE Username = ? AND Password = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    System.out.println("Login successful!");
	                    UserTask();
	                    return true; 
	                } else {
	                    System.out.println("Invalid username or password.");
	                    userLogin();
	                    return false; 
	                    
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error during login: " + e.getMessage());
	            return false; 
	        }
	    }

	    public static boolean UserRegistration() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter username:");
	        String username = scanner.nextLine();
	        System.out.println("Enter Password:");
	        String password = scanner.nextLine();
	        System.out.println("Enter Email:");
	        String email = scanner.nextLine();
	        
	      
	        String insertQuery = "INSERT INTO users (Username, Password, Email) VALUES (?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            preparedStatement.setString(1, username);
	            preparedStatement.setString(2, password);
	            preparedStatement.setString(3, email);
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("User registered successfully!");
	                UserTask();
	                return true;
	            } else {
	                System.out.println("Failed to register user.");
	                UserRegistration();
	                return false;
	            }
	        } catch (SQLException e) {
	            System.err.println("Error registering user: " + e.getMessage());
	            return false;
	        }
	    }
	    public static void modifyUserData() {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter your username:");
	        String username = scanner.nextLine();

	        
	        if (!userExists(username)) {
	            System.out.println("User does not exist.");
	            return;
	        }

	        System.out.println("Select the data you want to modify:");
	        System.out.println("1. Password");
	        System.out.println("2. Email");
	        System.out.println("Enter your choice:");
	        int choice = scanner.nextInt();
	        scanner.nextLine(); 

	        switch (choice) {
	            case 1:
	                modifyPassword(username);
	                break;
	            case 2:
	                modifyEmail(username);
	                break;
	            default:
	                System.out.println("Invalid choice.");
	        }
	    }

	    private static void modifyPassword(String username) {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter new password:");
	        String newPassword = scanner.nextLine();

	        String updateQuery = "UPDATE users SET Password = ? WHERE Username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
	            preparedStatement.setString(1, newPassword);
	            preparedStatement.setString(2, username);
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Password updated successfully!");
	            } else {
	                System.out.println("Failed to update password.");
	            }
	        } catch (SQLException e) {
	            System.err.println("Error updating password: " + e.getMessage());
	        }
	    }

	    private static void modifyEmail(String username) {
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Enter new email:");
	        String newEmail = scanner.nextLine();

	        String updateQuery = "UPDATE users SET Email = ? WHERE Username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
	            preparedStatement.setString(1, newEmail);
	            preparedStatement.setString(2, username);
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Email updated successfully!");
	            } else {
	                System.out.println("Failed to update email.");
	            }
	        } catch (SQLException e) {
	            System.err.println("Error updating email: " + e.getMessage());
	        }
	    }

	    private static boolean userExists(String username) {
	       
	        String query = "SELECT COUNT(*) FROM users WHERE Username = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, username);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count > 0;
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking user existence: " + e.getMessage());
	        }
	        return false;
	    }

	    public static void UserTask()
	    
	    {  try {
	    	System.out.println("\nSelect the operation you want to perform:");
        System.out.println("1. View Doctor");
        System.out.println("2. Book Appointment");
        System.out.println("3. Modify The Login Details");
        System.out.println("4. Exit");
        
         Scanner sc = new Scanner(System.in);
         int choice = sc.nextInt();
         switch(choice)
         {
         case 1:
        	 viewDoctor();
        	 break;
         case 2:
        	 bookAppointment();
        	 break;
         case 3:
        	 modifyUserData();
        	 break;
         case 4:
        	Admin.display();
        	 break;
        default:
        	 System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        	 UserTask();
         }
	    }
	    catch(Exception e)
	    {
	    	System.err.println("IT should be only integer");
	    	UserTask();
	    }
	    }
	    

	    public static void viewDoctor() {
	        try {
	            System.out.println("Doctors available:");
	            String sql = "SELECT ID, DNAME, SPECIALIZATION FROM DOCTOR";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
	                 ResultSet rs = preparedStatement.executeQuery()) {
	                while (rs.next()) {
	                    int id = rs.getInt("ID");
	                    String name = rs.getString("DNAME");
	                    String specialization = rs.getString("SPECIALIZATION");
	                    System.out.println("ID: " + id + ", Name: " + name + ", Specialization: " + specialization);
	                }
	            }
	            
	            UserTask();
	        } catch (SQLException e) {
	            System.err.println("Error viewing doctors: " + e.getMessage());
	            UserTask();
	            
	        }
	    }


	    public static void bookAppointment() {
	        try {
	            Scanner scanner = new Scanner(System.in);
	            System.out.println("Enter patient ID:");
	            int patientId = scanner.nextInt();
	            System.out.println("Enter doctor ID:");
	            int doctorId = scanner.nextInt();
	            scanner.nextLine(); // Consume newline
	            System.out.println("Enter appointment date (YYYY-MM-DD):");
	            String appointmentDate = scanner.nextLine();
	            if (!isDoctorAvailable(doctorId, appointmentDate)) {
	                System.out.println("Doctor is not available on this date. Please choose another date or doctor.");
	                UserTask();
	                return;
	            }
	            String insertQuery = "INSERT INTO APPOIMENTS (PID, DID, APP_DATE) VALUES (?, ?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	                preparedStatement.setInt(1, patientId);
	                preparedStatement.setInt(2, doctorId);
	                preparedStatement.setString(3, appointmentDate);
	                int rowsAffected = preparedStatement.executeUpdate();
	                if (rowsAffected > 0) {
	                    System.out.println("Appointment booked successfully!");
	                    UserTask();
	                } else {
	                    System.out.println("Failed to book appointment.");
	                    UserTask();
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error booking appointment: " + e.getMessage());
	            UserTask();
	        }
	    }
	    public static boolean isDoctorAvailable(int doctorId, String appointmentDate) {
	        try {
	            String query = "SELECT COUNT(*) FROM APPOIMENTS WHERE DID = ? AND APP_DATE = ?";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                preparedStatement.setInt(1, doctorId);
	                preparedStatement.setString(2, appointmentDate);
	                ResultSet resultSet = preparedStatement.executeQuery();
	                if (resultSet.next()) {
	                    int count = resultSet.getInt(1);
	                    return count == 0; 
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking doctor availability: " + e.getMessage());
	        }
	        return false; 
	    }


	    public static void main(String[] args) {
			
		}

}
