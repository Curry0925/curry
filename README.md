# Curry Demo Spring Boot

一个最小可运行的 Spring Boot Demo，包含两个接口：

- `GET /`：返回欢迎消息
- `GET /health`：返回健康状态

## 运行方式

确保本机已安装 **JDK 17+** 与 **Maven 3.9+**。

```bash
mvn spring-boot:run
```

启动后访问：

- <http://localhost:8080/>
- <http://localhost:8080/health>

## 测试

```bash
mvn test
```
