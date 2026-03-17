package com.demo.alert;

public record AlertRule(
        String name,
        String pattern,
        int minMatches,
        int windowSeconds,
        String level
) {
}
