package pe.area51.githubsearcher.domain;

import android.support.annotation.IntDef;

public class DataGatewayException extends Exception {

    public final static int NOT_IMPLEMENTED = -1;
    public final static int ERROR_UNKNOWN_USER = 1;
    public final static int ERROR_IO = 2;
    public final static int ERROR_OTHER = 3;

    @IntDef({
            NOT_IMPLEMENTED,
            ERROR_UNKNOWN_USER,
            ERROR_IO,
            ERROR_OTHER
    })
    @interface ErrorCode {
    }

    @ErrorCode
    private final int errorCode;

    public DataGatewayException(String message, @ErrorCode int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataGatewayException(String message, Throwable cause, @ErrorCode int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DataGatewayException(Throwable cause, @ErrorCode int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    @ErrorCode
    public int getErrorCode() {
        return errorCode;
    }
}
