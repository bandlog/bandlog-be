package net.effize.bandlog.shared.exception;

import java.util.Objects;

public class ErrorCode {
    public static final ErrorCode BAD_REQUEST = ErrorCode.of("BAD_REQUEST");
    public static final ErrorCode INTERNAL_SERVER_ERROR = ErrorCode.of("INTERNAL_SERVER_ERROR");
    private final String value;

    private ErrorCode(String value) {
        this.value = value;
    }

    public static ErrorCode of(String value) {
        return new ErrorCode(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ErrorCode errorCode)) return false;
        return Objects.equals(value, errorCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
