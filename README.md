# curry

Java Spring Boot 版本的日志告警规则匹配 demo：

- 按 `pattern`（正则）匹配日志内容
- 按 `level` 过滤日志级别
- 使用 `windowSeconds` 作为滚动时间窗口
- 使用 `minMatches` 作为触发阈值

## 运行 demo

```bash
mvn spring-boot:run
```

示例规则：
- 规则名：`db-timeout-spike`
- 条件：60 秒内 `ERROR` 级别日志中出现至少 3 次 `database timeout`

## 运行测试

```bash
mvn test
```
