package citytech.usecases.restclient;

import citytech.platforms.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record RestClientResponse(String message) implements UseCaseResponse {
}
