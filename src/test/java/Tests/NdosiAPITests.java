package Tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.*;

import static Common.commonTestData.*;
import static RequestBuilder.NdosiAPIRequestBuilder.*;

@Test
@Feature("Ndosi API")
@Story("Login")
public class NdosiAPITests {

    @Description("As a user i want to be able to login to Ndosi API")
    public void loginTests() {
        loginResponse().
                then().
                log().all().
                assertThat().
                statusCode(success_status_code);
    }


}
