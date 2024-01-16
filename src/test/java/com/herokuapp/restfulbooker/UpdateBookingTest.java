package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest{

    @Test
    public void updateBookingTest(){
        //1. Create booking
        //Создадим новую запись
        Response responseCreate = createBooking();
        responseCreate.print();

        //2. Get booking Id
        //Нам нужен id этой записи из response, чтобы по нему потом проверить, что все верно обновилось
        int bookingid = responseCreate.jsonPath().getInt("bookingid");

        //3. Create JSON body
        JSONObject body = new JSONObject();//И заполним его значениями
        body.put("firstname", "Milla");
        body.put("lastname", "Yovovich");
        body.put("totalprice", 789);
        body.put("depositpaid", true);

        JSONObject bookingdates = new JSONObject();//Создадим еще один JSONObject
        bookingdates.put("checkin", "2023-08-22");
        bookingdates.put("checkout", "2023-12-03");
        body.put("bookingdates", bookingdates);//И засуним в JSONObject body объект JSONObject bookingdates

        body.put("additionalneeds", "Bruce Willis");

        //4. Update booking
        Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").contentType(ContentType.JSON).body(body.toString()).
                put("/booking/" + bookingid);
        responseUpdate.print();

        //4. Verifications
        //Verify status code
        Assert.assertEquals(responseUpdate.getStatusCode(), 200,
                "Status code should be [200], but it is [" + responseUpdate.getStatusCode() + "]");

        SoftAssert softAssert = new SoftAssert();

        String firstName = responseUpdate.jsonPath().getString("firstname");
        String lastName = responseUpdate.jsonPath().getString("lastname");
        int totalprice = responseUpdate.jsonPath().getInt("totalprice");
        boolean depositpaid = responseUpdate.jsonPath().getBoolean("depositpaid");

        String checkin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        String checkout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        String additionalneeds = responseUpdate.jsonPath().getString("additionalneeds");

        softAssert.assertEquals(firstName, "Milla", "FirstName should be Milla, but it is " + firstName);
        softAssert.assertEquals(lastName, "Yovovich", "LastName should be Yovovich, but it is " + lastName);
        softAssert.assertEquals(totalprice, 789,"Totalprice should be 789, but it is " + totalprice);
        softAssert.assertTrue(depositpaid, "Depositpaid should be 'true', but it is not");
        softAssert.assertEquals(checkin, "2023-08-22","Checkin should be '2023-08-22', but it is " + checkin);
        softAssert.assertEquals(checkout, "2023-12-03","Checkout should be '2023-12-03', but it is " + checkout);
        softAssert.assertEquals(additionalneeds, "Bruce Willis",
                "Additionalneeds should be 'Bruce Willis', but it is " + additionalneeds);

        softAssert.assertAll();
    }

}
