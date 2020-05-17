package oauthDemo;

import files.ReusableMethods;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;


public class oAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String[] courseTitles= { "Selenium Webdriver Java","Cypress","Protractor"};
		String chromeDriverLocation = System.getProperty("user.dir") + "\\src\\chromedriver.exe";
		
		 System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
		 
			WebDriver driver= new ChromeDriver();
			
			
			driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys("fdfd");
			driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
			Thread.sleep(3000);
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys("fxfe");
			driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
			Thread.sleep(4000);
			String url=driver.getCurrentUrl();
			String partialcode=url.split("code=")[1];
			String code=partialcode.split("&scope")[0];
			System.out.println(code);

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authoriztion_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = ReusableMethods.rawToJson(accessTokenResponse);
		String accessToken = js.getString("access_token");

		GetCourse gcObject = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gcObject.getLinkedIn());
		System.out.println(gcObject.getInstructor());
		System.out.println(gcObject.getCourses().getApi().get(1).getCourseTitle());
		
		
		List<Api> apiCourses= gcObject.getCourses().getApi();
		
		for (int i = 0; i < apiCourses.size(); i++) {
			
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
			
		}
		
		ArrayList<String> a= new ArrayList<String>();
		List<WebAutomation> w = gcObject.getCourses().getWebAutomation();
		for (int i = 0; i < w.size(); i++) {
			System.out.println(w.get(i).getCourseTitle());
			a.add(w.get(i).getCourseTitle());
		}
		
		ArrayList<String> expectedtList= (ArrayList<String>) Arrays.asList(courseTitles);

		Assert.assertTrue(a.equals(expectedtList));

	}

}
