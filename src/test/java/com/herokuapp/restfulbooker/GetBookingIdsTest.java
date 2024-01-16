package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBookingIdsTest  extends BaseTest{

    @Test
    public void getBookingIdsWithoutFilterTest(){
        //Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();//to print response body into console;

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        //Verify at least 1 booking id is in the list
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");//Т.к. наш ответ в json,
        //мы будем использовать jsonPath(), чтобы получить ответ. Передаем в него путь до нужного элемента

        Assert.assertFalse(bookingIds.isEmpty(), "List of booking ids is empty, but it should not be");

    }

    @Test
    public void getBookingIdsWithFilterTest(){
        //add query parameter
        spec.queryParam("firstname", "Sally");
        spec.queryParam("lastname", "Wilson");

        //Get response with booking ids
        Response response = RestAssured.given(spec).get("/booking");
        response.print();//to print response body into console;

        //Verify response is 200
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        //Verify at least 1 booking id is in the list
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");//Т.к. наш ответ в json,
        //мы будем использовать jsonPath(), чтобы получить ответ. Передаем в него путь до нужного элемента

        Assert.assertFalse(bookingIds.isEmpty(), "List of booking ids is empty, but it should not be");

    }
}
