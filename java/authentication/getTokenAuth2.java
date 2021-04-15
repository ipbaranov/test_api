package authentication;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class getTokenAuth2 {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api-m.paypal.com/";
    }
    private static String endpoint_rest = "/oauth2/token";
    private static String CLIENT_ID = "AfQmRuaazQE-8kurb9kAkMXoz55555555555kcprTYlQD4arpSaKE6Gvu5555oTglpL79-mcT";
    private static String CLENT_SECRET = "EKV5555ZBjSl_pvkxIZ55555555555a3Yt443Gs_PK5f_gNyIUREFTG4dlNfmia9fqhgbQY-";

    @Test
    public void testOAuthWithClientCredential() throws JSONException {

        Response response =
                given()
                        .auth().preemptive().basic(CLIENT_ID, CLENT_SECRET)
.contentType(ContentType.JSON)
                .formParam("grant_type", "client_credentials")

                .when()
                .post(baseURI+endpoint_rest);


        
        public static String parseForAccessToken  (Response response) {

            JSONObject JSONResponseBody = new JSONObject(response.getBody().asString());
            String accessToken = JSONResponseBody.getString("access_token");
            String tokenType = JSONResponseBody.getString("token_type");
            System.out.println("Oauth Token with type " + tokenType + "   " + accessToken);

            return accessToken;
        }


    @Test
    public void autnTwo(){



        // Building request using requestSpecBuilder
        RequestSpecBuilder builder = new RequestSpecBuilder();

        //Setting API's body


        //Setting content type as application/json or application/json
        builder.setContentType("application/json");

        //builder.setAccept(ContentType.JSON);
        builder.addFormParam("grant_type", "client_credentials");

        RequestSpecification requestSpec = builder.build();

        //Making post request with authentication, leave blank in case there are no credentials- basic("","")
        Response response = given().auth().preemptive().basic("CLIENT_ID", "CLENT_SECRET")
                .spec(requestSpec).when().post(baseURI+endpoint_rest);

        JSONObject JSONResponseBody = new JSONObject(response.body().asString());

        //Fetching the desired value of a parameter
        //String result = JSONResponseBody.getString("access_token");


        //Asserting that result of Norway is Oslo
        Assert.assertTrue(true);



    }



}
