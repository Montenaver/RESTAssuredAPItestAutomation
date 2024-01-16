package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest{

    @Test(enabled = false)//Чтобы вырубить тест
    public void getBookingTest(){
        //1. Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //2. Get booking Id
        int bookingid = responseCreate.jsonPath().getInt("bookingid");


        //Set path parameter
        spec.pathParam("bookingId", bookingid);

        //Get response with booking
        Response response = RestAssured.given(spec).get("/booking/{bookingId}");//Чтобы передать pathParam
        //вставляем его в {}
        response.print();

        //Verify status code
        //Тут мы пользыем hard assertion, т.е. тест упадет срзу как наткнется на несоответствие
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        //Verify all field
        //Тут мы используем soft assertion, т.е. тест вначале исполнит все assertions, и тоько потом упадет с
        //ошибкй, если любой изних был провален
        SoftAssert softAssert = new SoftAssert();

        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");
        int totalprice = response.jsonPath().getInt("totalprice");
        boolean depositpaid = response.jsonPath().getBoolean("depositpaid");
        //checkin/checkout - это объекты, внутри которых лежат другие объекты
        //=> путь до него прописывается через токи: bookingdates.checkin
        String checkin = response.jsonPath().getString("bookingdates.checkin");
        String checkout = response.jsonPath().getString("bookingdates.checkout");
        String additionalneeds = response.jsonPath().getString("additionalneeds");

        softAssert.assertEquals(firstName, "Jack", "FirstName should be Jack, but it is " + firstName);
        softAssert.assertEquals(lastName, "Black", "LastName should be Black, but it is " + lastName);
        softAssert.assertEquals(totalprice, 123,"Totalprice should be 123, but it is " + totalprice);
        softAssert.assertFalse(depositpaid, "Depositpaid should be 'false', but it is not");
        softAssert.assertEquals(checkin, "2023-11-01","Checkin should be '2023-11-01', but it is " + checkin);
        softAssert.assertEquals(checkout, "2023-11-23","Checkout should be '2023-11-23', but it is " + checkout);
        softAssert.assertEquals(additionalneeds, "Baby crib",
                "Additionalneeds should be 'Baby crib', but it is " + additionalneeds);

        softAssert.assertAll();


//        Assert.assertEquals(firstName, "Sally", "FirstName should be Sally, but it is " + firstName);
//        Assert.assertEquals(lastName, "Brown", "LastName should be Brown, but it is " + lastName);

    }

    @Test
    public void getBookingXMLTest(){//Нужно просто добавит header в нащ request

        //1. Create booking
        Response responseCreate = createBooking();
        responseCreate.print();

        //2. Get booking Id
        int bookingid = responseCreate.jsonPath().getInt("bookingid");


        //Set path parameter
        spec.pathParam("bookingId", bookingid);

        //Get response with booking
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        Response response = RestAssured.given(spec).get("/booking/{bookingId}");//Чтобы передать pathParam
        //вставляем его в {}
        response.print();

        //Verify status code
        //Тут мы пользыем hard assertion, т.е. тест упадет срзу как наткнется на несоответствие
        Assert.assertEquals(response.getStatusCode(), 200,
                "Status code should be 200, but it is " + response.getStatusCode());

        //Verify all field
        //Тут мы используем soft assertion, т.е. тест вначале исполнит все assertions, и тоько потом упадет с
        //ошибкй, если любой изних был провален
        SoftAssert softAssert = new SoftAssert();

        String firstName = response.xmlPath().getString("booking.firstname");
        String lastName = response.xmlPath().getString("booking.lastname");
        int totalprice = response.xmlPath().getInt("booking.totalprice");
        boolean depositpaid = response.xmlPath().getBoolean("booking.depositpaid");

        String checkin = response.xmlPath().getString("booking.bookingdates.checkin");
        String checkout = response.xmlPath().getString("booking.bookingdates.checkout");
        String additionalneeds = response.xmlPath().getString("booking.additionalneeds");

        softAssert.assertEquals(firstName, "Jack", "FirstName should be Jack, but it is " + firstName);
        softAssert.assertEquals(lastName, "Black", "LastName should be Black, but it is " + lastName);
        softAssert.assertEquals(totalprice, 123,"Totalprice should be 123, but it is " + totalprice);
        softAssert.assertFalse(depositpaid, "Depositpaid should be 'false', but it is not");
        softAssert.assertEquals(checkin, "2023-11-01","Checkin should be '2023-11-01', but it is " + checkin);
        softAssert.assertEquals(checkout, "2023-11-23","Checkout should be '2023-11-23', but it is " + checkout);
        softAssert.assertEquals(additionalneeds, "Baby crib",
                "Additionalneeds should be 'Baby crib', but it is " + additionalneeds);

        softAssert.assertAll();


//        Assert.assertEquals(firstName, "Sally", "FirstName should be Sally, but it is " + firstName);
//        Assert.assertEquals(lastName, "Brown", "LastName should be Brown, but it is " + lastName);

    }
}
