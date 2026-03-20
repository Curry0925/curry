# Curry Spring Boot Service

这是一个更规范的 Spring Boot 工程示例，采用常见的分层结构：

- `controller`：负责对外提供 HTTP 接口
- `service`：负责封装业务逻辑
- `dto`：负责接口返回对象定义

## 接口列表

- `GET /api/curry/greeting`：返回应用名和欢迎消息
- `GET /api/curry/health`：返回服务健康状态

示例返回：

```json
{
  "success": true,
  "data": {
    "application": "curry-service",
    "message": "Hello from Curry Service!"
  }
}
```

## 项目结构

```text
src/main/java/com/curry/app
├── MainApplication.java
├── controller
│   └── ApplicationInfoController.java
├── dto
│   ├── ApiResponse.java
│   ├── GreetingResponse.java
│   └── HealthResponse.java
└── service
    └── ApplicationInfoService.java
```

## 运行方式

请确保本机已安装：

- JDK 17+
- Maven 3.9+

```bash
mvn spring-boot:run
```

启动后访问：

- <http://localhost:8080/api/curry/greeting>
- <http://localhost:8080/api/curry/health>

## 测试

```bash
mvn test
```
