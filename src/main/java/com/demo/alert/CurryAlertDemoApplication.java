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
    CommandLineRunner runDemo(AlertEngine engine) {
        return args -> {
            Instant now = Instant.now();
            List<LogEntry> logs = List.of(
                    new LogEntry(now.minus(50, ChronoUnit.SECONDS), "INFO", "service started"),
                    new LogEntry(now.minus(40, ChronoUnit.SECONDS), "ERROR", "database timeout while querying orders"),
                    new LogEntry(now.minus(30, ChronoUnit.SECONDS), "ERROR", "database timeout while querying users"),
                    new LogEntry(now.minus(20, ChronoUnit.SECONDS), "WARN", "retrying database connection"),
                    new LogEntry(now.minus(10, ChronoUnit.SECONDS), "ERROR", "database timeout while querying payments")
            );

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
