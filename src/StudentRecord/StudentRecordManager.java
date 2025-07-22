package StudentRecord;

import java.util.*;
import java.io.*;

public class StudentRecordManager {
		    static class Student {
		        String name;
		        int roll;
		        double gpa;

		        Student(int roll, String name, double gpa) {
		            this.roll = roll;
		            this.name = name;
		            this.gpa = gpa;
		        }

		        String getGrade() {
		            if (gpa >= 9) return "A";
		            else if (gpa >= 8) return "B";
		            else if (gpa >= 7) return "C";
		            else if (gpa >= 6) return "D";
		            else return "F";
		        }

		        @Override
		        public String toString() {
		            return roll + "," + name + "," + gpa + "," + getGrade();
		        }
		    }

		    static Map<Integer, Student> records = new HashMap<>();
		    static final String FILE_NAME = "students.csv";

		    public static void main(String[] args) {
		        Scanner sc = new Scanner(System.in);
		        if (!login(sc)) {
		            System.out.println("Access denied.");
		            return;
		        }
		        loadFromFile();
		        int choice;
		        do {
		            System.out.println("\n===== Student Record Menu =====");
		            System.out.println("1. Add Student\n2. View All\n3. Search by Roll\n4. Update GPA\n5. Delete Student\n6. Export to File\n7. Sort by Name\n8. Sort by GPA\n9. Show Analytics\n10. Exit");
		            System.out.print("Enter choice: ");
		            choice = sc.nextInt();
		            sc.nextLine();

		            switch (choice) {
		                case 1 -> addStudent(sc);
		                case 2 -> viewAll();
		                case 3 -> searchStudent(sc);
		                case 4 -> updateGPA(sc);
		                case 5 -> deleteStudent(sc);
		                case 6 -> exportToFile();
		                case 7 -> sortByName();
		                case 8 -> sortByGPA();
		                case 9 -> showAnalytics();
		                case 10 -> System.out.println("Exiting...");
		                default -> System.out.println("Invalid choice.");
		            }
		        } while (choice != 10);
		        sc.close();
		    }

		    static boolean login(Scanner sc) {
		        System.out.println("Admin Login");
		        System.out.print("Username: ");
		        String user = sc.nextLine();
		        System.out.print("Password: ");
		        String pass = sc.nextLine();
		        return user.equals("101604") && pass.equals("241619");
		    }

		    static void addStudent(Scanner sc) {
		        System.out.print("Enter roll number: ");
		        int roll = sc.nextInt();
		        sc.nextLine();
		        System.out.print("Enter name: ");
		        String name = sc.nextLine();
		        System.out.print("Enter GPA: ");
		        double gpa = sc.nextDouble();
		        records.put(roll, new Student(roll, name, gpa));
		        System.out.println("Student added.");
		    }

		    static void viewAll() {
		        if (records.isEmpty()) {
		            System.out.println("No records found.");
		            return;
		        }
		        System.out.println("\nRoll\tName\tGPA\tGrade");
		        for (Student s : records.values()) {
		            System.out.println(s.roll + "\t" + s.name + "\t" + s.gpa + "\t" + s.getGrade());
		        }
		    }

		    static void searchStudent(Scanner sc) {
		        System.out.print("Enter roll number to search: ");
		        int roll = sc.nextInt();
		        Student s = records.get(roll);
		        if (s != null) {
		            System.out.println("Found: " + s.roll + ", " + s.name + ", " + s.gpa + ", Grade: " + s.getGrade());
		        } else {
		            System.out.println("Student not found.");
		        }
		    }

		    static void updateGPA(Scanner sc) {
		        System.out.print("Enter roll number to update: ");
		        int roll = sc.nextInt();
		        sc.nextLine();
		        Student s = records.get(roll);
		        if (s != null) {
		            System.out.print("Enter new GPA: ");
		            double gpa = sc.nextDouble();
		            s.gpa = gpa;
		            System.out.println("GPA updated.");
		        } else {
		            System.out.println("Student not found.");
		        }
		    }

		    static void deleteStudent(Scanner sc) {
		        System.out.print("Enter roll number to delete: ");
		        int roll = sc.nextInt();
		        if (records.remove(roll) != null) {
		            System.out.println("Student deleted.");
		        } else {
		            System.out.println("Student not found.");
		        }
		    }

