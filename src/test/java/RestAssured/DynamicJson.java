package RestAssured;

import RestAssured.files.ReUsableMethods;
import RestAssured.files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;

import static io.restassured.RestAssured.given;

public class DynamicJson {


    @org.testng.annotations.Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle)
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String response =
            given()
                .header("Content-Type", "application/json")
                .body(payload.AddBook(isbn, aisle))
            .when().
                    post("/Library/Addbook.php")
            .then()
                    .log().all()
                    .assertThat().statusCode(200)
                    .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");

        System.out.println(id);

    }

    @DataProvider(name="BooksData")
    public Object[][] getData(){

        return new Object[][] {{"test1","1234"}, {"test2", "5421"}, {"test3", "3241"}};

    }
}
