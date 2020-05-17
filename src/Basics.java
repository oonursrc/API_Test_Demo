import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;


public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Validate if AddPlace API works

		// given --all input details
		// when -- submit the api
		// then -- validate the response
		// all inside import static io.restassured.RestAssured.*;

		RestAssured.baseURI = files.Payload.getbaseURL();
		String response= given().log().all().queryParam(files.Payload.get_paramKey(), files.Payload.get_paramValue()).header(files.Payload.get_headerType(), files.Payload.get_headerValue()).body(files.Payload.getAddPlaceBody())
		.when().post(files.Payload.get_addResources())
		.then().log().all().assertThat().statusCode(files.Payload.get_successStatusCode()).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		//log().all() for logging input and output
		// equalTo is inside import static org.hamcrest.Matchers.*;
		System.out.println("\nFull response is: \n"+response);
		
		// add place -> update it with new address -> get place to validate
		
		JsonPath jspath= ReusableMethods.rawToJson(response);// takes text and converts to json
		String place_id= jspath.getString("place_id");
		
		System.out.println("\nplace_id is: \n"+place_id);
		
		String newaddress="kartal ist";
		
		
		//update place
		
		given().log().all().queryParam(files.Payload.get_paramKey(), files.Payload.get_paramValue()).header(files.Payload.get_headerType(), files.Payload.get_headerValue())
				.body(files.Payload.getUpdatePlaceBody(place_id,newaddress)).when().put(files.Payload.get_updateResources())
				.then().log().all().assertThat().statusCode(files.Payload.get_successStatusCode()).body("msg", equalTo("Address successfully updated"));

		
		// Get place
		String getPlaceResponse= given().log().all().queryParam(files.Payload.get_paramKey(), files.Payload.get_paramValue())
		.queryParam(files.Payload.get_place_idKey(), place_id)
		.when().get(Payload.get_getResources()).then().log().all().assertThat().statusCode(files.Payload.get_successStatusCode()).extract().response().asString();
		
		
		JsonPath jsgetPlaaceResponse= ReusableMethods.rawToJson(getPlaceResponse); // takes text and converts to json
		String actualAddress= jsgetPlaaceResponse.getString("address");
		
		System.out.println("\nactualAddress is: \n"+actualAddress);
		
		Assert.assertEquals(actualAddress,newaddress);
		
	}

}
