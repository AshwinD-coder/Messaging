package citytech.platforms.exceptions;

public class SapatiAshwinException extends RuntimeException{
    private final String code;
    private final String message;
    private final SapatiAshwinExceptionType sapatiAshwinExceptionType;

    public SapatiAshwinException(SapatiAshwinExceptionType code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.sapatiAshwinExceptionType = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SapatiAshwinExceptionType getExceptionType() {
        return sapatiAshwinExceptionType;
    }
}
