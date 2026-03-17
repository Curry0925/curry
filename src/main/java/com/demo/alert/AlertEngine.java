package com.demo.alert;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class AlertEngine {

    public List<Alert> evaluate(List<LogEntry> entries, List<AlertRule> rules, Instant now) {
        List<Alert> alerts = new ArrayList<>();

        for (AlertRule rule : rules) {
            Instant windowStart = now.minusSeconds(rule.windowSeconds());
            Pattern pattern = Pattern.compile(rule.pattern());

            List<LogEntry> matched = entries.stream()
                    .filter(entry -> !entry.timestamp().isBefore(windowStart) && !entry.timestamp().isAfter(now))
                    .filter(entry -> rule.level() == null || entry.level().equalsIgnoreCase(rule.level()))
                    .filter(entry -> pattern.matcher(entry.message()).find())
                    .toList();

            if (matched.size() >= rule.minMatches()) {
                alerts.add(new Alert(
                        rule.name(),
                        matched.size(),
                        rule.windowSeconds(),
                        matched.stream().map(LogEntry::message).toList()
                ));
            }
        }

        return alerts;
    }
}
