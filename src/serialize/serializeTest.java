package serialize;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = files.Payload.getbaseURL();
		
		AddPlace addplace= new AddPlace();
		
		addplace.setAccuracy(50);
		addplace.setAddress("address12");
		addplace.setLanguage("en");
		addplace.setPhone_number("12345");
		addplace.setWebsite("www.abc.com");
		addplace.setName("onur house");
		
		List<String> myList= new ArrayList<String>();
		myList.add("shoe");
		myList.add("shop");
	
		addplace.setTypes(myList);
		
		Location location = new Location();
		location.setLat(3.456);
		location.setLng(5.123);
		
		addplace.setLocation(location);
		
		RequestSpecification req= (RequestSpecification) new RequestSpecBuilder().setBaseUri(files.Payload.getbaseURL())
		.addQueryParam(files.Payload.get_paramKey(), files.Payload.get_paramValue()).setContentType(ContentType.JSON).build();
		
		
		ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(files.Payload.get_successStatusCode())
				.expectContentType(ContentType.JSON).build();
		
		RequestSpecification res= given().spec(req).body(addplace);
		
		
		Response response=res.when().post(files.Payload.get_addResources())
		.then().spec(resspec).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
		
		
		
	}

}
