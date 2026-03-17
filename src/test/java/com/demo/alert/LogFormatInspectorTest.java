package com.demo.alert;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogFormatInspectorTest {

    private final LogFormatInspector inspector = new LogFormatInspector();

    @Test
    void shouldBeRuleLogWhenAllSkyWalkingFieldsExist() {
        String line = "|| ... | skyWalkingTid: NA |skyWalkingTSId: N/A | skyWalkingSpanId: N/A | ...";
        assertTrue(inspector.isRuleLog(line));
    }

    @Test
    void shouldBeNonRuleLogWhenAnyFieldMissing() {
        String line = "|| ... | skyWalkingTid: NA |skyWalkingTSId: N/A | ...";
        assertFalse(inspector.isRuleLog(line));
    }
}
