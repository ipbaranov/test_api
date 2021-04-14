package authentication;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;

import static io.restassured.RestAssured.*;
import static org.mortbay.jetty.security.B64Code.encode;


public class getAccessTokenBarer {
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api-m.paypal.com/";
    }


    private static String username = "AfhhhhhhhhhhhhhhrpSaKE6GvuCUBCDGTisoTglpL79-mcT";
    private static String password = "EKV3Tq9lUMZBjSl_pvkxIZ3FdBtLDgggggggggggggia9fqhgbQY-";


    private static String token_resource = "https://api-m.paypal.com/v1";
    private static String endpoint_rest = "/oauth2/token";
    private static String endpoint_rest1 = "/";


    public static Response getToken(String authCode) {

        String authorization = encode(username + ":" + password);

        return

                given()
                        .header("authorization", "Basic " + authorization)
                        .contentType(ContentType.JSON)
                        .formParam("grant_type", "client_credentials")

        //.queryParam("code", authCode)
        //.queryParam("redirect_uri", redirectUri)
        //.queryParam("grant_type", "client_credentials")

                 .post(token_resource+endpoint_rest )
                .then()
                .statusCode(200)
                .extract()
                .response();


    }


   public static String parseForAccessToken  (Response loginResponse) {

        JSONObject JSONResponseBody = new JSONObject(loginResponse.body().asString());
        String result = JSONResponseBody.getString("access_token");
        System.out.println(result);

        return result;
    }


    @Test
    public void iShouldGetToken() throws UnsupportedEncodingException {

        Response tokenResponse = getToken("access_token");
        String accessToken = parseForAccessToken(tokenResponse);
        Assertions.assertNotNull(accessToken);
    }


}
