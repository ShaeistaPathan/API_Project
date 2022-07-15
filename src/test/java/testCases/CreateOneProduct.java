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

public class CreateOneProduct {
	
//	given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
//	when:  submit api requests(Http method,Endpoint/Resource)
//	then:  validate response(status code, Headers, responseTime, Payload/Body)/extract response
//	
//	03.CreateOneProduct
//	HTTP Method: POST
//	EndpointUrl: https://techfios.com/api-prod/api/product/create.php
//	Authorization:Basic Auth/ Bearer Token
//	Header:"Content-Type" : "application/json; charset=UTF-8"
//	Status Code: 201
//	Payload/Body: 
//	{
//	    "name": "Amazing Pillow 2.0 By MD",
//	    "price": "199",
//	    "description": "The best pillow for amazing programmers.",
//	    "category_id":"2"
//	    "category_name":"Electronics"
//	}  
	

	HashMap<String,String> createPayload;
	String firstProductId;
	
	public Map<String,String> createPayloadMap(){
		
		createPayload = new HashMap<String,String>();
		createPayload.put("name","Amazing Pillow 2.0 By Shaeista");
		createPayload.put("price","199");
		createPayload.put("description","The best pillow for amazing programmers.");
		createPayload.put("category_id","2");
		createPayload.put("category_name","Electronics");
		
		return createPayload;
	}


	@Test(priority=1)
	public void createOneProduct() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json; charset=UTF-8")
		       .header("Authorization","Bearer hagdfjookjdw458dkfg")
		       .body(createPayloadMap())
		       .relaxedHTTPSValidation().
		when()
		      .post("/create.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 201);
		
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader,"application/json; charset=UTF-8");
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String productMessage = jp.get("message");
		System.out.println("Product Message: "+ productMessage );
		Assert.assertEquals(productMessage , "Product was created.");
		
		}

	@Test(priority=2)
	public void getFirstProductId() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json; charset=UTF-8")
		       .auth().preemptive().basic("demo@techfios.com","abc123")
		       .relaxedHTTPSValidation().
		when()
		      .get("/read.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualHeader = response.getHeader("Content-Type");
		Assert.assertEquals(actualHeader,"application/json; charset=UTF-8");
		
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		firstProductId = jp.get("records[0].id");
		System.out.println("The First Product Id: " + firstProductId );
		
		}
	
	@Test(priority=3)
	public void validateProductDetails() {
		
		Response response =
		
		given()
		       .baseUri("https://techfios.com/api-prod/api/product")
		       .header("Content-Type", "application/json")
		       .queryParam("id", firstProductId)
		       .relaxedHTTPSValidation().
		when()
		      .get("/read_one.php").
		then()
		      .extract().response();
		
		int actualStatusCode = response.statusCode();
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualResponseBody = response.getBody().asString();
		
		JsonPath jp = new JsonPath(actualResponseBody);
		
		String expectedProductName = createPayloadMap().get("name");
		String expectedProductDescription = createPayloadMap().get("description");
		String expectedProductPrice = createPayloadMap().get("price");
		String expectedProductCategoryId = createPayloadMap().get("category_id");
		String expectedProductCategoryName = createPayloadMap().get("category_name");
		
		String ActualProductName = jp.get("name");
		System.out.println("Actual Product Name: "+ ActualProductName  );
		System.out.println("Expected Product Name: "+ expectedProductName  );
		Assert.assertEquals(ActualProductName  , expectedProductName);
		
		String ActualproductDescription = jp.get("description");
		System.out.println("Product Description: "+ ActualproductDescription );
		System.out.println("Expected Product Description: "+ expectedProductDescription );
		Assert.assertEquals( ActualproductDescription, expectedProductDescription);
		

		String ActualproductPrice = jp.get("price");
		System.out.println("Actual Product Price: "+  ActualproductPrice );
		System.out.println("Expected Product Price: "+ expectedProductPrice );
		Assert.assertEquals(  ActualproductPrice, expectedProductPrice);
		
		String ActualcategoryId = jp.get("category_id");
		System.out.println("Actual Category Id: "+ ActualcategoryId);
		System.out.println("Expected Category Id: "+expectedProductCategoryId);
		Assert.assertEquals(ActualcategoryId,expectedProductCategoryId);
		
		String ActualcategoryName = jp.get("category_name");
		System.out.println("Actual Category Name: "+ ActualcategoryName);
		System.out.println("Actual Category Name: "+ expectedProductCategoryName);
		Assert.assertEquals( ActualcategoryName, expectedProductCategoryName);
		
		
		
		
		
		
		
		
	}



}
