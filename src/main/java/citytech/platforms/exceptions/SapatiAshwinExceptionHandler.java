package citytech.platforms.exceptions;

import citytech.platforms.response.SapatiAshwinResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {SapatiAshwinExceptionHandler.class, ExceptionHandler.class})
public class SapatiAshwinExceptionHandler implements ExceptionHandler<SapatiAshwinException, HttpResponse<SapatiAshwinResponse<String>>> {
    @Override
    public HttpResponse<SapatiAshwinResponse<String>> handle(HttpRequest request, SapatiAshwinException exception) {
        return HttpResponse.badRequest().body(new SapatiAshwinResponse<>(exception.getCode(), exception.getMessage(), null));
    }
}
