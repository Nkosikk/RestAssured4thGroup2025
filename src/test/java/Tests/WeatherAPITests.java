package Tests;

import Common.commonTestData;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static Common.BasePaths.*;
import static Common.BasePaths.API_KEY;
import static Common.BasePaths.*;
import static Common.commonTestData.*;
import static RequestBuilder.WeatherAPIRequestBuilder.*;
import static Utils.generateTestData.stationName;
import static Utils.generateTestData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Ndosi API")
@Story("registerStation")
public class WeatherAPITests {

    @Test(priority = 1)
    public static void registerStationTest() {

        Response response = registerStation(external_id, name, latitude, longitude, altitude);
        int statusCode = response.getStatusCode();

        System.out.println("Create Station Response Code: " + statusCode);
        response.then()
                .log().all()
                .assertThat()
                .statusCode(create_success_status_code)
                .body("ID", notNullValue())
                .body("name", containsString("Station_"))
                .body("external_id", containsString("EXT_"))
                .body("created_at", notNullValue());

        if (statusCode == bad_request_status_code) {
            System.out.println("Bad Request: " + response.asPrettyString());
            response.then()
                    .body("message", containsString("Missing"))
                    .body("code", notNullValue());

        } else if (statusCode == duplicate_external_ID) {
            System.out.println("Duplicate External ID: " + response.asPrettyString());
            response.then()
                    .body("message", containsString("already exists"));

        } else {
            System.out.println("Unexpected Error: " + statusCode);
            System.out.println(response.asPrettyString());
        }
    }



    @Test(priority = 2, dependsOnMethods = "registerStationTest")
    public void transferMeasurementsTest() {

        System.out.printf("Transferring measurements to station: " + station_id);
        Response response = getNewlyRegisteredStation(station_id);

        response.then()
                .log().all()
                .assertThat()
                .statusCode(success_status_code)
                .body("id", equalTo(station_id))
                .body("name", containsString("Station_"))
                .body("external_id", containsString("EXT_"))
                .body("created_at", notNullValue());

        //negative test
        String invalidId = commonTestData.invalidId;
        Response invalidIdResponse = getNewlyRegisteredStation(invalidId);

        if (invalidIdResponse.statusCode() != bad_request_status_code) {
            throw new AssertionError("Expected 400 for invalid station ID, but got: "
                    + invalidIdResponse.statusCode());
        }

        Response missingIdResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH+"/")
                .queryParam("appid", API_KEY)
                .get();

        if (missingIdResponse.statusCode() != not_found_status_code) {
            throw new AssertionError("Expected 404 for missing station ID, but got: "
                    + missingIdResponse.statusCode());
        }

        Response invalidKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .queryParam("appid", "INVALID_KEY")
                .get();

        if (invalidKeyResponse.statusCode() != invalid_api_status_code) {
            throw new AssertionError("Expected 401 for invalid API key, but got: "
                    + invalidKeyResponse.statusCode());
        }

        Response noKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .get();

        if (noKeyResponse.statusCode() != invalid_api_status_code) {
            throw new AssertionError("Expected 401 for missing API key, but got: "
                    + noKeyResponse.statusCode());
        }

    }


    @Test(priority = 3, dependsOnMethods = "transferMeasurementsTest")
    public void updateStationNameTest() {

        System.out.printf("Updating station name to: " + station_id);
        Response response = updateStationNameRequestBuilder(external_id, station_id, stationName, latitude, longitude, altitude);
        response.then()
                .log().all()
                .assertThat()
                .statusCode(success_status_code)
                .body("id", equalTo(station_id))
                .body("name", containsString(stationName))
                .body("external_id", containsString("EXT_"))
                .body("created_at", notNullValue());

        //negative
        String invalidStationId = "0000000";

        Response invalidIdResponse = updateStationNameRequestBuilder(external_id, invalidStationId, stationName, latitude, longitude, altitude
        );

        if (invalidIdResponse.statusCode() != bad_request_status_code) {
            throw new AssertionError("Expected 400 for invalid station ID but got: " + invalidIdResponse.statusCode()
            );
        }

        Response missingIdResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/")  // No ID included
                .queryParam("appid", API_KEY)
                .contentType("application/json")
                .body("{}")
                .put();

        if (missingIdResponse.statusCode() != duplicate_external_ID) {
            throw new AssertionError(
                    "Expected 404 for missing station ID but got: " + missingIdResponse.statusCode());
        }

        Response invalidKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .queryParam("appid", "WRONG_KEY")
                .contentType("application/json")
                .body("{}")
                .put();

        if (invalidKeyResponse.statusCode() != invalid_api_status_code) {
            throw new AssertionError(
                    "Expected 401 for invalid API key but got: " + invalidKeyResponse.statusCode()
            );
        }

        Response noKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .contentType("application/json")
                .body("{}")
                .put();

        if (noKeyResponse.statusCode() != invalid_api_status_code) {
            throw new AssertionError(
                    "Expected 401 for missing API key but got: " + noKeyResponse.statusCode()
            );
        }
    }

    @Test(priority = 4)
    public void deleteStationTest() {

        System.out.printf("Deleting station: " + station_id);
        Response response = deleteNewlyRegisteredStation(station_id);

        response.then()
                .log().all()
                .assertThat()
                .statusCode(delete_success_status_code);

        Response deleteAgainResponse = deleteNewlyRegisteredStation(station_id);

        if (deleteAgainResponse.getStatusCode() != not_found_status_code) {
            throw new AssertionError(
                    "Expected 404 when deleting station again but got: " + deleteAgainResponse.getStatusCode());
        }


        // negative test

        String invalidStationId = commonTestData.invalidStationId;
        Response invalidIdResponse = deleteNewlyRegisteredStation(invalidStationId);

        if (invalidIdResponse.getStatusCode() != bad_request_status_code) {
            throw new AssertionError(
                    "Expected 400 for invalid station ID but got: " + invalidIdResponse.getStatusCode());
        }



        Response missingIdResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/") // No ID
                .queryParam("appid", API_KEY)
                .delete();

        if (missingIdResponse.getStatusCode() != duplicate_external_ID) {
            throw new AssertionError(
                    "Expected 404 for missing station ID but got: "
                            + missingIdResponse.getStatusCode()
            );
        }


        Response invalidKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .queryParam("appid", "INVALID_KEY")
                .delete();

        if (invalidKeyResponse.getStatusCode() != invalid_api_status_code) {
            throw new AssertionError("Expected 401 for invalid API key but got: " + invalidKeyResponse.getStatusCode());
        }

        Response noKeyResponse = given()
                .baseUri(BASE_URI)
                .basePath(BASE_PATH + "/" + station_id)
                .delete();

        if (noKeyResponse.getStatusCode() != invalid_api_status_code) {
            throw new AssertionError("Expected 400 for missing API key but got: " + noKeyResponse.getStatusCode());
        }
    }


}