		    static void exportToFile() {
		        try (FileWriter writer = new FileWriter(FILE_NAME)) {
		            writer.write("Roll,Name,GPA,Grade\n");
		            for (Student s : records.values()) {
		                writer.write(s.toString() + "\n");
		            }
		            System.out.println("Exported to " + FILE_NAME);
		        } catch (IOException e) {
		            System.out.println("Error writing file: " + e.getMessage());
		        }
		    }

		    static void loadFromFile() {
		        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
		            String line = reader.readLine();
		            while ((line = reader.readLine()) != null) {
		                String[] parts = line.split(",");
		                int roll = Integer.parseInt(parts[0]);
		                String name = parts[1];
		                double gpa = Double.parseDouble(parts[2]);
		                records.put(roll, new Student(roll, name, gpa));
		            }
		            System.out.println("Loaded records from " + FILE_NAME);
		        } catch (IOException e) {
		            System.out.println("No previous records found.");
		        }
		    }

		    static void sortByName() {
		        List<Student> list = new ArrayList<>(records.values());
		        list.sort(Comparator.comparing(s -> s.name.toLowerCase()));
		        System.out.println("\nSorted by Name:");
		        for (Student s : list) {
		            System.out.println(s.roll + ", " + s.name + ", GPA: " + s.gpa + ", Grade: " + s.getGrade());
		        }
		    }

		    static void sortByGPA() {
		        List<Student> list = new ArrayList<>(records.values());
		        list.sort((a, b) -> Double.compare(b.gpa, a.gpa));
		        System.out.println("\nSorted by GPA:");
		        for (Student s : list) {
		            System.out.println(s.roll + ", " + s.name + ", GPA: " + s.gpa + ", Grade: " + s.getGrade());
		        }
		    }

		   static void showAnalytics() {
		    if (records.isEmpty()) {
		        System.out.println("No data to analyze.");
		        return;
		    }

		    List<Student> sorted = new ArrayList<>(records.values());
		    sorted.sort((a, b) -> Double.compare(b.gpa, a.gpa));

		    double totalGPA = 0;
		    for (Student s : sorted) totalGPA += s.gpa;
		    double avgGPA = totalGPA / sorted.size();

		    Student lowest = sorted.get(0);
		    Student topper = sorted.get(sorted.size() - 1);
		    Student middle = sorted.get(sorted.size() / 2);

		    // Console output
		    System.out.printf("\nAverage GPA: %.2f\n", avgGPA);
		    System.out.println("Topper: " + topper.name + " (Roll: " + topper.roll + ", GPA: " + topper.gpa + ")");
		    System.out.println("Lowest: " + lowest.name + " (Roll: " + lowest.roll + ", GPA: " + lowest.gpa + ")");
		    System.out.println("Middle: " + middle.name + " (Roll: " + middle.roll + ", GPA: " + middle.gpa + ")");

		    System.out.println("\n--- GPA Rankings ---");
		    sorted.sort((a, b) -> Double.compare(b.gpa, a.gpa));
		    for (Student s : sorted) {
		        System.out.printf("Roll: %d, Name: %s, GPA: %.2f, Grade: %s\n",
		                s.roll, s.name, s.gpa, s.getGrade());
		    }

		    // Write to file
		    try (PrintWriter out = new PrintWriter("analytics_report.txt")) {
		        out.printf("Average GPA: %.2f\n", avgGPA);
		        out.println("Topper: " + topper.name + " (Roll: " + topper.roll + ", GPA: " + topper.gpa + ")");
		        out.println("Lowest: " + lowest.name + " (Roll: " + lowest.roll + ", GPA: " + lowest.gpa + ")");
		        out.println("Middle: " + middle.name + " (Roll: " + middle.roll + ", GPA: " + middle.gpa + ")");
		        out.println("\n--- GPA Rankings ---");
		        for (Student s : sorted) {
		            out.printf("Roll: %d, Name: %s, GPA: %.2f, Grade: %s\n",
		                    s.roll, s.name, s.gpa, s.getGrade());
		        }
		        System.out.println("\nAnalytics saved to 'analytics_report.txt'");
		    } catch (IOException e) {
		        System.out.println("Error writing analytics file: " + e.getMessage());
		    }
		}

		

	}


