package rollcall.model;

public class Student {
    private String studentNo;
    private String name;
    private String className;
    private int totalCalled;
    private int totalAnswered;

    public Student(String studentNo, String name, String className) {
        this.studentNo = studentNo;
        this.name = name;
        this.className = className;
    }

    public double getAnswerRate() {
        if (totalCalled == 0) return 0.0;
        return (double) totalAnswered / totalCalled * 100;
    }

    public String getStudentNo() { return studentNo; }
    public String getName() { return name; }
    public String getClassName() { return className; }
    public int getTotalCalled() { return totalCalled; }
    public void incrementCalled() { totalCalled++; }
    public int getTotalAnswered() { return totalAnswered; }
    public void incrementAnswered() { totalAnswered++; }
}
