package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp(){
         spec = new RequestSpecBuilder().
                setBaseUri("https://restful-booker.herokuapp.com").
                build();//Create a variable
    }
    protected Response createBooking() {
        //Create JSON body
        //Добавим JSON In Java dependency, чтобы иметь возможность создавать JSON Objects и создадим его
        JSONObject body = new JSONObject();//И заполним его значениями
        body.put("firstname", "Jack");
        body.put("lastname", "Black");
        body.put("totalprice", 123);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();//Создадим еще один JSONObject
        bookingdates.put("checkin", "2023-11-01");
        bookingdates.put("checkout", "2023-11-23");
        body.put("bookingdates", bookingdates);//И засуним в JSONObject body объект JSONObject bookingdates

        body.put("additionalneeds", "Baby crib");

        //Get response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString()).
                post("/booking");//Для POST-метода
        //нужно передать body, и дать программе знать, что это body у нас передается в JSON. Плюс в body(),
        //мы должны передавать String, а не объект, поэтому добавим toString()
        return response;
    }
}
