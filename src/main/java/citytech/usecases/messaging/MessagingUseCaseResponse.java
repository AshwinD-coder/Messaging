package citytech.usecases.messaging;

import citytech.platforms.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record MessagingUseCaseResponse(String status, String recipient) implements UseCaseResponse {
}
