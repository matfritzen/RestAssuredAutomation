package CucumberAutomation.stepDefinitions;

import CucumberAutomation.resources.APIResources;
import CucumberAutomation.resources.TestDataBuild;
import CucumberAutomation.resources.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@RunWith(Cucumber.class)
public class StepDefinition extends Utils {

    RequestSpecification req = requestSpecification();
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;

    // if you not put static in the string, when the second scenario runs, the variable will be set as null
    static String place_id;

    TestDataBuild testDataBuild = new TestDataBuild();

    public StepDefinition() throws IOException {
    }

    @Given("Add Place Payload with {string} {string} {string}")
    public void addPlacePayloadWith(String name, String language, String address) throws IOException {
        res=given().spec(req).body(testDataBuild.addPlace_Payload(name, language, address));
    }

    @Given("Delete Place Payload")
    public void deletePlacePayload() {
        res=given().spec(req).body(testDataBuild.deletePlacePayload(place_id));
    }

    @When("user calls {string} with {string} http request")
    public void userCallsWithHttpRequest(String resource, String httpmethod) {

         APIResources resourceAPI = APIResources.valueOf(resource);
         System.out.println(resourceAPI.getResource());

         resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

         if (httpmethod.equalsIgnoreCase("POST")){

             response =res.when().post(resourceAPI.getResource());
         }

         else if (httpmethod.equalsIgnoreCase("GET")){

             response =res.when().get(resourceAPI.getResource());
         }

    }

    @Then("^the API call got success with status code 200$")
    public void the_api_call_got_success_with_status_code_200() throws Throwable {
        Assert.assertEquals(200, response.getStatusCode());
    }

    @And("{string} in response body is {string}")
    public void inResponseBodyIs(String key, String value) {

        Assert.assertEquals(getJsonPath(response,key), value);
    }

    @And("verify place_id created maps to {string} using {string}")
    public void verifyPlace_idCreatedMapsToUsing(String expectedName, String resource) throws IOException {


        place_id = getJsonPath(response, "place_id");
        res = given().spec(req).queryParam("place_id", place_id);
        userCallsWithHttpRequest(resource,"GET");
        String actualName = getJsonPath(response, "name");
        Assert.assertEquals(actualName, expectedName);
    }

}
