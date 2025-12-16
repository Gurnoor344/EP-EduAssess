// package src.eduassess;

// import java.util.List;
// import java.util.Scanner;

// public class Exam {

//     public static void takeExam(String studentID, String studentName) {
//         List<Question> questions = CSVHandler.getQuestions();

//         if (questions.isEmpty()) {
//             System.out.println("No questions available.");
//             return;
//         }

//         Scanner sc = new Scanner(System.in);
//         int score = 0;

//         System.out.println("\n=== Exam: " + studentName + " ===");

//         for (Question q : questions) {
//             System.out.println("\nQ" + q.getId() + ": " + q.getQuestionText());
//             String[] opts = q.getOptions();
//             for (int i = 0; i < opts.length; i++) {
//                 System.out.println((i + 1) + ". " + opts[i]);
//             }

//             int choice = 0;
//             while (true) {
//                 System.out.print("Answer (1-4): ");
//                 try {
//                     choice = Integer.parseInt(sc.nextLine());
//                     if (choice >= 1 && choice <= 4) break;
//                 } catch (Exception e) {}
//                 System.out.println("Invalid choice. Try again.");
//             }

//             if (opts[choice - 1].equalsIgnoreCase(q.getAnswer())) score++;
//         }

//         System.out.println("\nExam Completed. Score: " + score + "/" + questions.size());

//         CSVHandler.addResult(new String[]{studentID, studentName, String.valueOf(score), String.valueOf(questions.size())});
//     }

//     public static void viewResults() {
//         List<String[]> results = CSVHandler.getResults();
//         if (results.isEmpty()) {
//             System.out.println("No results found.");
//             return;
//         }

//         System.out.printf("%-5s %-20s %-5s %-5s%n", "ID", "Name", "Score", "Total");
//         for (String[] r : results) {
//             System.out.printf("%-5s %-20s %-5s %-5s%n", r[0], r[1], r[2], r[3]);
//         }
//     }
// }


package src.eduassess;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Exam {

    private static volatile boolean timeUp = false;

    // ===================== STUDENT TAKES EXAM =====================
    public static void takeExam(String studentID, String studentName) {

        List<Question> questions = CSVHandler.getQuestions();

        if (questions == null || questions.isEmpty()) {
            System.out.println("❌ No questions available. Contact Admin.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int score = 0;

        int totalTimeSeconds = questions.size() * 15; // 15 sec per question

        System.out.println("\n================================");
        System.out.println("      EXAM STARTED");
        System.out.println(" Student: " + studentName);
        System.out.println(" Time Limit: " + totalTimeSeconds + " seconds");
        System.out.println("================================");

        // ===================== TIMER =====================
        // ===================== TIMER =====================
Timer timer = new Timer();
TimerTask countdownTask = new TimerTask() {

    int remaining = totalTimeSeconds;

    @Override
    public void run() {

        if (remaining <= 0) {
            timeUp = true;
            System.out.println("\n⏰ TIME IS UP! Auto submitting exam...");
            timer.cancel();
            return;
        }

        System.out.print("\r⏳ Time Left: " + remaining + " seconds ");
        remaining--;
    }
};

timer.scheduleAtFixedRate(countdownTask, 0, 1000);


        // ===================== EXAM LOOP =====================
        for (Question q : questions) {

            if (timeUp) break;

            System.out.println("\nQ" + q.getId() + ". " + q.getQuestionText());

            String[] options = q.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            Integer choice = getValidAnswer(sc);
            if (choice == null) break; // time up during input

            if (options[choice - 1].equalsIgnoreCase(q.getAnswer())) {
                score++;
            }
        }

        timer.cancel(); // stop timer

        System.out.println("\n================================");
        System.out.println(" EXAM COMPLETED");
        System.out.println(" Score: " + score + " / " + questions.size());
        System.out.println("================================");

        // Save result
        CSVHandler.addResult(new String[]{
                studentID,
                studentName,
                String.valueOf(score),
                String.valueOf(questions.size())
        });
    }

    // ===================== INPUT VALIDATION =====================
    private static Integer getValidAnswer(Scanner sc) {

        while (!timeUp) {
            System.out.print("Answer (1-4): ");
            try {
                String input = sc.nextLine();
                if (timeUp) return null;

                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 4) {
                    return choice;
                }
            } catch (Exception ignored) {}

            System.out.println("❌ Invalid input. Please enter 1 to 4.");
        }
        return null;
    }

    // ===================== VIEW ALL RESULTS (ADMIN) =====================
    public static void viewResults() {

        List<String[]> results = CSVHandler.getResults();

        if (results == null || results.isEmpty()) {
            System.out.println("❌ No results found.");
            return;
        }

        System.out.println("\n=========== EXAM RESULTS ===========");
        System.out.printf("%-8s %-20s %-8s %-8s%n",
                "ID", "Name", "Score", "Total");

        for (String[] r : results) {
            System.out.printf("%-8s %-20s %-8s %-8s%n",
                    r[0], r[1], r[2], r[3]);
        }
    }
}

