package citytech.controllers.payloads;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record SignupPayload(String username, String email, String phoneNumber) {
}
