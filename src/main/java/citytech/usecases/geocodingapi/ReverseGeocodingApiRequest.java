package citytech.usecases.geocodingapi;

import citytech.platforms.usecase.UseCaseRequest;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable
public record ReverseGeocodingApiRequest(String location) implements UseCaseRequest {
}
