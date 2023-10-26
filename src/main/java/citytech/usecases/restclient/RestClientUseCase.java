package citytech.usecases.restclient;

import citytech.platforms.usecase.UseCase;
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
public class RestClientUseCase implements UseCase<RestClientRequest, RestClientResponse> {
    //REST CLIENT API OF SRIJANSIL
    @Override
    public Optional<RestClientResponse> execute(RestClientRequest request) throws IOException, URISyntaxException, InterruptedException {
        String jsonBody = new ObjectMapper().writeValueAsString(request);
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://172.16.16.229:8080/user/login"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-type", "application/json")
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        var response = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        RestClientResponse restClientResponse = new ObjectMapper().readValue(response.body(), RestClientResponse.class);
        return Optional.of(restClientResponse);
    }
}
