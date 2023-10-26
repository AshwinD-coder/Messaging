package citytech.converters;

import citytech.controllers.payloads.ReverseGeocodingApiPayload;
import citytech.usecases.geocodingapi.ReverseGeocodingApiRequest;

public class ReverseGeocodingApiConverter {
    private ReverseGeocodingApiConverter() {
    }

    public static ReverseGeocodingApiRequest toReverseGeocodingApiRequest(ReverseGeocodingApiPayload reverseGeocodingApiPayload) {
        return new ReverseGeocodingApiRequest(reverseGeocodingApiPayload.location());
    }
}
