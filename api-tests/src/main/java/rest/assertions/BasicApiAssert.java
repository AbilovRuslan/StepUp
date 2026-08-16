package rest.assertions;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

public class BasicApiAssert {
    private final Response actual;

    private BasicApiAssert(Response response) {
        this.actual = response;
    }

    public static BasicApiAssert assertThat(Response response) {
        return new BasicApiAssert(response);
    }

    public BasicApiAssert statusCodeIsEquals(int expected) {
        Assertions.assertThat(actual.statusCode())
                .as("Status code must be %d".formatted(expected))
                .isEqualTo(expected);
        return this;
    }

    public BasicApiAssert fieldIsExists(String path) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Field with path %s must be exists!".formatted(path))
                .isNotNull();
        return this;
    }

    public BasicApiAssert fieldIsEquals(String path, String value) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Field with path %s must be equals '%s'!".formatted(path, value))
                .isEqualTo(value);
        return this;
    }

    public BasicApiAssert headerIsEqual(String header, String value) {
        Assertions.assertThat(actual.getHeader(header))
                .as("Header %s must be equal '%s'".formatted(header, value))
                .isEqualToIgnoringCase(value);
        return this;
    }
}