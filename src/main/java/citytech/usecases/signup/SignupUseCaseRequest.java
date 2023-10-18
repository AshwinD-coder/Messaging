package citytech.usecases.signup;

import citytech.platforms.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record SignupUseCaseRequest(String username, String email, String phoneNumber) implements UseCaseRequest {
}
