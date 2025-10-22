package PayloadBuilder;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.sql.PreparedStatement;

public class NdosiAPIPayloadBuilder {

    public static JSONObject loginPayload() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "nkosi@gmail.com");
        jsonObject.put("password", "12345678");

        return jsonObject;
    }
}
