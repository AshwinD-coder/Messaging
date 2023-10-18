package citytech.usecases.signup;

import citytech.platforms.usecase.UseCaseResponse;
import citytech.usecases.messaging.MessagingUseCaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;

@Introspected
@Serdeable
@RecordBuilder
public record SignupUseCaseResponse(String username, String email, String message,
                                    @JsonProperty("MessagingAppResponse") MessagingUseCaseResponse messagingUseCaseResponse) implements UseCaseResponse {
}
