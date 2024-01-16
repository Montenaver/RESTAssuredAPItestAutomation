package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest{

    @Test
    public void deleteBookingTest(){
        //1. Create booking
        //Создадим новую запись
        Response responseCreate = createBooking();
        responseCreate.print();

        //2. Get booking Id
        //Нам нужен id этой записи из response, чтобы по нему потом проверить, что все верно обновилось
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //3. Delete booking
        Response responseDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
                delete("/booking/" + bookingid);
        responseDelete.print();

        //4. Verification
        //Verify status code
        Assert.assertEquals(responseDelete.getStatusCode(), 201,
                "Status code should be [201], but it is [" + responseDelete.getStatusCode() + "]");

        //Verify body response
        Assert.assertEquals(responseDelete.getBody().asString(), "Created",
                "The response should be 'Created', but it is [" + responseDelete.getStatusCode() + "]");

        //5. Add getBooking, to verify, there is actually no such a booking in a system

        Response responseGet = RestAssured.given(spec).get("/booking/" + bookingid);
        responseGet.print();

        //6. Verification
        //Verify status code
        Assert.assertEquals(responseGet.getStatusCode(), 404,
                "Status code should be [404], but it is [" + responseGet.getStatusCode() + "]");

        //Verify body response
        Assert.assertEquals(responseGet.getBody().asString(), "Not Found",
                "The response should be 'Not Found', but it is [" + responseGet.getStatusCode() + "]");

    }
}
