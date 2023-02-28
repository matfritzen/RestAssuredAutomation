package XSSFWorkbook.test;

import RestAssured.files.ReUsableMethods;
import XSSFWorkbook.dataDriven;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class excelDriven extends ReUsableMethods {

    @Test
    public void addBook() throws IOException
    {
        dataDriven d=new dataDriven();
        ArrayList data=d.getData("RestAddbook","RestAssured");


        HashMap<String, Object> map = new HashMap<>();
        map.put("name", data.get(1));
        map.put("isbn", data.get(2));
        map.put("aisle", data.get(3));
        map.put("author", data.get(4));

	/*	HashMap<String, Object>  map2 = new HashMap<>();
		map.put("lat", "12");
		map.put("lng", "34");
		map.put("location", map2);*/


        RestAssured.baseURI="http://216.10.245.166";
        Response resp=given().
                header("Content-Type","application/json").
                body(map).
                when().
                post("/Library/Addbook.php").
                then().assertThat().statusCode(200).
                extract().response();
        JsonPath js= ReUsableMethods.rawToJson(resp.asString());
        String id=js.get("ID");
        System.out.println(id);


    }
}
