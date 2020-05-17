package LibraryAPI;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = Payload.getbaseURLLibraryAPI();

		String response= given().log().all().header(files.Payload.get_headerType(), files.Payload.get_headerValue()).body(Payload.addBook(isbn, aisle))
		.when().post(Payload.addBookResource()).then().assertThat().statusCode(Payload.get_successStatusCode()).extract().response().asString();
	
		JsonPath js = ReusableMethods.rawToJson(response);
		
		String id = js.get("ID");
		System.out.println("New book ID is: "+id);

	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"sadasd","123asd"},{"454gd","67kj"},{"13ff","8978kas"} };
	}
	
	

}
