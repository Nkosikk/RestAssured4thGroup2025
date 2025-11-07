package PayloadBuilder;

import org.json.simple.JSONObject;
import java.util.LinkedHashMap;

import static Utils.generateTestData.*;

public class WeatherAPIPayloadBuilder {

    public static JSONObject registerStationPayload(String external_id, String name, double latitude, double longitude, double altitude) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("external_id", external_id);
        requestBody.put("name", name);
        requestBody.put("latitude", latitude);
        requestBody.put("longitude", longitude);
        requestBody.put("altitude", altitude);

        return requestBody;

    }
    public static JSONObject updateStationNamePayload(String external_id,String stationId,double latitude, String stationName, double longitude, double altitude) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("external_id", external_id);
        requestBody.put("station_id", stationId);
        requestBody.put("latitude", latitude);
        requestBody.put("name", stationName);
        requestBody.put("longitude", longitude);
        requestBody.put("altitude", altitude);

        return requestBody;

    }



    }

