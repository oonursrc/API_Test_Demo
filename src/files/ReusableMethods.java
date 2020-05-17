package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String response) {
		JsonPath js1= new JsonPath(response); // takes text and converts to json
		return js1;
	}

}
