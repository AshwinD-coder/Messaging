package citytech.platforms.response;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public class SapatiAshwinResponse<T> {
    private String code;
    private String message;

    private T data;

    public SapatiAshwinResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public SapatiAshwinResponse() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SapatiAshwinResponse<T> success(T data){
        return new SapatiAshwinResponse<>("0","SUCCESS",data);
    }
}