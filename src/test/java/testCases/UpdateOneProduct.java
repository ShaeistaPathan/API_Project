package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateOneProduct {
	
//	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)/extract response
//	
//	04.UpdateOneProduct
//	HTTP Method: PUT
//	EndpointUrl: https://techfios.com/api-prod/api/product/update.php
//	Authorization:
//	Basic Auth/ Bearer Token
//	Header:
//	"Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 200
//	Payload/Body: 
//	{
//	    "id": "4562",
//	    "name": "Amazing Pillow 3.0 By Shaeista",
//	    "price": "299",
//	    "description": "The updated pillow for amazing programmers.",
//	    "category_id": 2
//    "category_name":"Electronics"
//     }
	

	HashMap<String,String> updatePayload;
	
	public Map<String,String> updatePayloadMap(){
		
		updatePayload = new HashMap<String,String>();
		updatePayload.put("id","4817");
		updatePayload.put("name","Amazing Pillow 5.0 By Shaeista");
		updatePayload.put("price","999");
		updatePayload.put("description","The updated pillow for amazing programmers.");
		updatePayload.put("category_id","2");
		updatePayload.put("category_name","Electronics");
		
		return updatePayload;
	}


	@Test(priority=1)
	public void updateOneProduct() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json; charset=UTF-8")
		       .body(updatePayloadMap())
		       .relaxedHTTPSValidation().
		when()
		      .put("/update.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader,"application/json; charset=UTF-8");
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String productMessage = jp.get("message");
		System.out.println("Product Message: "+ productMessage );
		Assert.assertEquals(productMessage , "Product was updated.");
		
		}

	
	@Test(priority=2)
	public void validateUpdatedProductDetails() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json")
		       .queryParam("id", updatePayloadMap().get("id"))
		       .relaxedHTTPSValidation().
		when()
		      .get("/read_one.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String updatedProductName = updatePayloadMap().get("name");
		String updatedProductDescription = updatePayloadMap().get("description");
		String updatedProductPrice = updatePayloadMap().get("price");
		
		String actualProductName = jp.get("name");
		System.out.println("Actual Product Name: "+ actualProductName  );
		System.out.println("Updated Product Name: "+ updatedProductName  );
		Assert.assertEquals(actualProductName  ,updatedProductName);
		
		String actualproductDescription = jp.get("description");
		System.out.println("Product Description: "+ actualproductDescription );
		System.out.println("Updted Product Description: "+ updatedProductDescription );
		Assert.assertEquals( actualproductDescription, updatedProductDescription);
		

		String actualproductPrice = jp.get("price");
		System.out.println("Actual Product Price: "+  actualproductPrice );
		System.out.println("Updted Product Price: "+ updatedProductPrice );
		Assert.assertEquals(  actualproductPrice,  updatedProductPrice);
		
		
		}



}
