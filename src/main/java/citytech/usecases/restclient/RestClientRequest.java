package citytech.usecases.restclient;

import citytech.platforms.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record RestClientRequest(String email, String password) implements UseCaseRequest {
}
