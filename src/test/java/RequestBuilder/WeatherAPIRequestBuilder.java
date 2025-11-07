package RequestBuilder;

import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static Common.BasePaths.*;
import static PayloadBuilder.WeatherAPIPayloadBuilder.*;
import static io.restassured.RestAssured.given;
import PayloadBuilder.WeatherAPIPayloadBuilder;
public class WeatherAPIRequestBuilder {
    public static String station_id;
    public static String stationName;

    public static Response registerStation(String external_id, String name, double latitude, double longitude, double altitude) {


        Response response = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                .queryParam("appid", API_KEY)
                .contentType("application/json")
                .body(registerStationPayload(external_id, name, latitude, longitude, altitude))
                .log().all()
                .post()
                .then()
                .log().all()
                .extract().response();
        station_id = response.jsonPath().getString("ID");
        return response;

    }

    public static Response getNewlyRegisteredStation(String stationId) {
        return given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + stationId) // append the ID to the path
                .queryParam("appid", API_KEY)
                .contentType("application/json")
                .log().all()
                .get() // ✅ use GET, not POST
                .then()
                .log().all()
                .extract().response();
    }
    public static Response updateStationNameRequestBuilder(String external_id, String stationId, String stationName,
                                                           double latitude, double longitude, double altitude) {
        return given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + stationId)
                .queryParam("appid", API_KEY)
                .contentType("application/json")
                .log().all()
                .body(updateStationNamePayload(external_id, stationId,latitude, stationName, longitude, altitude))
                .when()
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteNewlyRegisteredStation(String stationId) {
        return given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + stationId)
                .queryParam("appid", API_KEY)
                .contentType("application/json")
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    }




