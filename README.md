# 组员B —— 统计与数据分析

课堂点名系统 - 统计与数据分析模块

## 负责文件

| 文件 | 类型 | 说明 |
|------|:--:|------|
| `model/Student.java` | 共享实体 | 学生信息，含 answerRate / totalCalled |
| `model/RollCallRecord.java` | 共享实体 | 点名记录，含 isAnswered |
| `service/StatisticsService.java` | **独立负责** | 汇总统计、频次分布、Stream API 过滤 |
| `view/StatisticsPanel.java` | **独立负责** | 统计界面：四卡片 + 排名表 + 频次展示 |

## 功能

- 汇总统计（学生总数、总点名次数、总答出次数、总体成功率）
- 点名频次分布（0次 / 1~3次 / 4~6次 / 7~10次 / 10次以上）
- 学生排名表（按被点名次数降序排列）
- 图形化统计面板
