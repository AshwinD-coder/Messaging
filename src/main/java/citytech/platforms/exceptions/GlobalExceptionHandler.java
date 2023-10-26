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
@Requires(classes = {Exception.class, ExceptionHandler.class})
public class GlobalExceptionHandler implements ExceptionHandler<Exception, HttpResponse<SapatiAshwinResponse<String>>> {
    @Override
    public HttpResponse<SapatiAshwinResponse<String>> handle(HttpRequest request, Exception exception) {
        return HttpResponse.badRequest().body(new SapatiAshwinResponse<>("400", exception.getMessage(), null));

    }
}
