package rollcall.view;

import rollcall.model.Student;
import rollcall.service.StatisticsService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatisticsPanel extends JPanel {

    private StatisticsService service;
    private JTextArea textArea;

    public StatisticsPanel(StatisticsService service) {
        this.service = service;
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 40);
        textArea.setFont(new Font("宋体", Font.PLAIN, 14));
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton refresh = new JButton("刷新数据");
        refresh.addActionListener(e -> refreshAll());
        add(refresh, BorderLayout.SOUTH);

        refreshAll();
    }

    private void refreshAll() {
        Map<String, Object> s = service.getSummary();
        StringBuilder sb = new StringBuilder();
        sb.append("===== 统计汇总 =====\n\n");
        sb.append("学生总数：").append(s.get("totalStudents")).append("\n");
        sb.append("总点名次数：").append(s.get("totalCalls")).append("\n");
        sb.append("总答出次数：").append(s.get("totalAnswered")).append("\n");
        sb.append("总体成功率：").append(s.get("overallRate")).append("%\n");
        textArea.setText(sb.toString());
    }
}
