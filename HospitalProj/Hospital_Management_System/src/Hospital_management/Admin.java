package Hospital_management;

import java.sql.*;
import java.util.Scanner;

public class Admin {
    
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
    static {
        System.out.println("Welcome to the Hospital Management System");
        System.out.println("----------------------------------------");
    }
  
    public static boolean display(){
        System.out.println("1. User Login");
        System.out.println("2. Admin Login");
        System.out.println("3. User Registration");
        System.out.println("4. Exit");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your Choice: ");
        int choice = sc.nextInt();
        switch(choice) {
            case 1:
               User.userLogin();
                break;
            case 2:
               admin();
               break;
            case 3:
             User.UserRegistration();
                break;
            case 4: 
                System.out.println("Exiting Hospital Management System...");
                System.out.println("Thank you for using the Hospital Management System.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        }
        return false;
    }
   
   public static boolean admin() {
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter Username: ");
       String username = sc.nextLine();
       System.out.println("Enter Password: ");
       String password = sc.nextLine();
       
       if(username.equals("Admin") && password.equals("Admin@123")) {
           System.out.println("Admin Login Successfully.....");
           AdminTask();
           return true;
       } else {
           System.out.println("Invalid admin credentials.");
           admin();
           return false;
       }
   }


   public static void AdminTask() {
        if (connection != null) {
           
                Scanner sc = new Scanner(System.in);
                int choice;
                do {
                    System.out.println("\nSelect the operation you want to perform:");
                    System.out.println("1. Patient");
                    System.out.println("2. Doctor");
                    System.out.println("3. Appointment");
                    System.out.println("4. Exit");
                    System.out.print("Enter your choice: ");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            Patient patient = new Patient(connection);
                            patient.main(null);
                            break;
                        case 2:
                        	DoctorCrud ref1 = new DoctorCrud();
                        	ref1.main(null);
                        	break;
                        case 3:
                        	Appoinment ref = new Appoinment();
                        	ref.Appoinment();
                        case 4:
                            System.out.println("Exiting Hospital Management System...");
                            System.out.println("Thank you for using the Hospital Management System.");
                            display();
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    }
                } while (choice != 4);
            } 
         else {
            System.out.println("Failed to establish database connection.");
        }
    }
   public static void main(String[] args) {
	   display();
	
}
}

