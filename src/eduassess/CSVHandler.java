package src.eduassess;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {

    private static final String STUDENT_FILE = "src/data/students.csv";
    private static final String QUESTION_FILE = "src/data/questions.csv";
    private static final String RESULT_FILE = "src/data/results.csv";

    // --- Student Methods ---
    public static void addStudent(Student student) {
        writeCSV(STUDENT_FILE, student.toCSV());
    }

    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (String line : readCSV(STUDENT_FILE)) {
            Student s = Student.fromCSV(line);
            if (s != null) students.add(s);
        }
        return students;
    }

    public static void updateStudents(List<Student> students) {
        List<String> data = new ArrayList<>();
        for (Student s : students) data.add(s.toCSV());
        overwriteCSV(STUDENT_FILE, data);
    }

    public static boolean studentExists(String id, String name) {
    List<Student> students = getStudents();
    for (Student s : students) {
        if (s.getId().equals(id) &&
            s.getName().equalsIgnoreCase(name)) {
            return true;
        }
    }
    return false;
}
    // --- Question Methods ---
    public static void addQuestion(Question q) {
        writeCSV(QUESTION_FILE, q.toCSV());
    }

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        for (String line : readCSV(QUESTION_FILE)) {
            Question q = Question.fromCSV(line);
            if (q != null) questions.add(q);
        }
        return questions;
    }

    // --- Overwrite Questions ---
public static void overwriteQuestions(List<Question> questions) {
    List<String> data = new ArrayList<>();
    for (Question q : questions) data.add(q.toCSV());
    overwriteCSV(QUESTION_FILE, data);
}

    // --- Result Methods ---
    public static void addResult(String[] result) {
        writeCSV(RESULT_FILE, String.join(",", result));
    }

    public static List<String[]> getResults() {
        List<String[]> results = new ArrayList<>();
        for (String line : readCSV(RESULT_FILE)) {
            results.add(line.split(","));
        }
        return results;
    }

    // --- Generic CSV Read ---
    private static List<String> readCSV(String filePath) {
        List<String> data = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return data;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) data.add(line);
        } catch (IOException e) {
            System.out.println("Error reading file " + filePath + ": " + e.getMessage());
        }
        return data;
    }

    // --- Generic CSV Append ---
    private static void writeCSV(String filePath, String data) {
        try { new File(filePath).getParentFile().mkdirs(); } catch (Exception ignored) {}
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pw.println(data);
        } catch (IOException e) {
            System.out.println("Error writing file " + filePath + ": " + e.getMessage());
        }
    }

    // --- Generic CSV Overwrite ---
    private static void overwriteCSV(String filePath, List<String> data) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (String row : data) pw.println(row);
        } catch (IOException e) {
            System.out.println("Error updating file " + filePath + ": " + e.getMessage());
        }
    }
}
