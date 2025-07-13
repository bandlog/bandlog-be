package net.effize.bandlog.domain.common.exception;

public class ErrorCode {
    public static ErrorCode BAD_REQUEST = ErrorCode.of("BAD_REQUEST");
    public static ErrorCode INTERNAL_SERVER_ERROR = ErrorCode.of("INTERNAL_SERVER_ERROR");
    private String value;

    private ErrorCode(String value) {
        this.value = value;
    }

    public static ErrorCode of(String value) {
        return new ErrorCode(value);
    }
}
