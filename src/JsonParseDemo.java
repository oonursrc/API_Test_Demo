import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class JsonParseDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath jscoursePrice=  ReusableMethods.rawToJson(Payload.coursePrice());
		
		// number of cources
		int count=jscoursePrice.getInt("courses.size()");
		
		System.out.println("\nTotal Cource Number is : "+count);
		
		// print purchase amount
		int totalAmount= jscoursePrice.getInt("dashboard.purchaseAmount");
		
		System.out.println("\nTotal Amount is : "+totalAmount);
		
		
		// print title of first course
		String titleFirstCourse= jscoursePrice.get("courses[0].title"); // get default string
		
		System.out.println("\nTitle first course is : "+titleFirstCourse);
		
		
		// print all titles and prices
		
		for (int i=0;i<count;i++) {
			String courseTitles=jscoursePrice.get("courses["+i+"].title");
			int price= jscoursePrice.get("courses["+i+"].price");
			
			System.out.println("\nTitle is: "+courseTitles+ ", Price is: "+price);
		}
		
		
		
		
		// print RPA copies sold
		
		for (int i=0;i<count;i++) {
			String courseTitles=jscoursePrice.get("courses["+i+"].title");
			if(courseTitles.equalsIgnoreCase("RPA")) {
				int copies= jscoursePrice.get("courses["+i+"].copies");
				System.out.println("\nSold copies is: "+ copies);
				break;
			}
			
			
		}
		
		
		// validate total price
		int sum=0;
		for (int i=0;i<count;i++) {
			int price= jscoursePrice.get("courses["+i+"].price");
			int copies= jscoursePrice.get("courses["+i+"].copies");
			
			sum=sum+(price*copies);	
		}
		System.out.println("\nSum is: "+ sum);
		
		Assert.assertEquals(totalAmount, sum);

	}

}
