package rollcall.service;

import rollcall.model.RollCallRecord;
import rollcall.model.Student;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {

    private List<Student> students = new ArrayList<>();
    private List<RollCallRecord> records = new ArrayList<>();

    public void setStudents(List<Student> students) { this.students = students; }
    public void setRecords(List<RollCallRecord> records) { this.records = records; }

    public Map<String, Object> getSummary() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalStudents", students.size());
        map.put("totalCalls", records.size());
        long answered = records.stream().filter(RollCallRecord::isAnswered).count();
        map.put("totalAnswered", answered);
        double rate = records.isEmpty() ? 0 : (double) answered / records.size() * 100;
        map.put("overallRate", String.format("%.1f", rate));
        return map;
    }

    /** 按被点名次数降序排列 */
    public List<Student> getRankedStudents() {
        return students.stream()
                .sorted((a, b) -> b.getTotalCalled() - a.getTotalCalled())
                .collect(Collectors.toList());
    }
}
