package src.eduassess;

public class Question {
    private String id;
    private String questionText;
    private String[] options;
    private String answer;

    public Question(String id, String questionText, String[] options, String answer) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.answer = answer;
    }

    public String getId() { return id; }
    public String getQuestionText() { return questionText; }
    public String[] getOptions() { return options; }
    public String getAnswer() { return answer; }

    public String toCSV() {
        return String.join(",", id, questionText, options[0], options[1], options[2], options[3], answer);
    }

    public static Question fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 7) return null;
        String[] options = new String[]{parts[2], parts[3], parts[4], parts[5]};
        return new Question(parts[0], parts[1], options, parts[6]);
    }
}
