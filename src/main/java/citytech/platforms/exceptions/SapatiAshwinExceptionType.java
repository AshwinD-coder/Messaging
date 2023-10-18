package citytech.platforms.exceptions;

public enum SapatiAshwinExceptionType {
    USERNAME_EMPTY_OR_BLANK("U002","Username is empty or blank!"),
    EMAIL_INVALID("U004","Email must be of gmail format!"),
    EMAIL_ALREADY_EXISTS("U005","Email already registered!"),
    PASSWORD_INCORRECT("U006","Password incorrect!"),
    SECURITY_TOKEN_MISSING("S001","X-XSRF-TOKEN is missing!"),
    SECURITY_TOKEN_EXPIRED("S004","Security Token is expired"),
    SECURITY_TOKEN_INVALID("S005","Security Token is invalid"),
    USERNAME_ALREADY_EXISTS("U007","Username already taken!"),
    PASSWORD_EMPTY_OR_BLANK("U009","Password is empty or blank!"),
    USER_ENTITY_EMPTY("U010","User not found!"),
    RESPONSE_EMPTY("S002","Response is empty!"),
    SECURITY_INTERCEPTOR_EXCEPTION("S003","Error in chain process!"),
    DB_SIGNUP_ERROR("DB1","Couldn't insert user entity into database!"),
    DB_LOGIN_ERROR("DB2","Couldn't fetch login details from database!")


;
    private final String code;
    private final String message;

    SapatiAshwinExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


}
