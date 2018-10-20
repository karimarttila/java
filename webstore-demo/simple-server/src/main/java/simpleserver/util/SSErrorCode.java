package simpleserver.util;

public enum  SSErrorCode {

    // Simple Server application errors 100-
    EMAIL_ALREADY_EXISTS(100),

    // Server errors 500-
    SERVER_ERROR(500);

    private int errorCode;

    private SSErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
