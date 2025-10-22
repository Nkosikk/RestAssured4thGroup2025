package PayloadBuilder;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class NdosiAPIPayloadBuilder {

    public static JSONObject registerPayload(String firstName, String lastName, String email, String password, String confirmPassword) {
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObject = new JSONObject();

        jsonObject.put("success", true);
        jsonObject.put("message", "User registered successfully");
        jsonObject.put("data", dataObject);

        dataObject.put("firstName", firstName);
        dataObject.put("lastName", lastName);
        dataObject.put("email", email);
        dataObject.put("password", password);
        dataObject.put("confirmPassword", confirmPassword);

        String createdAt = ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        dataObject.put("createdAt", createdAt);

        return jsonObject;
    }

    public static JSONObject loginPayload(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        return jsonObject;
    }

//    @Test
//    public void testLogin() {
//        System.out.println(loginPayload("john.doeABCD@example.com", "SecurePass123!"));
//    }
//
//    @Test
//    public void testRegister() {
//        System.out.println(registerPayload(
//                "John",
//                "Doe",
//                "john.doeABCD@example.com",
//                "SecurePass123!",
//                "SecurePass123!"
//        ));
//
//    }

}
