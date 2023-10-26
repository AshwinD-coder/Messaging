package citytech.controllers.payloads;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record RestClientPayload(String email, String password) {
}
