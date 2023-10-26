package citytech.usecases.geocodingapi;

import citytech.platforms.usecase.UseCaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import io.soabase.recordbuilder.core.RecordBuilder;


@RecordBuilder
@Introspected
@Serdeable
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReverseGeocodingApiResponse(String country, String state, String district, String street) implements UseCaseResponse {
}
