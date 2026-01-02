package com.miniplatformer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger utility for the game.
 * Provides logging with specific categories for state transitions, decorator applications, and game events.
 */
public class GameLogger {
    private static final Logger logger = LogManager.getLogger(GameLogger.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Log general info message
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log state transition
     * Format: [STATE] Category: FROM -> TO
     */
    public static void logState(String category, String from, String to) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info(String.format("[STATE] %s: %s -> %s", category, from, to));
    }

    /**
     * Log decorator application
     * Format: [DECORATOR] DecoratorType applied to Target
     */
    public static void logDecorator(String decoratorType, String target) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info(String.format("[DECORATOR] %s applied to %s", decoratorType, target));
    }

    /**
     * Log decorator removal
     * Format: [DECORATOR] DecoratorType removed from Target
     */
    public static void logDecoratorRemoval(String decoratorType, String target) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info(String.format("[DECORATOR] %s removed from %s", decoratorType, target));
    }

    /**
     * Log gameplay event
     * Format: [GAMEPLAY] Event description
     */
    public static void logGameplay(String event) {
        String timestamp = LocalDateTime.now().format(formatter);
        logger.info(String.format("[GAMEPLAY] %s", event));
    }

    /**
     * Log error
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Log error with exception
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log warning
     */
    public static void warn(String message) {
        logger.warn(message);
    }
}
