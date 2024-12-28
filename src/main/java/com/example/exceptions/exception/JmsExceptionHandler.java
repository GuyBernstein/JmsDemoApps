package com.example.exceptions.exception;

import org.springframework.stereotype.Component;
import javax.jms.*;
import javax.jms.IllegalStateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JmsExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(JmsExceptionHandler.class);

    public void handleInvalidDestination(InvalidDestinationException e) {
        logException("InvalidDestinationException", e);
    }

    public void handleMessageFormat(MessageFormatException e) {
        logException("MessageFormatException", e);
    }

    public void handleMessageNotWriteable(MessageNotWriteableException e) {
        logException("MessageNotWriteableException", e);
    }

    public void handleJmsSecurity(JMSSecurityException e) {
        logException("JMSSecurityException", e);
    }

    public void handleIllegalState(IllegalStateException e) {
        logException("IllegalStateException", e);
    }

    public void handleUnexpectedException(Exception e) {
        logException("Unexpected Exception", e);
    }

    private void logException(String exceptionType, Exception e) {
        logger.error("=== {} ===", exceptionType);
        logger.error("Error message: {}", e.getMessage());
        logger.error("=================={}==================", "=".repeat(exceptionType.length()));
    }
}