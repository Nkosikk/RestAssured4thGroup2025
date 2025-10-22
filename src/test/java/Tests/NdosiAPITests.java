package Tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static Common.commonTestData.*;
import static RequestBuilder.NdosiAPIRequestBuilder.*;

@Test
@Feature("Ndosi API")
@Story("Login To Learning Material")
public class NdosiAPITests {

    @Description("As a user I want to be able to register to Ndosi API")
    @Test
    public void registerTests() {
        Response response = registerResponse(
                "John",
                "Doe",
                "john.doe99@example.com",
                "SecurePass123!",
                "SecurePass123!"
        );
        response.then()
                .log().all()
                .assertThat()
                .statusCode(bad_request_status_code);

//        String userId = response.jsonPath().getString("data.id");
//        if(userId == null){
//            throw new AssertionError("User ID is null! Registration might have failed.");
//        }
//        System.out.println("id: " + userId);
    }

    @Description("As a user I want to be able to login to Ndosi API")
    @Test(dependsOnMethods = "registerTests")
    public void loginTests() {
        loginResponse("john.doe99@example.com","SecurePass123!").
                then().
                log().all().
                assertThat().
                statusCode(unauthorized_status_code);
    }


}
