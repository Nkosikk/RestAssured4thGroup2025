package Utils;

import com.github.javafaker.Faker;

import java.util.UUID;

public class generateTestData {

   static Faker faker = new Faker();

    public static String external_id="EXT_" + (int)(Math.random() * 100000);;
    public static String name= "Station_" + (int)(Math.random() * 100000);;
    public static double latitude=37.76;
    public static double longitude=-122.43;
    public static double altitude=150;

    public static String stationName = faker.address().cityName() + " Station";

}
