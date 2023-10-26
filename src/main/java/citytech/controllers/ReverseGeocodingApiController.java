package citytech.controllers;

import citytech.controllers.payloads.ReverseGeocodingApiPayload;
import citytech.converters.ReverseGeocodingApiConverter;
import citytech.platforms.exceptions.SapatiAshwinException;
import citytech.platforms.exceptions.SapatiAshwinExceptionType;
import citytech.platforms.response.SapatiAshwinResponse;
import citytech.usecases.geocodingapi.ReverseGeocodingApiRequest;
import citytech.usecases.geocodingapi.ReverseGeocodingApiResponse;
import citytech.usecases.geocodingapi.ReverseGeocodingApiUseCase;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@Controller("reverse-geocoding")
public class ReverseGeocodingApiController {
    @Inject
    private ReverseGeocodingApiUseCase reverseGeocodingApiUseCase;


    @Post("/")
    public HttpResponse<SapatiAshwinResponse<Object>> reverseGeocodingApi(@Body ReverseGeocodingApiPayload reverseGeocodingApiPayload) throws URISyntaxException, IOException, InterruptedException {
        ReverseGeocodingApiRequest reverseGeocodingApiRequest = ReverseGeocodingApiConverter.toReverseGeocodingApiRequest(reverseGeocodingApiPayload);
        Optional<ReverseGeocodingApiResponse> reverseGeocodingApiResponse = reverseGeocodingApiUseCase.execute(reverseGeocodingApiRequest);
        if (reverseGeocodingApiResponse.isPresent())
            return HttpResponse.ok().body(new SapatiAshwinResponse<>().success(reverseGeocodingApiResponse.get()));
        else
            throw new SapatiAshwinException(SapatiAshwinExceptionType.RESPONSE_EMPTY);
    }
}
