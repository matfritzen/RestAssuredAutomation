package RestAssured;

import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import static io.restassured.RestAssured.given;

public class GraphQLScript {


    public static void main(String[] args){

        // Query

        int characterId = 1036;
        int episodeId = 1;
        int locationId = 1376;

        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId : Int!, $episodeId : Int!, $locationId : Int!){\\n\\n    character(characterId : $characterId){\\n        name\\n        gender\\n        status\\n        id\\n    }\\n    location(locationId : $locationId){\\n        name\\n        dimension\\n    }\\n    episode(episodeId : $episodeId){\\n        name\\n        air_date\\n        episode\\n    }\\n    characters(filters: {name: \\\"Rahul\\\"}){\\n        info{\\n            count\\n        }\\n\\n    }\\n}\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":"+episodeId+",\"locationId\":"+locationId+"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String characterName = js.getString("data.character.name");
        Assert.assertEquals("Robin", characterName);

         //Mutation

        String name = "Aus";
        String type = "Southzone";
        String dimension = "23";

        String responseMutation = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"mutation($name: String!, $type : String!, $dimension: String!){\\n  \\n  \\n    createLocation(location: {name: $name, type: $type, dimension: $dimension}){\\n  \\n    id\\n    \\n  }\\n  \\n}\",\"variables\":{\"name\":\""+name+"\",\"type\":\""+type+"\",\"dimension\":\""+dimension+"\"}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().statusCode(200).extract().response().asString();

        JsonPath jsMutation = new JsonPath(responseMutation);
        int id = jsMutation.get("data.createLocation.id");

        System.out.println(id);
        System.out.println(responseMutation);
    }
}
