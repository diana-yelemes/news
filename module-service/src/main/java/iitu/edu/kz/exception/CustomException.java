// module-service/src/main/java/iitu/edu/kz/exception/CustomException.java
package iitu.edu.kz.exception;

public class CustomException extends RuntimeException {
    private int errorCode;

    public CustomException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
