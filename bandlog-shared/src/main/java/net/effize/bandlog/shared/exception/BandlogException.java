package net.effize.bandlog.shared.exception;

public class BandlogException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String userMessage;
    private final String logMessage;
    private final Integer httpStatusCode;

    public BandlogException(ErrorCode errorCode, String userMessage, String logMessage, Integer httpStatusCode) {
        super(logMessage);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.logMessage = logMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public BandlogException(ErrorCode errorCode, String userMessage, String logMessage, Integer httpStatusCode, Throwable cause) {
        super(logMessage, cause);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.logMessage = logMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
