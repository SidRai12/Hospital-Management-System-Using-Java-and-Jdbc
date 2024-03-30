package Hospital_management;


	import java.sql.*;
	import java.util.Scanner;

	public class DoctorCrud { 

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

	   
	    public static void createDoctor() throws SQLException {
	        connect();
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter Doctor Name: ");
	        String doctorName = scanner.nextLine();
	        System.out.print("Enter Specialization: ");
	        String specialization = scanner.nextLine();
	        System.out.print("Enter Gender: ");
	        String gender = scanner.nextLine();

	        String sql = "INSERT INTO DOCTOR (DNAME, SPECIALIZATION, GENDER) VALUES (?, ?, ?)";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, doctorName);
	        statement.setString(2, specialization);
	        statement.setString(3, gender);
	        statement.executeUpdate();
	        disconnect();
	        System.out.println("Doctor created successfully.");
	    }

	 
	    public static void readDoctors() throws SQLException {
	        connect();
	        String sql = "SELECT * FROM DOCTOR";
	        statement = connection.prepareStatement(sql);
	        resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	            int doctorId = resultSet.getInt("ID");
	            String doctorName = resultSet.getString("DNAME");
	            String specialization = resultSet.getString("SPECIALIZATION");
	            String gender = resultSet.getString("GENDER");
	            System.out.println("Doctor ID: " + doctorId + ", Doctor Name: " + doctorName +
	                    ", Specialization: " + specialization + ", Gender: " + gender);
	        }
	        disconnect();
	    }

	    
	    public static void updateDoctor() throws SQLException {
	        connect();
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter Doctor ID to update: ");
	        int doctorId = scanner.nextInt();
	        scanner.nextLine(); // Consume newline character
	        System.out.print("Enter New Doctor Name: ");
	        String newDoctorName = scanner.nextLine();
	        System.out.print("Enter New Specialization: ");
	        String newSpecialization = scanner.nextLine();
	        System.out.print("Enter New Gender: ");
	        String newGender = scanner.nextLine();

	        String sql = "UPDATE DOCTOR SET DNAME = ?, SPECIALIZATION = ?, GENDER = ? WHERE ID = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, newDoctorName);
	        statement.setString(2, newSpecialization);
	        statement.setString(3, newGender);
	        statement.setInt(4, doctorId);
	        statement.executeUpdate();
	        disconnect();
	        System.out.println("Doctor updated successfully.");
	    }

	    
	    public static void deleteDoctor() throws SQLException {
	        connect();
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Enter Doctor ID to delete: ");
	        int doctorId = scanner.nextInt();

	        String sql = "DELETE FROM DOCTOR WHERE ID = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, doctorId);
	        statement.executeUpdate();
	        disconnect();
	        System.out.println("Doctor deleted successfully.");
	    }

	  
	    public static void main(String[] args) {
	        try {
	            Scanner scanner = new Scanner(System.in);
	            while (true) {
	                System.out.println("Choose an option:");
	                System.out.println("1. Create Doctor");
	                System.out.println("2. Read Doctors");
	                System.out.println("3. Update Doctor");
	                System.out.println("4. Delete Doctor");
	                System.out.println("5. Exit");
	                int option = scanner.nextInt();
	                switch (option) {
	                    case 1:
	                        createDoctor();
	                        break;
	                    case 2:
	                        readDoctors();
	                        break;
	                    case 3:
	                        updateDoctor();
	                        break;
	                    case 4:
	                        deleteDoctor();
	                        break;
	                    case 5:
	                        System.out.println("Exiting...");
	                        System.exit(0);
	                    default:
	                        System.out.println("Invalid option. Please choose again.");
	                        main(null);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            
	        }
	    }
	}


