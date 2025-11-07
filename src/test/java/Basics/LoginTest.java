package Basics;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest {

    @Test
    public void testLoginWithValidCredentials() {
        // Set base URI
        RestAssured.baseURI = "https://www.ndosiautomation.co.za";

        // Create request payload
        String requestBody = "{\n" +
                "    \"email\": \"nkosi@gmail.com\",\n" +
                "    \"password\": \"12345678\"\n" +
                "}";

        // Perform POST request
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/API/login")
                .then()
                .extract().response();

        // Print response details
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Response Headers: " + response.getHeaders().asList());

        // Basic assertions - adjust based on actual API response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");
    }

    @Test
    public void testLoginWithValidCredentialsUsingHamcrestMatchers() {
        // Alternative approach using Hamcrest matchers for validation
        given()
                .baseUri("https://www.ndosiautomation.co.za")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"email\": \"nkosi@gmail.com\",\n" +
                        "    \"password\": \"12345678\"\n" +
                        "}")
                .when()
                .post("/API/login")
                .then()
                .statusCode(200) // Adjust expected status code based on API
                .log().all(); // This will log the entire response
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Test with invalid credentials
        String requestBody = "{\n" +
                "    \"email\": \"invalid@gmail.com\",\n" +
                "    \"password\": \"wrongpassword\"\n" +
                "}";

        given()
                .baseUri("https://www.ndosiautomation.co.za")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/API/login")
                .then()
                .statusCode(anyOf(is(401), is(400), is(403))) // Common error status codes
                .log().all();
    }
}
