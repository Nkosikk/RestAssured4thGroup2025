package RequestBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Common.BasePaths.*;
import static PayloadBuilder.NdosiAPIPayloadBuilder.*;
import static io.restassured.RestAssured.*;

public class NdosiAPIRequestBuilder {

    //login API Request
    public static Response loginResponse(String email,String password) {
        return given().
                baseUri(NdosiBaseUrl).
                basePath("/login").
                contentType("application/json").
                body(loginPayload(email,password)).
                log().all().
                post().
                then().
                log().all().
                extract().response();
    }
    //register API Request
    public static Response registerResponse(String firstName, String lastName,String email,String password, String confirmPassword) {
        return given().
                baseUri(NdosiBaseUrl).
                basePath("/register").
                contentType("application/json").
                body(registerPayload(firstName,lastName,email,password,confirmPassword)).
                log().all().
                post().
                then().
                log().all().
                extract().response();

        //System.out.println();
    }

    //Specific user profile API Request by decoding JWT token
    public static Response getProfileResponse(String token) {
        // Defensive checks to avoid decoding a null token and to provide clear errors
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Provided token is null or empty. Ensure you pass the JWT token returned by the login response (e.g. data.token).");
        }

        // Remove common prefix if present
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        DecodedJWT decodedJWT = JWT.decode(token);
        // Try common claim names for user id
        String userId = null;
        if (decodedJWT.getClaim("userId") != null) {
            userId = decodedJWT.getClaim("userId").asString();
        }
        if ((userId == null || userId.isEmpty()) && decodedJWT.getClaim("id") != null) {
            userId = decodedJWT.getClaim("id").asString();
        }

        if (userId == null || userId.isEmpty()) {
            // Helpful runtime error to inspect token claims
            throw new IllegalStateException("JWT token does not contain a userId or id claim. Token claims: " + decodedJWT.getClaims().keySet());
        }

        //System.out.println(token);
        return given().
                baseUri(NdosiBaseUrl).
                basePath("/profile/" + userId).
                contentType("application/json").
                //body(registerPayload(firstName,lastName,email,password,confirmPassword)).
                log().all().
                get().
                then().
                log().all().
                extract().response();

        }
    }
