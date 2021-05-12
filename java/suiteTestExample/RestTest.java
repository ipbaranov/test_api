package suiteTestExample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.GsonObjectMapperFactory;
import io.restassured.response.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static io.restassured.RestAssured.*;

public class RestTest {

    public static String jsonAsString;
    String Token1 = "token";


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://begetwin.beget.tech/wp-json/wc/v3";
        RestAssured.basePath = "/customers";

    }

    @BeforeClass
    public static void callRidesAPI() {

        Response response = given().auth().oauth2("token").
                when().
                get("").
                then().
                contentType(ContentType.JSON).
                extract().response();
        jsonAsString = response.asString();


    }

    @Test
    public void extractLastValueReturned_Map() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(jsonAsString, new TypeReference<Map<String, Object>>() {
        });
        Set<String> keys = map.keySet();
        System.out.println("Ключи: " + keys);
        Assert.assertEquals(keys, Keys);
    }
}
















