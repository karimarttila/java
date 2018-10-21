package simpleserver.util;

/**
 * The type Simple Server exception.
 */
@SuppressWarnings("unused")
public class SSException extends RuntimeException {

    private static final long serialVersionUID = -1931005380014655090L;

    private final SSErrorCode errorCode;


    /**
     * Instantiates a new Simple Server exception.
     *
     * @param errorCode the error code
     */
    public SSException(SSErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }


    /**
     * Instantiates a new Simple Server exception.
     *
     * @param message   the message
     * @param cause     the cause
     * @param errorCode the error code
     */
    public SSException(String message, Throwable cause, SSErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }


    /**
     * Instantiates a new Simple Server exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public SSException(String message, SSErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


    /**
     * Instantiates a new Simple Server exception.
     *
     * @param cause     the cause
     * @param errorCode the error code
     */
    public SSException(Throwable cause, SSErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public SSErrorCode getCode() {
        return this.errorCode;
    }
}
