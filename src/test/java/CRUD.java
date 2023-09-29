import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;


public class CRUD {

    public static void main(String[] args) throws JSONException {

//        Response getResponse = given().get("https://api.restful-api.dev/objects");
//        // getResponse.prettyPrint();
//        System.out.println("This is the status Code >>>>>"+getResponse.statusCode());
//        System.out.println("This is the time taken >>>>>"+getResponse.time());
        //POST:


        /*{
            "clientName": "Postman",
                "clientEmail": "valentin@example.com"
        } */
//         String authPayload = "{\n" +
//                 "   \"clientName\": \"restapi\",\n" +
//                 "   \"clientEmail\": \"test856@example.com\"\n" +
//                 "}";

//        Response authResponse = given().contentType(ContentType.JSON).body(authPayload).post("https://simple-books-api.glitch.me/api-clients/");
//        String token = authResponse.jsonPath().get("accessToken");
//        System.out.println("This is the auth Token >>>>>"+token);

//        HashMap<String, String> orderPayload= new HashMap<String, String>();
//        orderPayload.put("bookId", "5");
//        orderPayload.put("customerName", "John");

        String orderPayload = "{\n"
                + "  \"bookId\": 3,\n"
                + "  \"customerName\": \"John\"\n"
                + "}";


//        JSONObject orderPayload= new JSONObject();
//        orderPayload.put("bookId", "5");
//       orderPayload.put("customerName", "John");

        Response orderResponse = given().header("Authorization", "Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034").contentType(ContentType.JSON).body(orderPayload).log().all().post("https://simple-books-api.glitch.me/orders");
        Assert.assertEquals(orderResponse.getStatusCode(), 201);
        orderResponse.prettyPrint();

//        Response getAllOrders =given().header("Authorization", "Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034").contentType(ContentType.JSON).get("https://simple-books-api.glitch.me/orders");
//
//        getAllOrders.prettyPrint();
//        Assert.assertEquals(getAllOrders.getStatusCode(), 200);
        String actualOrderID = orderResponse.jsonPath().get("orderId");
        System.out.println("actualOrderID is "+ actualOrderID);
//        //Assert.assertEquals(getAllOrders.jsonPath().get("id").toString(),"Sonia");

        Response getOrder =given().header("Authorization", "Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034").contentType(ContentType.JSON).log().all().get("https://simple-books-api.glitch.me/orders/"+actualOrderID);

        Assert.assertEquals(getOrder.getStatusCode(), 200);
        Assert.assertEquals(getOrder.jsonPath().get("id").toString(),actualOrderID);
        Assert.assertEquals(getOrder.jsonPath().get("customerName").toString(),"John");
        Assert.assertEquals(getOrder.jsonPath().get("bookId").toString(),"3");
        System.out.println("This is get orders response >>>>>>>>");
        getOrder.prettyPrint();

        //POsting


        Response deleteinValidOrder = given().header("Authorization","Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034" ).contentType(ContentType.JSON).delete("https://simple-books-api.glitch.me/orders/ghgghkljjlhl");
        Assert.assertEquals(deleteinValidOrder.getStatusCode(), 404);

        Response deleteOrder = given().header("Authorization","Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034" ).contentType(ContentType.JSON).delete("https://simple-books-api.glitch.me/orders/"+actualOrderID);
        Assert.assertEquals(deleteOrder.getStatusCode(), 204);

        Response deletedOrder = given().header("Authorization","Bearer 8d0694cd8abcb9103a24945eedc502d75092d79b15a1790c6909f44b31a25034" ).contentType(ContentType.JSON).delete("https://simple-books-api.glitch.me/orders/"+actualOrderID);
        Assert.assertEquals(deletedOrder.getStatusCode(), 404);




    }
}