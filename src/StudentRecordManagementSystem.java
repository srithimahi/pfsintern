import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentRecordManagementSystem {
    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nThis is a Student Roll Management System");
            System.out.println("1. Add a student's record");
            System.out.println("2. View all students records");
            System.out.println("3. Update a student's record");
            System.out.println("4. Delete a student's record");
            System.out.println("5. Exit");
            System.out.print("Pick a number to choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudentRecord(scanner);
                    break;
                case 2:
                    viewStudentRecords();
                    break;
                case 3:
                    updateStudentRecord(scanner);
                    break;
                case 4:
                    deleteStudentRecord(scanner);
                    break;
                case 5:
                    System.out.println("The system has been exited out from");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addStudentRecord(Scanner scanner) {
        System.out.print("Enter Roll Number: ");
        String rollNumber = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Grade: ");
        String grade = scanner.nextLine();

        Student student = new Student(rollNumber, name, grade);
        saveStudentToFile(student);
        System.out.println("The student's roll added successfully.");
    }

    private static void viewStudentRecords() {
        List<Student> students = getAllStudentsFromFile();
        if (students.isEmpty()) {
            System.out.println("No records found.");
        } else {
            System.out.println("\nExisting Student's roll");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void updateStudentRecord(Scanner scanner) {
        List<Student> students = getAllStudentsFromFile();
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.print("Enter the Roll Number of the student to update: ");
        String rollNumber = scanner.nextLine();
        Student studentToUpdate = null;

        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                studentToUpdate = student;
                break;
            }
        }

        if (studentToUpdate != null) {
            System.out.print("Enter new name (or press Enter to keep it unchanged): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                studentToUpdate.setName(newName);
            }

            System.out.print("Enter new grade (or press Enter to keep it unchanged): ");
            String newGrade = scanner.nextLine();
            if (!newGrade.isEmpty()) {
                studentToUpdate.setGrade(newGrade);
            }

            saveAllStudentsToFile(students);
            System.out.println("Student record updated successfully.");
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private static void deleteStudentRecord(Scanner scanner) {
        List<Student> students = getAllStudentsFromFile();
        if (students.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.print("Enter the Roll Number of the student to delete: ");
        String rollNumber = scanner.nextLine();
        boolean found = students.removeIf(student -> student.getRollNumber().equals(rollNumber));

        if (found) {
            saveAllStudentsToFile(students);
            System.out.println("Student record deleted successfully.");
        } else {
            System.out.println("Student with Roll Number " + rollNumber + " not found.");
        }
    }

    private static void saveStudentToFile(Student student) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(student.getRollNumber() + "," + student.getName() + "," + student.getGrade());
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Error while saving student record: " + e.getMessage());
        }
    }

    private static List<Student> getAllStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_NAME);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    students.add(new Student(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading student records: " + e.getMessage());
        }
        return students;
    }

    private static void saveAllStudentsToFile(List<Student> students) {
        try (FileWriter writer = new FileWriter(FILE_NAME);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Student student : students) {
                bufferedWriter.write(student.getRollNumber() + "," + student.getName() + "," + student.getGrade());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving student records: " + e.getMessage());
        }
    }
}
