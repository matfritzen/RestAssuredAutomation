package RestAssured;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


public class oAuth {


    public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub
//


//                                 GOOGLE IS NOT ALLOWING TO SIGN IN THROUGH CHROME DRIVER

//        System.setProperty("webdriver.chrome.driver", "src/test/java/RestAssured/files/driver/chromedriver");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth/oauthchooseaccount?access_type=offline&client_id=587594460880-u53ikl5ast2sup28098ofsm9iku8vvm6.apps.googleusercontent.com&code_challenge=b-47RI1SKw-8hRpQs64697tcHCxjcXJPvUgbxikbVF0&code_challenge_method=S256&include_granted_scopes=true&prompt=select_account%20consent&redirect_uri=https%3A%2F%2Fsso.teachable.com%2Fidentity%2Fcallbacks%2Fgoogle%2Fcallback&response_type=code&scope=email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email%20https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile%20openid%20profile&state=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJpYXQiOjE2NzU0NDc3NTYsImV4cCI6MTY3NTQ0OTU1NiwianRpIjoiNTUxNDJmMDMtYTBiMy00ZmFmLWI1MGUtZmU3MTQxMmY2ODdkIiwiaXNzIjoic2tfeno4dHc2ZGciLCJzdWIiOiI4XzQ1VGE1Rzd4N3FWb1JfWENxeUxQY1UwTE9EVzZVNERKUEc0eFlHT3oyWllGTDRUUVZyRkRvbXlzUTRXbXI1TzZJSGdhQmR6Rko5dTMzLVM3WTlJZyJ9.ulikxbJKMalmqeGnuJuofsq0COxofDaidHb08aILZgw&service=lso&o2v=2&flowName=GeneralOAuthFlow");
//        driver.findElement(By.id("identifierId")).sendKeys("matheus.fritzen1@gmail.com");
//        driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
//        driver.findElement(By.name("password")).sendKeys("(MFritzen123)");
//        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
//        Thread.sleep(4000);



//        String url = driver.getCurrentUrl();

        String[] courseTitles = {"Selenium Webdriver Java","Cypress", "Protractor"};

        String url ="https://sso.teachable.com/identity/callbacks/google/callback?state=eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJnb29nbGUiLCJpYXQiOjE2NzU0NDkzMDcsImV4cCI6MTY3NTQ1MTEwNywianRpIjoiYTI2NmJkODgtNzkxMy00MGRmLWJlMjEtY2M0Y2EyNDk4YjcyIiwiaXNzIjoic2tfeno4dHc2ZGciLCJzdWIiOiJhMTVIZVZxV0VOSVotWklXMXVlWHJNRnNUN2NvbXh2Vm1SblFGNHY5anVNclZDRjhPNElJbFhxcnhFRGQ0dmJqVmdvRS15VE1PV2pJbEJoLTlwLVNMZyJ9.eOhijU5yV5q-IV5Yoaaa7JhD7wkDjxumfE-f7LU9iEs&code=4%2F0AWtgzh6KsB_8CfhCTUYjd0g30ebK6VVFkxZRsFhOtY_LrHBhOUq54OZw5AU_xsxIusYr5Q&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&authuser=0&prompt=consent";



        String partialcode=url.split("code=")[1];

        String code=partialcode.split("&scope")[0];

        System.out.println(code);

        String response =

                given()

                        .urlEncodingEnabled(false)

                        .queryParams("code",code)



                        .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

                        .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

                        .queryParams("grant_type", "authorization_code")

                        // .queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")



                        .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")

                        .when().log().all()

                        .post("https://www.googleapis.com/oauth2/v4/token").asString();

// System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);

        String accessToken = jsonPath.getString("access_token");

        System.out.println(accessToken);

//        String r2=    given().contentType("application/json").
//
//                queryParams("access_token", accessToken).expect().defaultParser(Parser.JSON)
//
//                .when()
//
//                .get("https://rahulshettyacademy.com/getCourse.php")
//
//                .asString();
//
//        System.out.println(r2);

        GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);


        List<Api> apiCourses = gc.getCourses().getApi();
        for (int i = 0; i<apiCourses.size(); i++){

            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){

                System.out.println(apiCourses.get(i).getPrices());
            }
        }

        ArrayList<String> a = new ArrayList<String>();

        List<WebAutomation> w = gc.getCourses().getWebAutomation();

        for (int j = 0; j < w.size(); j++)
        {
            a.add(w.get(j).getCourseTitle());
        }

        List<String> expectedResult = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedResult));


    }



}