package citytech.controllers;

import citytech.controllers.payloads.RestClientPayload;
import citytech.converters.RestClientConverter;
import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.response.SapatiAshwinResponse;
import citytech.usecases.restclient.RestClientRequest;
import citytech.usecases.restclient.RestClientResponse;
import citytech.usecases.restclient.RestClientUseCase;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller("/rest-client")
public class RestClientController {

    private final RestClientUseCase restClientUseCase;

    @Inject
    public RestClientController(RestClientUseCase restClientUseCase) {
        this.restClientUseCase = restClientUseCase;
    }

    @Post("/login")
    public HttpResponse<SapatiAshwinResponse<Object>> login(@Body RestClientPayload restClientPayload) throws IOException, URISyntaxException, InterruptedException {
        RestClientRequest restClientRequest = RestClientConverter.toRestClientRequest(restClientPayload);
        Optional<RestClientResponse> response = this.restClientUseCase.execute(restClientRequest);
        if (response.isPresent()) {
            return HttpResponse.ok().body(new SapatiAshwinResponse<>().success(response.get()));
        } else throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
    }
}
