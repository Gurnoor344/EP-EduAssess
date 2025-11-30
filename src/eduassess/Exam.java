package src.eduassess;

import java.util.List;
import java.util.Scanner;

public class Exam {

    public static void takeExam(String studentID, String studentName) {
        List<Question> questions = CSVHandler.getQuestions();

        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int score = 0;

        System.out.println("\n=== Exam: " + studentName + " ===");

        for (Question q : questions) {
            System.out.println("\nQ" + q.getId() + ": " + q.getQuestionText());
            String[] opts = q.getOptions();
            for (int i = 0; i < opts.length; i++) {
                System.out.println((i + 1) + ". " + opts[i]);
            }

            int choice = 0;
            while (true) {
                System.out.print("Answer (1-4): ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice >= 1 && choice <= 4) break;
                } catch (Exception e) {}
                System.out.println("Invalid choice. Try again.");
            }

            if (opts[choice - 1].equalsIgnoreCase(q.getAnswer())) score++;
        }

        System.out.println("\nExam Completed. Score: " + score + "/" + questions.size());

        CSVHandler.addResult(new String[]{studentID, studentName, String.valueOf(score), String.valueOf(questions.size())});
    }

    public static void viewResults() {
        List<String[]> results = CSVHandler.getResults();
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        System.out.printf("%-5s %-20s %-5s %-5s%n", "ID", "Name", "Score", "Total");
        for (String[] r : results) {
            System.out.printf("%-5s %-20s %-5s %-5s%n", r[0], r[1], r[2], r[3]);
        }
    }
}
