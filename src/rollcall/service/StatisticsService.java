package rollcall.service;

import rollcall.model.Student;
import java.util.*;

public class StatisticsService {

    private List<Student> students = new ArrayList<>();

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Map<String, Object> getSummary() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalStudents", students.size());
        int totalCalls = 0, totalAnswered = 0;
        for (Student s : students) {
            totalCalls += s.getTotalCalled();
            totalAnswered += s.getTotalAnswered();
        }
        map.put("totalCalls", totalCalls);
        map.put("totalAnswered", totalAnswered);
        double rate = totalCalls == 0 ? 0 : (double) totalAnswered / totalCalls * 100;
        map.put("overallRate", String.format("%.1f", rate));
        return map;
    }
}
