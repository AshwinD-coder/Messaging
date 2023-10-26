package citytech.usecases.geocodingapi;

import citytech.platforms.usecase.UseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Singleton
public class ReverseGeocodingApiUseCase implements UseCase<ReverseGeocodingApiRequest, ReverseGeocodingApiResponse> {
    @Override
    public Optional<ReverseGeocodingApiResponse> execute(ReverseGeocodingApiRequest request) throws URISyntaxException, IOException, InterruptedException {
        String apiKey = "EqvJLz823a8wwnjcWjOYRj7rSoXQn9F5";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://www.mapquestapi.com/geocoding/v1/reverse?key=" + apiKey + "&location=" + request.location() + "&includeRoadMetadata=true&includeNearestIntersection=true"))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode jsonNode = new ObjectMapper().readTree(response.body()).path("results").get(0).path("locations").get(0);
        ReverseGeocodingApiResponse reverseGeocodingApiResponse = ReverseGeocodingApiResponseBuilder.builder()
                .country(jsonNode.path("adminArea1").asText())
                .state(jsonNode.path("adminArea3").asText())
                .district(jsonNode.path("adminArea5").asText())
                .street(jsonNode.path("street").asText())
                .build();
        return Optional.of(reverseGeocodingApiResponse);
    }
}

