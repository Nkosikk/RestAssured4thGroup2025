package Utils;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.UUID;

public class generateTestData {

   static Faker faker = new Faker();

    public static String firstName=faker.name().firstName();
    public static String lastName=faker.name().lastName();
    public static String email = firstName+"."+lastName + "@gmail.com";

   public static String newFirstName=faker.name().firstName();
   public static String newLastName=faker.name().lastName();
 public static String fullName = newFirstName + " " + newLastName;

    public static String password =faker.internet().password(8, 16, true, true, true);

    @Test public void generateData() {
    System.out.println(fullName);}
}
