package com.samh.personal.common.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class LogbackColourConfig extends ForegroundCompositeConverterBase<ILoggingEvent> {
    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        switch (level.toInt()) {
            case Level.INFO_INT:
                return ANSIConstants.GREEN_FG;
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            case Level.ERROR_INT:
                return ANSIConstants.RED_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
