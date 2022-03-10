package Automation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.GetCourse;
import pojo.location_new;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class pojoTest {

	public static void main( String[] args )
    {
    	//RestAssured.baseURI = "https://rahulshettyacademy.com";
    	GetCourse getc = new GetCourse();
    	getc.setAccuracy(50);
    	getc.setAddress("Karnal, Sector-32,35");
    	getc.setLanguage("English");
    	getc.setPhone_number("9050909709");
    	getc.setWebsite("sourabhsharma@qainfotech.com");
    	getc.setName("Sourabh");
    	
    	List<String> typlst = new ArrayList<String>();
    	typlst.add("ShoePark");
    	typlst.add("shop");
    	
    	getc.setTypes(typlst);
    	
    	location_new loc = new location_new();
    	loc.setLat(-33.4848);
    	loc.setLng(534.32342);
    	
    	getc.setLocation(loc);
    	
    	RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
    			.setContentType(ContentType.JSON).build();
    	
    	RequestSpecification reqspec=given().spec(req).body(getc);
    	
    	ResponseSpecification respspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    	
    	Response response = reqspec.when().post("maps/api/place/add/json").then().spec(respspec).extract().response();
    	
    	
    	
    	/*Response resp = given().log().all().queryParam("key", "qaclick123")
    	.body(getc)
    	.when().post("maps/api/place/add/json")
    	.then().assertThat().statusCode(200).extract().response();
    	*/
    	
    	String respString=response.asString();
		System.out.println(respString);
    	
    }
	
}
