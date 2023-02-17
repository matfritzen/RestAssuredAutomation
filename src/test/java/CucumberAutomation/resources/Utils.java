package CucumberAutomation.resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {

    public static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {

        PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));

        String baseURL = getGlobalValue("baseURL");

        if(req == null) {
            req = new RequestSpecBuilder().setBaseUri(baseURL)
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }

         return req;
    }

    public static String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/CucumberAutomation/resources/global.properties");

        prop.load(fis);
        return prop.get(key).toString();

    }

    public String getJsonPath(Response response, String key){

        String res = response.asString();
        JsonPath js = new JsonPath(res);
        return js.get(key).toString();
    }

}
