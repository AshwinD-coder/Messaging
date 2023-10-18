package citytech.controllers.payloads;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record ReverseGeocodingApiPayload(String location) {
}
