package rollcall.view;

import rollcall.model.Student;
import rollcall.service.StatisticsService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * 数据统计面板 —— 汇总卡片 + 排名表 + 频次分布
 */
public class StatisticsPanel extends JPanel {

    private final StatisticsService service;
    private JLabel totalStu, totalCalls, answered, rate;
    private DefaultTableModel rankModel;
    private JProgressBar[] freqBars;
    private JLabel[] freqCounts;

    public StatisticsPanel(StatisticsService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 汇总卡片
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));
        totalStu = makeCard(cards, "学生总数", "0");
        totalCalls = makeCard(cards, "总点名次数", "0");
        answered = makeCard(cards, "总答出次数", "0");
        rate = makeCard(cards, "总体成功率", "0%");
        add(cards, BorderLayout.NORTH);

        // 排名表
        String[] cols = {"排名", "学号", "姓名", "班级", "被点名", "答出", "成功率"};
        rankModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(rankModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("宋体", Font.BOLD, 13));
        table.setFont(new Font("宋体", Font.PLAIN, 13));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("学生点名统计排名"));
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // 频次分布
        JPanel freqPanel = createFrequencyPanel();

        // 中部分割：上排名表 + 下频次分布
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;

        gbc.gridy = 0; gbc.weighty = 3;
        centerPanel.add(tablePanel, gbc);

        gbc.gridy = 1; gbc.weighty = 1;
        centerPanel.add(freqPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // 刷新
        JButton refreshBtn = new JButton("刷新数据");
        refreshBtn.addActionListener(e -> refreshAll());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refreshBtn);
        add(bottom, BorderLayout.SOUTH);

        refreshAll();
    }

    private JLabel makeCard(JPanel parent, String label, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        card.setBackground(Color.WHITE);

        JLabel val = new JLabel(value, SwingConstants.CENTER);
        val.setFont(new Font("Arial", Font.BOLD, 28));
        val.setForeground(new Color(67, 97, 238));
        card.add(val, BorderLayout.CENTER);

        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("宋体", Font.PLAIN, 12));
        lbl.setForeground(Color.GRAY);
        card.add(lbl, BorderLayout.SOUTH);
        parent.add(card);
        return val;
    }

    private JPanel createFrequencyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("点名频次分布"));
        panel.setPreferredSize(new Dimension(400, 150));
        panel.setBackground(Color.WHITE);

        String[] ranges = {"0次", "1~3次", "4~6次", "7~10次", "10次以上"};
        Color[] colors = {
            new Color(144, 202, 249),
            new Color(66, 133, 244),
            new Color(67, 97, 238),
            new Color(48, 79, 254),
            new Color(25, 55, 109)
        };
        freqBars = new JProgressBar[5];
        freqCounts = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 5, 3, 5);

            gbc.gridx = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
            JLabel rangeLbl = new JLabel(ranges[i]);
            rangeLbl.setFont(new Font("宋体", Font.PLAIN, 13));
            panel.add(rangeLbl, gbc);

            gbc.gridx = 1; gbc.weightx = 1; gbc.anchor = GridBagConstraints.WEST;
            JProgressBar bar = new JProgressBar(0, 100);
            bar.setStringPainted(true);
            bar.setForeground(colors[i]);
            bar.setFont(new Font("Arial", Font.PLAIN, 11));
            bar.setBackground(new Color(230, 230, 230));
            panel.add(bar, gbc);
            freqBars[i] = bar;

            gbc.gridx = 2; gbc.weightx = 0;
            JLabel countLbl = new JLabel("0人");
            countLbl.setFont(new Font("Arial", Font.PLAIN, 13));
            countLbl.setForeground(Color.DARK_GRAY);
            countLbl.setPreferredSize(new Dimension(60, 20));
            panel.add(countLbl, gbc);
            freqCounts[i] = countLbl;
        }
        return panel;
    }

    private void refreshAll() {
        Map<String, Object> s = service.getSummary();
        totalStu.setText(String.valueOf(s.get("totalStudents")));
        totalCalls.setText(String.valueOf(s.get("totalCalls")));
        answered.setText(String.valueOf(s.get("totalAnswered")));
        rate.setText(s.get("overallRate") + "%");

        rankModel.setRowCount(0);
        List<Student> students = service.getStudents();
        students.sort((a, b) -> b.getTotalCalled() - a.getTotalCalled());
        int rank = 1;
        for (Student st : students) {
            rankModel.addRow(new Object[]{
                    rank++, st.getStudentNo(), st.getName(), st.getClassName(),
                    st.getTotalCalled(), st.getTotalAnswered(),
                    String.format("%.1f%%", st.getAnswerRate())
            });
        }

        // 刷新频次分布
        List<String[]> freqData = service.getFrequency();
        int maxCount = 0;
        for (String[] row : freqData) {
            int count = Integer.parseInt(row[1]);
            if (count > maxCount) maxCount = count;
        }
        for (int i = 0; i < freqData.size() && i < 5; i++) {
            int count = Integer.parseInt(freqData.get(i)[1]);
            int pct = maxCount > 0 ? count * 100 / maxCount : 0;
            freqBars[i].setValue(pct);
            freqBars[i].setString(count + "人 (" + pct + "%)");
            freqCounts[i].setText(count + "人");
        }
    }
}
