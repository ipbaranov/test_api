package authentication;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.contains;

public class authBasic {
    private String username = "admin";
    private String password = "password123";
    String APIUrl = "https://restful-booker.herokuapp.com/auth";
    String APIBody = "{\"username\":\"admin\",\"password\":\"password123\"}";


    @Before
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void basicAuthLogin() {

        Response response = given().auth().preemptive().basic(username, password)
                .contentType(ContentType.JSON)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then().extract().response();
        Assert.assertEquals(response.statusCode(), 400);


    }

    @Test
    public void autnTwo() {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBody(APIBody);
        builder.setContentType("application/json; charset=UTF-8");
        RequestSpecification requestSpec = builder.build();
        Response response = given().auth().preemptive().basic(username, password).
                spec(requestSpec).when().post(APIUrl);
        JSONObject JSONResponseBody = new JSONObject(response.body().asString());
        String result = JSONResponseBody.getString("token");
        System.out.println(result);
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void auth2() {

        given().auth()
                .oauth2("password")
                .when()
                .get(APIUrl)
                .then()
                .statusCode(200);
    }
}

