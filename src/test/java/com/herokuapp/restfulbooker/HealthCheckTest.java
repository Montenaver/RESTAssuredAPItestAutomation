package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest{

    @Test
    public void healthCheckTest(){

        given().//bdc index?
                spec(spec).//use spec variable
        when().
                get("/ping").//т.е. теперь достаточно будет передать только endpoint, а основно url передается через spec
        then().
                assertThat().
                statusCode(201);

    }

    @Test
    public void headersAndCookiesTest(){
        //Можем добавить header and cookies к RequestSpecificatn даже до создания реального response
        Header someHeader = new Header("Some_name", "Some_value");
        spec.header(someHeader);

        Cookie someCookie = new Cookie.Builder("some_cookie", "some_cookie_value").build();
        spec.cookie(someCookie);

        Response response = RestAssured.given(spec).
                cookie("Test cookie name", "Test cookie value").
                header("Test header name", "Test header value").
                log().all().//add this to log cookies and headers with RestAssured logging method
                get("/ping");
        //Извлечем headers and cookies из ответа и выведем их на консоль

        //Get headers
        Headers headers = response.getHeaders();
        System.out.println("Headers: " + headers);

        //Get single header
        //1. get single header through Header class
        Header serverHeader1 = headers.get("Server");
        System.out.println(serverHeader1.getName() + ": " + serverHeader1.getValue());

        //2. get single header through Response class (возвращает только значение этого заголовка)
        String serverHeader2 = response.getHeader("Server");
        System.out.println("Server: " + serverHeader2);

                //Get cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println("Cookies: " + cookies);

    }
}
