package petStoreTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DataDrivenApproach {

	@BeforeTest
	public void setup() {
	RestAssured.baseURI = "https://petstore.swagger.io";
	
	
	}
	
	
	@Test(dataProvider="testing", dataProviderClass = MyDataProvider.class)
	public void getAPIpetStatus(String status) {
		
		Response response = given().header("accept","application/json").header("Content-Type","application/json")
		.when().get("/v2/pet/findByStatus?status="+status);
		Assert.assertEquals(response.statusCode(),200);
		
		
	}
	

	
	
}
