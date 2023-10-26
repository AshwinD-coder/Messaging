package citytech.usecases.messaging;

import citytech.platforms.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@Serdeable
@RecordBuilder
public record MessagingUseCaseRequest(String email, String username, String password) implements UseCaseRequest {
}
