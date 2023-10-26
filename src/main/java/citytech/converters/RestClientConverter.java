package citytech.converters;

import citytech.controllers.payloads.RestClientPayload;
import citytech.usecases.restclient.RestClientRequest;

public class RestClientConverter {
    private RestClientConverter() {
    }

    public static RestClientRequest toRestClientRequest(RestClientPayload restClientPayload) {
        return new RestClientRequest(restClientPayload.email(), restClientPayload.password());
    }
}
