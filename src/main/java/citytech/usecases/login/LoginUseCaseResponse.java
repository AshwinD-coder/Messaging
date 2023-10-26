package citytech.usecases.login;

import citytech.platforms.usecase.UseCaseResponse;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record LoginUseCaseResponse(String token,String message) implements UseCaseResponse {
}
