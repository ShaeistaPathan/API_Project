package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadOneProduct {
	
//	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)
//	
//	02.ReadOneProduct
//	HTTP Method: GET
//	EndpointUrl: https://techfios.com/api-prod/api/product/read_one.php
//	Authorization:
//	Basic Auth/ Bearer Token
//	Header:
//	"Content-Type" : "application/json"
//	QueryParam:"id":"value"
//	Status Code: 200



	@Test
	public void readOneProduct() {
		
		Response response =
		
		given()
//		       log all here prints all the things in given
//		       .log().all()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json")
		       .header("Authorization","Bearer hagdfjookjdw728jb")
		       .queryParam("id", "4661")
		       .relaxedHTTPSValidation().
		when()
		      .get("/read_one.php").
		then()
//		       log all here prints all things stored in response 
//		      .log().all()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader,"application/json");
		
		long actualResponseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
//		System.out.println("Actual Respose Time is : "+ actualResponseTime);
//		Actual Response Time is coming under 1000
		if(actualResponseTime <=2000) {
			System.out.println("Actual Response Time is within range.");
			} else {
			    System.out.println("Actual Response Time is out of range!!");
		}
		
		String actualResponseBody = response.getBody().asString();
//		System.out.println("Actual Response Body : " + actualResponseBody);
//		response.prettyPrint();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String productId = jp.get("id");
		System.out.println("Product Id: "+ productId );
		Assert.assertEquals(productId , "4661");
		
		String productName = jp.get("name");
		System.out.println("Product Name: "+ productName );
		Assert.assertEquals(productName , "Amazing Pillow 3.0 by Shaeista");
		
		String productDescription = jp.get("description");
		System.out.println("Product Description: "+ productDescription );
		Assert.assertEquals( productDescription, "The updated pillow for amazing programmers.");
		

		String productPrice = jp.get("price");
		System.out.println("Product Price: "+ productPrice );
		Assert.assertEquals( productPrice, "299");
		
		String categoryId = jp.get("category_id");
		System.out.println("Category Id: "+ categoryId);
		Assert.assertEquals( categoryId, "2");
		
		String categoryName = jp.get("category_name");
		System.out.println("Category Name: "+ categoryName);
		Assert.assertEquals( categoryName, "Electronics");
		
		
		
		
		
		
		
		
	}

}
