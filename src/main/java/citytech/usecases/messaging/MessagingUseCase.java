package citytech.usecases.messaging;

import citytech.platforms.exceptions.SapatiAshwinException;
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
public class MessagingUseCase implements UseCase<MessagingUseCaseRequest, MessagingUseCaseResponse> {
    @Override
    public Optional<MessagingUseCaseResponse> execute(MessagingUseCaseRequest request) throws SapatiAshwinException, IOException, InterruptedException, URISyntaxException {
            String jsonRequest = new ObjectMapper().writeValueAsString(request);
            HttpRequest messageRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/messaging/signup"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .header("Content-type", "application/json")
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            var response = httpClient.send(messageRequest, HttpResponse.BodyHandlers.ofString());
            MessagingUseCaseResponse messagingUseCaseResponse = new ObjectMapper().readValue(response.body(), MessagingUseCaseResponse.class);
            return Optional.of(messagingUseCaseResponse);
    }
}
