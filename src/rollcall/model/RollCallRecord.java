package rollcall.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RollCallRecord {
    private String studentNo;
    private String courseName;
    private boolean answered;
    private String callTime;

    public RollCallRecord(String studentNo, String courseName, boolean answered) {
        this.studentNo = studentNo;
        this.courseName = courseName;
        this.answered = answered;
        this.callTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getStudentNo() { return studentNo; }
    public String getCourseName() { return courseName; }
    public boolean isAnswered() { return answered; }
    public String getCallTime() { return callTime; }
}
