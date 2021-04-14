package authentication;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class getToken {

    public static Response response;
    private String userAdminClientId = System.getenv("AfQmRuaazQE-8kurb9kAkMXozVelC3kwTf6PUGkcprTYlQD4arpSaKE6GvuCUBCDGTisoTglpL79-mcT");
    private String userAdminClientSecret = System.getenv("EKV3Tq9lUMZBjSl_pvkxIZ3FdBtLDTc1_Pyjusa3Yt443Gs_PK5f_gNyIUREFTG4dlNfmia9fqhgbQY-");

    private String oauth2Payload = "{\n" +
            "  \"client_id\": \"" + userAdminClientId + "\",\n" +
            "  \"client_secret\": \"" + userAdminClientSecret + "\",\n" +
            "  \"audience\": \"https://some-url.com/user\",\n" +
            "  \"grant_type\": \"client_credentials\",\n" +
            "  \"scope\": \"user:admin\" \n}";

    private static String createUserPayload = "{\n" +
            "  \"username\": \"api-user\",\n" +
            "  \"email\": \"api-user@putsbox.com\",\n" +
            "  \"password\": \"Passw0rd123!\",\n" +
            "  \"firstName\": \"my-first-name\",\n" +
            "  \"lastName\": \"my-last-name\",\n" +
            "  \"roles\": [\"read\"] \n}";

    public void userAdminConfigSetup() {
        RequestSpecification requestSpecification = given().auth().oauth2(getAccessToken(oauth2Payload))
                .header("Accept", ContentType.JSON.getAcceptHeader())
                .contentType(ContentType.JSON);
    }

    public String getAccessToken(String payload) {
        return given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("v1/oauth2/token")
                .then().extract().response()
                .jsonPath().getString("access_token");
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api-m.paypal.com/";
    }

    @Test
    public void createUser() {
        userAdminConfigSetup();
        response = given(requestSpecification)
                .body(createUserPayload)
                .post("/user")
                .then().extract().response();

        Assertions.assertEquals(201, response.statusCode());
    }
}