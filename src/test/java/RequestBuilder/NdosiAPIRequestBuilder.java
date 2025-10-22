package RequestBuilder;

import io.restassured.response.Response;

import static Common.BasePaths.*;
import static PayloadBuilder.NdosiAPIPayloadBuilder.*;
import static io.restassured.RestAssured.*;

public class NdosiAPIRequestBuilder {

    public static Response loginResponse() {
        return given().
                baseUri(NdosiBaseUrl).
                basePath("/login").
                contentType("application/json").
                body(loginPayload()).
                log().all().
                post().
                then().
                log().all().
                extract().response();
    }
}
