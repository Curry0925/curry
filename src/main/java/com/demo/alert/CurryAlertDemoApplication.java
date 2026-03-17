package com.demo.alert;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootApplication
public class CurryAlertDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurryAlertDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runDemo(AlertEngine engine, LogFormatInspector inspector) {
        return args -> {
            Instant now = Instant.now();
            List<LogEntry> logs = List.of(
                    new LogEntry(now.minus(40, ChronoUnit.SECONDS), "ERROR",
                            "|| NIL | IDC_PRIMARY | NIL | bjcb-tec | NIL | NIL | NIL | 2026-03-17 08:37:00.305 | BFS| NIL | ERROR | MyTaskExecutoF-95 | initChannel | com.dap.cloud.1warn.service.config.IilarnConfigIniter | w1-deploy-z9-bfs-app-d8cd7bc65-58nvp | NIL |6w16qwegegegenqwfbkebfbfbfvbfewbfegb skyWalkingTid: NA |skyWalkingTSId: N/A | skyWalkingSpanId: N/A | database timeout while querying orders"),
                    new LogEntry(now.minus(30, ChronoUnit.SECONDS), "ERROR",
                            "|| NIL | IDC_PRIMARY | NIL | bjcb-tec | NIL | NIL | NIL | 2026-03-17 08:37:10.305 | BFS| NIL | ERROR | MyTaskExecutoF-96 | initChannel | com.dap.cloud.1warn.service.config.IilarnConfigIniter | w1-deploy-z9-bfs-app-d8cd7bc65-58nvp | NIL |xxxx skyWalkingTid: NA |skyWalkingTSId: N/A | skyWalkingSpanId: N/A | database timeout while querying users"),
                    new LogEntry(now.minus(20, ChronoUnit.SECONDS), "ERROR",
                            "|| NIL | IDC_PRIMARY | NIL | bjcb-tec | NIL | NIL | NIL | 2026-03-17 08:37:20.305 | BFS| NIL | ERROR | MyTaskExecutoF-97 | initChannel | com.dap.cloud.1warn.service.config.IilarnConfigIniter | w1-deploy-z9-bfs-app-d8cd7bc65-58nvp | NIL |yyyy skyWalkingTid: NA |skyWalkingTSId: N/A | skyWalkingSpanId: N/A | database timeout while querying payments"),
                    new LogEntry(now.minus(10, ChronoUnit.SECONDS), "ERROR",
                            "|| NIL | IDC_PRIMARY | NIL | bjcb-tec | NIL | NIL | NIL | 2026-03-17 08:37:30.305 | BFS| NIL | ERROR | MyTaskExecutoF-98 | initChannel | com.dap.cloud.1warn.service.config.IilarnConfigIniter | w1-deploy-z9-bfs-app-d8cd7bc65-58nvp | NIL |zzzz database timeout without skywalking fields")
            );

            long nonRuleCount = logs.stream().filter(log -> !inspector.isRuleLog(log.message())).count();
            System.out.println("Non-rule log count=" + nonRuleCount);

            AlertRule rule = new AlertRule("db-timeout-spike", "database timeout", 3, 60, "ERROR");
            List<Alert> alerts = engine.evaluate(logs, List.of(rule), now);

            if (alerts.isEmpty()) {
                System.out.println("No alert triggered.");
                return;
            }

            alerts.forEach(alert -> {
                System.out.println("[ALERT] rule=" + alert.ruleName());
                System.out.println("  matches=" + alert.matchCount() + " in last " + alert.windowSeconds() + "s");
                alert.matchedMessages().forEach(msg -> System.out.println("  - " + msg));
            });
        };
    }
}
