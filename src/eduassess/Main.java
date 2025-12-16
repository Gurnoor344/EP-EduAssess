// package src.eduassess;

// import java.util.List;
// import java.util.Scanner;

// public class Main {

//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         boolean running = true;

//         while (running) {
//             System.out.println("\n=== Examination System===");
//             System.out.println("1. Add Student");
//             System.out.println("2. View Students");
//             System.out.println("3. Update Student");
//             System.out.println("4. Delete Student");
//             System.out.println("5. Take Exam");
//             System.out.println("6. View Results");
//             System.out.println("7. Exit");
//             System.out.print("Choose option: ");

//             String choice = sc.nextLine();

//             switch (choice) {
//                 case "1": addStudent(sc); break;
//                 case "2": viewStudents(); break;
//                 case "3": updateStudent(sc); break;
//                 case "4": deleteStudent(sc); break;
//                 case "5": takeExam(sc); break;
//                 case "6": Exam.viewResults(); break;
//                 case "7": running = false; break;
//                 default: System.out.println("Invalid option.");
//             }
//         }

//         sc.close();
//     }

//     private static void addStudent(Scanner sc) {
//         System.out.print("ID: "); String id = sc.nextLine();
//         System.out.print("Name: "); String name = sc.nextLine();
//         System.out.print("Email: "); String email = sc.nextLine();
//         System.out.print("Password: "); String pwd = sc.nextLine();
//         CSVHandler.addStudent(new Student(id, name, email, pwd));
//         System.out.println("Student added.");
//     }

//     private static void viewStudents() {
//         List<Student> students = CSVHandler.getStudents();
//         System.out.printf("%-5s %-20s %-25s%n", "ID", "Name", "Email");
//         for (Student s : students) {
//             System.out.printf("%-5s %-20s %-25s%n", s.getId(), s.getName(), s.getEmail());
//         }
//     }

//     private static void updateStudent(Scanner sc) {
//         System.out.print("Enter ID to update: ");
//         String id = sc.nextLine();
//         List<Student> students = CSVHandler.getStudents();
//         for (Student s : students) {
//             if (s.getId().equals(id)) {
//                 System.out.print("New Name (" + s.getName() + "): "); String name = sc.nextLine();
//                 System.out.print("New Email (" + s.getEmail() + "): "); String email = sc.nextLine();
//                 System.out.print("New Password: "); String pwd = sc.nextLine();
//                 if (!name.isEmpty()) s.setName(name);
//                 if (!email.isEmpty()) s.setEmail(email);
//                 if (!pwd.isEmpty()) s.setPassword(pwd);
//                 CSVHandler.updateStudents(students);
//                 System.out.println("Student updated.");
//                 return;
//             }
//         }
//         System.out.println("Student not found.");
//     }

//     private static void deleteStudent(Scanner sc) {
//         System.out.print("Enter ID to delete: ");
//         String id = sc.nextLine();
//         List<Student> students = CSVHandler.getStudents();
//         boolean removed = students.removeIf(s -> s.getId().equals(id));
//         if (removed) {
//             CSVHandler.updateStudents(students);
//             System.out.println("Student deleted.");
//         } else System.out.println("Student not found.");
//     }

//     private static void takeExam(Scanner sc) {
//         System.out.print("Enter Student ID: "); String id = sc.nextLine();
//         System.out.print("Enter Name: "); String name = sc.nextLine();
//         Exam.takeExam(id, name);
//     }
// }



package src.eduassess;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== EDUASSESS EXAMINATION SYSTEM ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Student Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> adminFlow();
                case "2" -> studentFlow();
                case "3" -> {
                    System.out.println("System closed.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ======================= ADMIN FLOW =======================
    private static void adminFlow() {

        System.out.print("\nAdmin Username: ");
        String user = sc.nextLine();
        System.out.print("Admin Password: ");
        String pass = sc.nextLine();

        if (!user.equals("admin") || !pass.equals("admin123")) {
            System.out.println("❌ Invalid admin credentials.");
            return;
        }

        boolean running = true;

        while (running) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View Results");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> addStudent();
                case "2" -> viewStudents();
                case "3" -> updateStudent();
                case "4" -> deleteStudent();
                case "5" -> Exam.viewResults();
                case "6" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ======================= STUDENT FLOW =======================
    private static void studentFlow() {

        System.out.print("\nStudent ID: ");
        String id = sc.nextLine();
        System.out.print("Student Name: ");
        String name = sc.nextLine();

        if (!CSVHandler.studentExists(id, name)) {
            System.out.println("❌ Student not found.");
            return;
        }

        Exam.takeExam(id, name);
    }

    // ======================= ADMIN OPERATIONS =======================
    private static void addStudent() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        CSVHandler.addStudent(new Student(id, name, email, pwd));
        System.out.println("✅ Student added.");
    }

    private static void viewStudents() {
        List<Student> students = CSVHandler.getStudents();
        System.out.printf("%-5s %-20s %-25s%n", "ID", "Name", "Email");

        for (Student s : students) {
            System.out.printf("%-5s %-20s %-25s%n",
                    s.getId(), s.getName(), s.getEmail());
        }
    }

    private static void updateStudent() {

        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine();

        List<Student> students = CSVHandler.getStudents();

        for (Student s : students) {
            if (s.getId().equals(id)) {

                System.out.print("New Name (" + s.getName() + "): ");
                String name = sc.nextLine();
                System.out.print("New Email (" + s.getEmail() + "): ");
                String email = sc.nextLine();
                System.out.print("New Password: ");
                String pwd = sc.nextLine();

                if (!name.isEmpty()) s.setName(name);
                if (!email.isEmpty()) s.setEmail(email);
                if (!pwd.isEmpty()) s.setPassword(pwd);

                CSVHandler.updateStudents(students);
                System.out.println("✅ Student updated.");
                return;
            }
        }
        System.out.println("❌ Student not found.");
    }

    private static void deleteStudent() {

        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine();

        List<Student> students = CSVHandler.getStudents();
        boolean removed = students.removeIf(s -> s.getId().equals(id));

        if (removed) {
            CSVHandler.updateStudents(students);
            System.out.println("✅ Student deleted.");
        } else {
            System.out.println("❌ Student not found.");
        }
    }
}
