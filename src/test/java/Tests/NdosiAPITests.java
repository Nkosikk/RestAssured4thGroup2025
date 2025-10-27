package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;

import java.util.UUID;

import static Common.commonTestData.*;
import static RequestBuilder.NdosiAPIRequestBuilder.*;
import static Utils.generateTestData.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;


@Test
@Feature("Ndosi API")
@Story("Login")
public class NdosiAPITests {

    private static String token;

    //Registration Test
    @Description("As a user i want to be able to register to Ndosi API")

    public void registerTests() {
        registerResponse(firstName,lastName,email,password,password).
                then().
                log().all().
                assertThat().
                statusCode(create_success_status_code).
                body(containsString("data")).
                body("success",equalTo(true)).
                body("message",equalTo("User registered successfully")).
                body("data.email",equalTo(email)).
                body("data.firstName",equalTo(firstName)).
                body("data.lastName",equalTo(lastName)).
                body("data.id",notNullValue()).
                body("data.createdAt",notNullValue());
    }

    //Login Test
    @Description("As a user i want to be able to login to Ndosi API")
    @Test(dependsOnMethods = "registerTests")
    public void loginTests() {
        loginResponse(email,password).
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
