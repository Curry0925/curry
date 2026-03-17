package com.demo.alert;

import org.springframework.stereotype.Component;

@Component
public class LogFormatInspector {

    public static final String REQUIRED_TID = "skyWalkingTid: NA";
    public static final String REQUIRED_TSID = "skyWalkingTSId: N/A";
    public static final String REQUIRED_SPAN = "skyWalkingSpanId: N/A";

    public boolean isRuleLog(String logLine) {
        if (logLine == null || logLine.isBlank()) {
            return false;
        }

        return logLine.contains(REQUIRED_TID)
                && logLine.contains(REQUIRED_TSID)
                && logLine.contains(REQUIRED_SPAN);
    }
}
