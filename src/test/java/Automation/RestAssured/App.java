package Automation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;

public class App 
{
    public static void main( String[] args )
    {
    	
    	RestAssured.baseURI = "https://rahulshettyacademy.com";
    	String resp = given().log().all().queryParam("key", "qaclick123").header("content-Type","application/json")
    	.body(payload.AddPlace()).when().post("maps/api/place/add/json")
    	.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
    	.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
    	
    	System.out.println(resp);
    	JsonPath jp = new JsonPath(resp);
    	String placeid = jp.getString("place_id");
    	
    	String newAddress = "Summer Walk, Africa";
    	
    	//Update Place and address
    	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeid+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
    	
    	//Get place and address
    	String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
    			.queryParam("place_id",placeid)
    			.when().get("maps/api/place/get/json")
    			.then().assertThat().log().all().statusCode(200).extract().response().asString();
    	
    	JsonPath jp1 = new JsonPath(getPlaceResponse);
    	String actualAddress =jp1.getString("address");
    	Assert.assertEquals(actualAddress, newAddress);
    	
    }
}
