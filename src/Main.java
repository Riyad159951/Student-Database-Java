import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/myjavadb";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Choose an option:");
            System.out.println("1. Add new student");
            System.out.println("2. Search by Roll No");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 1) {
                // Insert data
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Roll No: ");
                String roll = sc.nextLine();

                // Semester
                System.out.print("Enter Semester: ");
                String semester = sc.nextLine();

                // Department (Radio type)
                System.out.println("Choose Department: ");
                System.out.println("1. CSE\n2. EEE\n3. Civil");
                int deptChoice = sc.nextInt();
                sc.nextLine();
                String department = switch (deptChoice) {
                    case 1 -> "CSE";
                    case 2 -> "EEE";
                    case 3 -> "Civil";
                    default -> "Unknown";
                };

                // DOB Dropdown Style
                System.out.print("Enter Birth Day (1-31): ");
                String day = sc.nextLine();

                System.out.print("Enter Birth Month (1-12): ");
                String month = sc.nextLine();

                System.out.print("Enter Birth Year (e.g., 2005): ");
                String year = sc.nextLine();

                String dob = day + "/" + month + "/" + year;

                // Insert into DB
                String query = "INSERT INTO students (name, roll, semester, department, dob) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, roll);
                stmt.setString(3, semester);
                stmt.setString(4, department);
                stmt.setString(5, dob);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("✅ Student added successfully!");
                }

                stmt.close();

            } else if (choice == 2) {
                // Search student
                System.out.print("Enter Roll No to search: ");
                String searchRoll = sc.nextLine();

                String query = "SELECT * FROM students WHERE roll = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, searchRoll);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Student Found:");
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Roll: " + rs.getString("roll"));
                    System.out.println("Semester: " + rs.getString("semester"));
                    System.out.println("Department: " + rs.getString("department"));
                    System.out.println("Date of Birth: " + rs.getString("dob"));
                } else {
                    System.out.println("❌ Student not found.");
                }

                rs.close();
                stmt.close();
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
