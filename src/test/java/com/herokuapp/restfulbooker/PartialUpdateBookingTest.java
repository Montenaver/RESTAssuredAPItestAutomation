package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest{

    @Test
    public void partialUpdateBookingTest(){
        //1. Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //2. Get booking Id
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //3. Create JSON body
        JSONObject body = new JSONObject();//И заполним его значениями
        body.put("firstname", "Igor");

        JSONObject bookingdates = new JSONObject();//Создадим еще один JSONObject
        bookingdates.put("checkin", "2024-01-03");
        bookingdates.put("checkout", "2024-11-23");

        body.put("bookingdates", bookingdates);

        //4. Update booking
        Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
                contentType(ContentType.JSON).body(body.toString()).
                patch("/booking/" + bookingid);
        responseUpdate.print();

        //5. Verification
        //Verify status code
        Assert.assertEquals(responseUpdate.getStatusCode(), 200,
                "Status code should be 200, but it is " + responseUpdate.getStatusCode());

        SoftAssert softAssert = new SoftAssert();

        String firstName = responseUpdate.jsonPath().getString("firstname");
        String lastName = responseUpdate.jsonPath().getString("lastname");
        int totalprice = responseUpdate.jsonPath().getInt("totalprice");
        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");

        String checkin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        String checkout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        String additionalneeds = responseUpdate.jsonPath().getString("additionalneeds");

        softAssert.assertEquals(firstName, "Igor", "FirstName should be Igor, but it is " + firstName);
        softAssert.assertEquals(lastName, "Black", "LastName should be Black, but it is " + lastName);
        softAssert.assertEquals(totalprice, 123,"Totalprice should be 123, but it is " + totalprice);
        softAssert.assertFalse(depositpaid, "Depositpaid should be 'false', but it is not");
        softAssert.assertEquals(checkin, "2024-01-03","Checkin should be '2024-01-03', but it is " + checkin);
        softAssert.assertEquals(checkout, "2024-11-23","Checkout should be '2024-11-23', but it is " + checkout);
        softAssert.assertEquals(additionalneeds, "Baby crib",
                "Additionalneeds should be 'Baby crib', but it is " + additionalneeds);

        softAssert.assertAll();

    }


}
