package matcher.dao;

/**
 * Use a RuntimeException to avoid "throws this and that" problem in Java. Basically this extends RuntimeException and
 * implements the default constructors.
 */
public class DaoRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DaoRuntimeException() {
    }

    public DaoRuntimeException(String arg0) {
        super(arg0);
    }

    public DaoRuntimeException(Throwable cause) {
        super(cause);
    }

    public DaoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
