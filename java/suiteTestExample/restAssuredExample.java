package suiteTestExample;

import com.google.gson.GsonBuilder;
import de.zeigermann.xml.simpleImporter.Item;
import helperMethods.GetContentJson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.mapper.factory.GsonObjectMapperFactory;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;

public class restAssuredExample {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://exampel.com/1.0";
        RestAssured.basePath = "/regions";

    }


    @Test
    public void UsingQueryParametr_q_BasicSearch()   {
        given().queryParam("q", "Петер").expect().body("items.name", is(Arrays.asList("Санкт-Петербург"))).when().get();    }

    @Test
    public void UsingQueryParametr_g_minNumberlettersTosearch()   {
        given().queryParam("q", "мск").expect().body("items.name", is(Arrays.asList("Омск", "Томск"))).when().get(baseURI+basePath);
    }

    @Test
    public void UsingQuery_g_CapitalLetters()   {
        given().queryParam("q", "МсК").expect().body("items.name", is(Arrays.asList("Омск", "Томск"))).when().get(baseURI+basePath);
    }

    @Test
    public void UsingQuery_g_less_minLetters()   {
        Object myObj = null;
        given().queryParam("q", "Мс").expect().body("items.name", is(myObj)).when().get(baseURI+basePath);
    }

    @Test
    public void UsingQuery_g_EmptyValue()   {

        given().queryParam("q", "").expect().when().get("https://regions-test.2gis.com/1.0/regions").then().assertThat().statusCode(500);
    }

    @Test
    public void UsingQuery_g_and_CountreCodeChekThatIgnor()   {
        given().queryParam("q", "мск", "country_code", "cz").expect().body("items.name", is(Arrays.asList("Омск", "Томск"))).when().get(baseURI+basePath);
    }

    @Test
    public void UsingQuery_g_and_pageChekThatIgnor()   {
        given().queryParam("q", "мск", "page", "0").expect().body("items.name", is(Arrays.asList("Омск", "Томск"))).when().get(baseURI+basePath);
    }

    @Test
    public void UsingQuery_g_and_pageSizeChekThatIgnor()   {
        given().queryParam("q", "мск", "page_size", "0").expect().body("items.name", is(Arrays.asList("Омск", "Томск"))).when().get(baseURI+basePath);
    }
    @Test
    public void UsingQuery_Country_codeEmptyValue() {
        given().queryParam("country_code", " ").expect().when().get(baseURI+basePath).then().assertThat().statusCode(500);
    }


    @Test
    public void UsingQueryCountry_codeChek()   {
        List categories;
        categories = GetContentJson.readJsonFileDynamic(baseURI+basePath, "$..country[?(@.code == 'kg')]");
        for (Object cat : categories) {
            Assert.assertEquals("{name=Кыргызстан, code=kg}", (cat).toString());
            System.out.println(cat);
        }}



    @Test
    public void UsingQueryCountry_code1()   {
        List categories;
        categories = GetContentJson.readJsonFileDynamic(baseURI+basePath, "$..country[?(@.code == 'kz')]");
        for (Object cat : categories) {
            Assert.assertEquals("{name=Казахстан, code=kz}", (cat).toString());
            System.out.println(cat);
        }}


    @Test
    public void UsingQueryCountry_code2 ()   {
        List categories;
        categories = GetContentJson.readJsonFileDynamic(baseURI+basePath, "$..country[?(@.code == 'ru')]");
        for (Object cat : categories) {
            Assert.assertEquals("{name=Россия, code=ru}", (cat).toString());
            System.out.println(cat);


        }
    }
    @Test
    public void DefautCountry_codeHasAll() {
        given().queryParam("total", "").expect().body("[code]",  hasValue("kz" )).
                when().get(baseURI+basePath);

    }
    @Test
    public void extractMapOfObjectWithFindAllAndFind_findSinglePlayerOfACertainPositionAndNationality() {
        Response response = RestAssured.get(baseURI+basePath);

        ArrayList<String> regioneCode = response.path("items.find { it.code == 'ru'}");
        for(String a:regioneCode){

            System.out.println("The amount value fetched is "+a);

        }


    }
    @Test
    public  void getSpecificPartOfResponseBody(){
        ArrayList<String> amounts = RestAssured.get(baseURI+basePath).then().extract().path("items.country.code") ;

        for(String a:amounts){
            if (a.equals("ru" )){
                System.out.println("The  is "+a);
            } else{
                System.out.println("Statement is false"+a);


            }


        }}
    @Test
    public void extractLastValueWhenSeveralReturned_findLastTeamName() {
        Response response = RestAssured.get( baseURI+basePath);
        String lastTeamName = response.path("teams.name[-1]");
        System.out.println(lastTeamName);
    }
    @Test
    public void extractLastValueWhenSeveralReturned_findLastTeamName1(){
        List<String> a = new ArrayList<String>(Arrays.asList("kz", "ru", "kg",  "cz"));
        List<String> b = given()
                .contentType(io.restassured.http.ContentType.JSON).get(
                        baseURI+basePath).then().extract().
                        path("items.country.code") ;
        // b.forEach(System.out::println);
        boolean test=true;

        for (String ad:a){

            if(!(b.stream().anyMatch(x->x.contains(ad))))
            {
                test = false;
                System.out.println("Содержит не все элементы " +ad);
                //bre
            }

        }}
    @Test
    private void extractLastValueWhenSeveralReturned(){


        List<String> a = new ArrayList<String>(Arrays.asList("ru","kz", "bu","fr", "kg", "cz", "us"));

        List<String> b =  given().contentType(io.restassured.http.ContentType.JSON).get(
                baseURI+basePath).then().extract().
                path("items.country.code") ;
        boolean test=true;

        for (String ad:a){

            if(!(b.stream().anyMatch(x-> x==ad)))
            {
                test = false;
                System.out.println("Содержит не все элементы " +ad);

            }
        }


    }


}
