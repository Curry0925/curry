package com.demo.alert;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlertEngineTest {

    private final AlertEngine engine = new AlertEngine(new LogFormatInspector());

    @Test
    void shouldTriggerWhenThresholdReachedWithRuleLogsOnly() {
        Instant now = Instant.parse("2026-01-01T10:00:00Z");
        List<LogEntry> entries = List.of(
                new LogEntry(now.minus(40, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #1")),
                new LogEntry(now.minus(20, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #2")),
                new LogEntry(now.minus(10, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #3"))
        );

        AlertRule rule = new AlertRule("db-timeout", "database timeout", 3, 60, "ERROR");
        List<Alert> alerts = engine.evaluate(entries, List.of(rule), now);

        assertEquals(1, alerts.size());
        assertEquals("db-timeout", alerts.get(0).ruleName());
        assertEquals(3, alerts.get(0).matchCount());
    }

    @Test
    void shouldIgnoreNonRuleLogsWhenSkyWalkingFieldsMissing() {
        Instant now = Instant.parse("2026-01-01T10:00:00Z");
        List<LogEntry> entries = List.of(
                new LogEntry(now.minus(40, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #1")),
                new LogEntry(now.minus(20, ChronoUnit.SECONDS), "ERROR", "database timeout #2"),
                new LogEntry(now.minus(10, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #3"))
        );

        AlertRule rule = new AlertRule("db-timeout", "database timeout", 3, 60, "ERROR");
        List<Alert> alerts = engine.evaluate(entries, List.of(rule), now);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void shouldNotTriggerWhenOneMatchOutsideWindow() {
        Instant now = Instant.parse("2026-01-01T10:00:00Z");
        List<LogEntry> entries = List.of(
                new LogEntry(now.minus(120, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #1")),
                new LogEntry(now.minus(20, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #2")),
                new LogEntry(now.minus(10, ChronoUnit.SECONDS), "ERROR", withSkyWalking("database timeout #3"))
        );

        AlertRule rule = new AlertRule("db-timeout", "database timeout", 3, 60, "ERROR");
        List<Alert> alerts = engine.evaluate(entries, List.of(rule), now);

        assertTrue(alerts.isEmpty());
    }

    private String withSkyWalking(String text) {
        return "... skyWalkingTid: NA |skyWalkingTSId: N/A | skyWalkingSpanId: N/A | " + text;
    }
}
