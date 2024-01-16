package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest extends BaseTest{

    @Test
    public void createBookingTest(){
        Response response = createBooking();
        response.print();

        //Verifications
        //Verify status code
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        SoftAssert softAssert = new SoftAssert();

        String firstName = response.jsonPath().getString("booking.firstname");
        String lastName = response.jsonPath().getString("booking.lastname");
        int totalprice = response.jsonPath().getInt("booking.totalprice");
        boolean depositpaid = response.jsonPath().getBoolean("booking.depositpaid");

        String checkin = response.jsonPath().getString("booking.bookingdates.checkin");
        String checkout = response.jsonPath().getString("booking.bookingdates.checkout");
        String additionalneeds = response.jsonPath().getString("booking.additionalneeds");

        softAssert.assertEquals(firstName, "Jack", "FirstName should be Jack, but it is " + firstName);
        softAssert.assertEquals(lastName, "Black", "LastName should be Black, but it is " + lastName);
        softAssert.assertEquals(totalprice, 123,"Totalprice should be 123, but it is " + totalprice);
        softAssert.assertFalse(depositpaid, "Depositpaid should be 'false', but it is not");
        softAssert.assertEquals(checkin, "2023-11-01","Checkin should be '2023-11-01', but it is " + checkin);
        softAssert.assertEquals(checkout, "2023-11-23","Checkout should be '2023-11-23', but it is " + checkout);
        softAssert.assertEquals(additionalneeds, "Baby crib",
                "Additionalneeds should be 'Baby crib', but it is " + additionalneeds);

        softAssert.assertAll();
    }

    @Test
    public void createBookingWithPOJOTest(){
        //Create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2022-02-22", "2023-03-03");
        Booking booking = new Booking("Simon", "Bearer", 253, true,
        bookingdates, "More alcohol");

        //Get response
        Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).//Тут не надо
                //toString(), т.к. RestAssured автоматически конвертирет instance нашего Booking.class в json body,
                //который нужно послать по указанному ниже адресу
                post("/booking");
        response.print();
        BookingId bookingid = response.as(BookingId.class);//Мы по сути говорим RestAssured взять этот класс и
        //конвертнуть response body as this class. Поэтому все getters and setters должны полностью совпадать с тем,
        //что будет в BookingId

        //Verifications
        //Verify status code
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        System.out.println("Request booking : " + booking.toString());
        System.out.println("Response booking: " + bookingid.getBooking().toString());

        //И теперь для верификации мы можем просто сравнить данные из ответа
        Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());


    }

}
