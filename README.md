# curry

Java Spring Boot 版本的日志告警规则匹配 demo。

## 规则日志定义（按你的业务口径）
给定你提供的真实业务日志格式（`|` 分隔字段），如果一条日志**同时包含**下面三个字段：

- `skyWalkingTid: NA`
- `skyWalkingTSId: N/A`
- `skyWalkingSpanId: N/A`

则判定为**规则日志**；否则判定为**非规则日志**。

## 告警规则能力
- 按 `pattern`（正则）匹配日志内容
- 按 `level` 过滤日志级别
- 使用 `windowSeconds` 作为滚动时间窗口
- 使用 `minMatches` 作为触发阈值
- 告警统计时仅统计“规则日志”

## 运行 demo
```bash
mvn spring-boot:run
```

demo 中包含：
- 3 条符合规则日志 + `database timeout`（会参与告警计数）
- 1 条缺少 skywalking 字段的非规则日志（不会参与告警计数）

## 运行测试
```bash
mvn test
```
