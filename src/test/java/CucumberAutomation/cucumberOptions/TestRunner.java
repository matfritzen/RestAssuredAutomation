package CucumberAutomation.cucumberOptions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/CucumberAutomation/features",
        glue = {"CucumberAutomation.stepDefinitions"},
        plugin = {"html:target/cucumber-html-reports/overview-features.html","json:target/jsonReports/cucumber-report.json"},
//        tags= "@AddPlace")
        publish = true)
public class TestRunner {

}


