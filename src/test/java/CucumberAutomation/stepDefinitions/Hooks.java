package CucumberAutomation.stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        StepDefinition steps = new StepDefinition();

        if (StepDefinition.place_id == null) {
            steps.addPlacePayloadWith("matheus", "Portuguese", "Novo Hamburgo");
            steps.userCallsWithHttpRequest("addPlaceAPI", "POST");
            steps.verifyPlace_idCreatedMapsToUsing("matheus", "getPlaceAPI");

        }
    }


}
