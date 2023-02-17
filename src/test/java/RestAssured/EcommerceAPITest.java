package RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import pojo.Ecommerce.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceAPITest {

    public static void  main(String[] args){


        // Login
        RequestSpecification req =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("matheus-fritzen@hotmail.com");
        loginRequest.setUserPassword("MFritzen@123");


        //relaxedHTTPSValidation() is for when you have SSL certification saying your certification is not valid

        RequestSpecification reqlogin = given()./*relaxedHTTPSValidation().*/spec(req).body(loginRequest);
        LoginResponse loginResponse = reqlogin.when().post("/api/ecom/auth/login").then().extract().response().
                as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();


        //Add Product

        RequestSpecification addProductBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .build();

        ResponseSpecification resSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        RequestSpecification reqAddProduct = given().spec(addProductBaseReq)
                .param("productName", "Jordan") // Form Parameter
                .param("productAddedBy", userId) // Form Parameter
                .param("productCategory","fashion") // Form Parameter
                .param("productSubCategory", "shirts") // Form Parameter
                .param("productPrice", "11500") // Form Parameter
                .param("productDescription", "Air Jordan") // Form Parameter
                .param("productFor", "woman") // Form Parameter
                .multiPart("productImage", new File("/Users/matheusfritzen/Downloads/air jordan.jpeg")); // Form Parameter

        AddProductResponse addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().statusCode(201).extract().as(AddProductResponse.class);

        String productId = addProductResponse.getProductId();



        // Create Order

        RequestSpecification createOrderBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("Brazil");
        orderDetails.setProductOrderedId(productId);

        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        orderDetailsList.add(orderDetails);

        Orders orders = new Orders();
        orders.setOrders(orderDetailsList);

        RequestSpecification reqCreateOrder = given().spec(createOrderBaseReq)
                .body(orders);

        OrderResponse resCreateOrder = reqCreateOrder.when()
                .post("/api/ecom/order/create-order")
                .then().statusCode(201).extract().response().as(OrderResponse.class);

        String orderId = resCreateOrder.getOrders().get(0);


        // Delete Order

        RequestSpecification deleteBaseReq =  new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType(ContentType.JSON)
                .build();

        RequestSpecification reqDeleteOrder = given().spec(deleteBaseReq)
                .pathParam("orderId",orderId);

        DeleteOrderResponse delOrderResponse = reqDeleteOrder.when()
                .delete("/api/ecom/order/delete-order/{orderId}")
                .then().spec(resSpec).extract().response().as(DeleteOrderResponse.class);

        String delOrderMessage = delOrderResponse.getMessage();
        Assert.assertEquals("Orders Deleted Successfully", delOrderMessage);


        //Delete Product

        RequestSpecification reqDeleteProduct = given().spec(deleteBaseReq)
                .pathParam("productId",productId);

        DeleteProductResponse delProductResponse = reqDeleteProduct.when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().spec(resSpec).extract().response().as(DeleteProductResponse.class);

        String delProductMessage = delProductResponse.getMessage();

        Assert.assertEquals("Product Deleted Successfully", delProductMessage);


    }


}
