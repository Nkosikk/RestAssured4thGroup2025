package PayloadBuilder;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;

public class NdosiAPIPayloadBuilder {

    public static JSONObject loginPayload(String email,String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        return jsonObject;
    }


    public static JSONObject registerPayload(String firstName, String lastName,String email,String password, String confirmPassword) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("confirmPassword", password);

        return jsonObject;
    }

    //Update User Payload
    public  static JSONObject UpdateUserPayload(String fullName, String email) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("full_name", fullName);
        jsonObject.put("email", email);

        return jsonObject;
    }



}
