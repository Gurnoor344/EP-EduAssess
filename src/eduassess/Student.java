package src.eduassess;

public class Student {
    private String id;
    private String name;
    private String email;
    private String password;

    public Student(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // Convert to CSV row
    public String toCSV() {
        return String.join(",", id, name, email, password);
    }

    // Convert from CSV line
    public static Student fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 4) return null;
        return new Student(parts[0], parts[1], parts[2], parts[3]);
    }

    @Override
    public String toString() {
        return "Student[ID=" + id + ", Name=" + name + ", Email=" + email + "]";
    }
}
