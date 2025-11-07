package Tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import static Common.commonTestData.*;
import static RequestBuilder.WeatherAPIRequestBuilder.*;
import static RequestBuilder.WeatherAPIRequestBuilder.registerStation;
import static RequestBuilder.WeatherAPIRequestBuilder.*;
import static Utils.generateTestData.*;
import static Utils.generateTestData.stationName;
import static org.hamcrest.Matchers.*;

@Feature("Ndosi API")
@Story("registerStation")
public class WeatherAPITests {

    @Test(priority = 1)
    public static void registerStationTest() {

        Response response = registerStation(external_id, name, latitude, longitude, altitude);
        String stationId;
        response.then()
                .assertThat()
                .statusCode(create_success_status_code)
                .assertThat()
                .body("ID", notNullValue())
                .assertThat()
                .body("name", containsString("Station_"))
                .assertThat()
                .body("external_id", containsString("EXT_"));

         stationId = response.jsonPath().getString("ID");
        System.out.println("Created Station ID: " + stationId);

    }

    @Test(priority = 2, dependsOnMethods = "registerStationTest")
    public void transferMeasurementsTest() {
        System.out.printf("Transfering measurements to station:"+station_id);
        Response response = getNewlyRegisteredStation(station_id);
        response.then()
                .log().all()
                .assertThat()
                .statusCode(success_status_code)
                .assertThat()
                .body("id", equalTo(station_id))
                .assertThat()
                .body("name", containsString("Station_"))
                .assertThat()
                .body("external_id", containsString("EXT_"))
                .assertThat()
                .body("created_at", notNullValue());

    }
    @Test(priority = 3, dependsOnMethods = "transferMeasurementsTest")
    public void updateStationNameTest() {
        System.out.printf("Updating station name to: " + station_id);
        Response response = updateStationNameRequestBuilder(external_id, station_id,stationName,latitude, longitude, altitude);
        response.then()
                .log().all()
                .assertThat()
                .statusCode(success_status_code)
                .assertThat()
                .body("id", equalTo(station_id))
                .assertThat()
                .body("name", containsString(stationName))
                .assertThat()
                .body("external_id", containsString("EXT_"))
                .assertThat()
                .body("created_at", notNullValue());
    }
    @Test(priority = 4)
    public void deleteStationTest() {

        Response response = deleteNewlyRegisteredStation(station_id);

        int statusCode = response.getStatusCode();
        System.out.println("Response Status Code: " + statusCode);


        if (statusCode == 204) {
            System.out.println("Station deleted successfully: " + station_id);

        } else if (statusCode == 400 || statusCode == 404 || statusCode == 500) {
            response.then()
                    .assertThat()
                    .body("code", notNullValue())
                    .body("message", notNullValue());
            System.out.println("Error deleting station: " + response.asPrettyString());

        } else {
            System.out.println("Unexpected status code: " + statusCode);
            System.out.println(response.asPrettyString());
        }
    }

}



