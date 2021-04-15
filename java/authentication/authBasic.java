package authentication;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class authBasic {
    
    private String path = "/auth";
    private String username = "admin";
    private String password = "password123";
    private String result;
    private String token = "f0c614addac3ae2";
    //Initializing Rest API's URL
    String APIUrl = "https://restful-booker.herokuapp.com/auth";
    //Initializing payload or API body
    String APIBody =  "{\"username\":\"admin\",\"password\":\"password123\"}";

    //language=JSON
    String jsonBody = "{\n" +
            "  \"name\": \"Foo\"\n" +
            "}";

    private String validRequest = "{\n" +
            "    \"token\": \"gghhhh\",\n" +
            "}";
    @Before
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void basicAuthLogin() {

        given().auth().preemptive().basic(username, password)
                .body(jsonBody)
                .contentType(ContentType.JSON)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("data", equalTo("hello Foo"));
    }
    @Test
    public void autnTwo(){



        // Building request using requestSpecBuilder
        RequestSpecBuilder builder = new RequestSpecBuilder();

        //Setting API's body
        builder.setBody(APIBody);

        //Setting content type as application/json or application/xml
        builder.setContentType("application/json; charset=UTF-8");


        RequestSpecification requestSpec = builder.build();

        //Making post request with authentication, leave blank in case there are no credentials- basic("","")
        Response response = given().auth().preemptive().basic(username, password)
                .spec(requestSpec).when().post(APIUrl);

        JSONObject JSONResponseBody = new JSONObject(response.body().asString());

        //Fetching the desired value of a parameter
        String result = JSONResponseBody.getString("token");
        System.out.println(result);


        Assert.assertTrue(true);



    }




    @Test
    public void auth2(){

        given().auth()
                .oauth2(token)
                .when()
                .get(APIUrl)
                .then()
                .statusCode(200);
    }

}



