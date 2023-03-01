package TesteApi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class TesteApi {

    private String vote_id;
    private String fav_id;

    @BeforeAll
    public static void urlBase(){

        RestAssured.baseURI = "https://api.thecatapi.com/v1/";

    }

//    @Test
    public void cadastro(){
        String url = "user/passwordlesssignup";
        String body = "{\"email\": \"matusfrditzen@hotmail.com\", \"appDescription\": \"teste the cat api\"}";

        Response response = given().log().all().contentType(ContentType.JSON).body(body).
                when().post(url);

        response.then().log().all().statusCode(200).body("message",containsString("SUCCESS"));
        //        response.then().statusCode(200).body("message",equalTo("SUCCESS"));

        System.out.println("RETORNO => " + response.body().asString());
    }

    public void votacao(){
        String url = "votes/";
        String body = "{\"image_id\": \"acn\", \"value\": \"true\", \"sub_id\": \"demo-3de809\"}";

        Response response = given().contentType(ContentType.JSON).
                    header("x-api-key","live_hOU1mTGtGUK225g4ls8Si9LACiYY7UWVH6mqNx1UVTyCcLIYVeVMTBzpkoYGaj0A").
                    body(body).
                when().post(url);

        response.then().statusCode(201).body("message",containsString("SUCCESS"));
        //        response.then().statusCode(200).body("message",equalTo("SUCCESS"));

        vote_id = response.jsonPath().getString("id");

        System.out.println("ID => " + vote_id);
        System.out.println("RETORNO => " + response.body().asString());
    }

    public void deletaVoto(){
        String url = "votes/{vote_id}";

        Response response = given().contentType(ContentType.JSON).
                header("x-api-key","live_hOU1mTGtGUK225g4ls8Si9LACiYY7UWVH6mqNx1UVTyCcLIYVeVMTBzpkoYGaj0A").
                pathParam("vote_id",vote_id).
                when().delete(url);

        response.then().statusCode(200).body("message",containsString("SUCCESS"));
        //        response.then().statusCode(200).body("message",equalTo("SUCCESS"));

        System.out.println("RETORNO => " + response.body().asString());
    }


//    @Test
    public void deletaVotacao(){
        votacao();
        deletaVoto();
    }


    public void favoritar(){
        String url = "favourites/";
        String body = "{\"image_id\": \"d99\"}";

        Response response = given().contentType(ContentType.JSON).
                header("x-api-key","live_hOU1mTGtGUK225g4ls8Si9LACiYY7UWVH6mqNx1UVTyCcLIYVeVMTBzpkoYGaj0A").
                body(body).
                when().post(url);

        response.then().statusCode(200).body("message",containsString("SUCCESS"));
//        response.then().statusCode(200).body("message",equalTo("SUCCESS"));

        fav_id = response.jsonPath().getString("id");

        System.out.println("ID => " + fav_id);
        System.out.println("RETORNO => " + response.body().asString());
    }

    public void deletaFavorito(){
        String url = "favourites/{favourite_id}";

        Response response = given().contentType(ContentType.JSON).
                header("x-api-key","live_hOU1mTGtGUK225g4ls8Si9LACiYY7UWVH6mqNx1UVTyCcLIYVeVMTBzpkoYGaj0A").
                pathParam("favourite_id",fav_id).
                when().delete(url);

        response.then().statusCode(200).body("message",containsString("SUCCESS"));
        //        response.then().statusCode(200).body("message",equalTo("SUCCESS"));

        System.out.println("RETORNO => " + response.body().asString());
    }

//    @Test
    public void favoritaDesfavorita(){
        favoritar();
        deletaFavorito();

    }


}
