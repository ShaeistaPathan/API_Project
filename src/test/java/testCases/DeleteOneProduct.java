package testCases;

import org.testng.Assert;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DeleteOneProduct {
	
//	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)/extract response
//	
//	05. DeleteOneProduct
//	HTTP Method: DELETE
//	EndpointUrl: https://techfios.com/api-prod/api/product/delete.php
//	Authorization:Basic Auth/ Bearer Token
//	Header:"Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 200
//	Payload/Body: 
//	{
//	    "id": "4787"   
//	}
	  
	

	HashMap<String,String> deletePayload;
	SoftAssert softAssert;
	
	public Map<String,String> deletePayloadMap(){
		
		deletePayload = new HashMap<String,String>();
		deletePayload.put("id","4787");
	
		return deletePayload;
	}


	@Test(priority=1)
	public void deleteOneProduct() {
		
		softAssert = new SoftAssert();
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json; charset=UTF-8")
		       .body(deletePayloadMap())
		       .relaxedHTTPSValidation().
		when()
		      .delete("/delete.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		softAssert.assertEquals(actualStatusCode, 200, "Status code doesnot match while deleting!!");
		
		String actualHeader = response.getHeader("Content-Type");
		softAssert.assertEquals(actualHeader,"application/json; charset=UTF-8","Actual header doesnot match with expected header while deleting!!");
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String productMessage = jp.get("message");
		System.out.println("Product Message: "+ productMessage );
		softAssert.assertEquals(productMessage , "Product was deleted.","Product message doenot match while deleting!!");
		
		softAssert.assertAll();
		
		}

	
	@Test(priority=2)
	public void validateDeletedProductDetails() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json")
		       .queryParam("id", deletePayloadMap().get("id"))
		       .relaxedHTTPSValidation().
		when()
		      .get("/read_one.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		softAssert.assertEquals(actualStatusCode, 404);
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String actualProductMessage = jp.get("message");
		System.out.println("Actual Product Message: "+ actualProductMessage  );
		softAssert.assertEquals(actualProductMessage,"Product does not exist.");
		
		softAssert.assertAll();
		
		}



}
