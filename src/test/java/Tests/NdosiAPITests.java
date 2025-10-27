package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;

import java.util.UUID;

import static Common.commonTestData.*;
import static RequestBuilder.NdosiAPIRequestBuilder.*;
import io.restassured.response.Response;
import org.testng.Assert;

@Test
@Feature("Ndosi API")
@Story("Login")
public class NdosiAPITests {
    public String email = UUID.randomUUID().toString().substring(0, 8) + "@gmail.com";
    private static String token;

    //Registration Test
    @Description("As a user i want to be able to register to Ndosi API")
    @Test(priority = 1)
    public void registerTests() {
        registerResponse("Test","Tiisetso",email,"12345678","12345678").
                then().
                log().all().
                assertThat().
                statusCode(create_success_status_code);
    }

    //Login Test
    @Description("As a user i want to be able to login to Ndosi API")
    @Test(priority = 2)
    public void loginTests() {
        loginResponse(email,"12345678").
                then().
                log().all().
                assertThat().
                statusCode(success_status_code);

        token = loginResponse(email, "12345678").jsonPath().getString("data.token");

        System.out.println("Retrieved Token: " + token);
    }

    //Get Specific User Profile Test
   /* @Description("As a user i want to be able to get my profile from Ndosi API")
    @Test(priority = 3)
    public void getProfileTests() {
        // Capture the login response so we can inspect the body and avoid calling the API twice
        Response loginResp = loginResponse(email, "12345678");
        // The API returns the token nested under `data.token` according to the README/example
        String token = loginResp.jsonPath().getString("data.token");
        // Fail fast with a helpful message if the token is missing
        Assert.assertNotNull(token, "Login response did not contain a token. Response body: " + loginResp.asString());

        getProfileResponse(token).
                then().
                log().all().
                assertThat().
                statusCode(success_status_code);

    }*/

}
