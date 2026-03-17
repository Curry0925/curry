package com.demo.alert;

import java.util.List;

public record Alert(
        String ruleName,
        int matchCount,
        int windowSeconds,
        List<String> matchedMessages
) {
}
