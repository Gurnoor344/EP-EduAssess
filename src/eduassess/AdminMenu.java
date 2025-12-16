package src.eduassess;


import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void show() {

        boolean running = true;

        while (running) {
            System.out.println("\n--- ADMIN QUESTION MANAGEMENT ---");
            System.out.println("1. Add Question");
            System.out.println("2. View Questions");
            System.out.println("3. Delete Question");
            System.out.println("4. Back to Admin Dashboard");
            System.out.print("Choose option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> addQuestion();
                case "2" -> viewQuestions();
                case "3" -> deleteQuestion();
                case "4" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ================= ADD QUESTION =================
    private static void addQuestion() {

        System.out.print("Question ID: ");
        String id = sc.nextLine();

        System.out.print("Question Text: ");
        String text = sc.nextLine();

        String[] options = new String[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("Option " + (i + 1) + ": ");
            options[i] = sc.nextLine();
        }

        System.out.print("Correct Answer (exact text): ");
        String answer = sc.nextLine();

        Question q = new Question(id, text, options, answer);
        CSVHandler.addQuestion(q);

        System.out.println("✅ Question added successfully.");
    }

    // ================= VIEW QUESTIONS =================
    private static void viewQuestions() {

        List<Question> questions = CSVHandler.getQuestions();

        if (questions.isEmpty()) {
            System.out.println("No questions available.");
            return;
        }

        for (Question q : questions) {
            System.out.println("\nID: " + q.getId());
            System.out.println("Q: " + q.getQuestionText());
            String[] opts = q.getOptions();
            for (int i = 0; i < opts.length; i++) {
                System.out.println((i + 1) + ". " + opts[i]);
            }
            System.out.println("Answer: " + q.getAnswer());
        }
    }

    // ================= DELETE QUESTION =================
    private static void deleteQuestion() {

        List<Question> questions = CSVHandler.getQuestions();

        if (questions.isEmpty()) {
            System.out.println("No questions to delete.");
            return;
        }

        for (Question q : questions) {
            System.out.println("ID: " + q.getId() + " | " + q.getQuestionText());
        }

        System.out.print("Enter Question ID to delete: ");
        String id = sc.nextLine();

        boolean removed = questions.removeIf(q -> q.getId().equals(id));

        if (removed) {
            CSVHandler.overwriteQuestions(questions);
            System.out.println("✅ Question deleted.");
        } else {
            System.out.println("❌ Question not found.");
        }
    }
}
