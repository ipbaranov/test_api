package suiteTestExample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.*;

public class RestTest {

    public static String jsonAsString;
    public static String finalResult;

    String Token1 = "e9d4ybokgrkwx5krx9ollorhc9i3csmmbcmyuuer";


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://begetwin.beget.tech/wp-json/wc/v3";
        RestAssured.basePath = "/customers";

    }

    @BeforeClass
    public static void callRidesAPI() {

        Response response = given().auth().oauth2("e9d4ybokgrkwx5krx9ollorhc9i3csmmbcmyuuer").
                when().
                get("").
                then().
                contentType(ContentType.JSON).
                extract().response();
        jsonAsString = response.asString();

        finalResult = response.getBody().path("$[]");

    }

    @Test
    public void extractLastValueReturned_Map() throws IOException {
        List<String> Keys = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(finalResult, new TypeReference<Map<String, Object>>() {
        } );
        Set<String> keys = map.keySet();
        System.out.println("Ключи: " + keys);
    }
    @Test
    public void putMapGson(){
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Gson gson = new Gson();
        Map<String, String> myMap = gson.fromJson(finalResult, type);
        Set<String> keys = myMap.keySet();
        System.out.println("Ключи: " + keys);

    }
}
















