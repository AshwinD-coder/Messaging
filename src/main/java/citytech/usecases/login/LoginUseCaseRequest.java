package citytech.usecases.login;

import citytech.platforms.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record LoginUseCaseRequest(String username, String password) implements UseCaseRequest {
}
