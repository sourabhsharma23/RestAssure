package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Resources.APIresource;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.GetCourse;
import pojo.location_new;

public class stepDefination extends Utils{

	
	RequestSpecification reqspec;
	ResponseSpecification respspec;
	Response response;
	TestDataBuild datatest;
	
	@Given("Add place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		datatest = new TestDataBuild();
    	
    	reqspec=given().spec(RequestSpecMethod())
    			.body(datatest.AddPLacePayload(name, language, address));
    
    	
    
	}
	

	@When("user calls {string} using {string} http request")
	public void user_calls_using_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
//Constructor of enum will be called with valueOf resource which you pass
		//APIresource is an enum not a simple class
		
		APIresource resourceOfApi = APIresource.valueOf(resource);
		System.out.println(resourceOfApi.getResource());
		
    	if(method.equalsIgnoreCase("post"))
		response = reqspec.when().post(resourceOfApi.getResource());
    	else if(method.equalsIgnoreCase("get"))
    	{
    		response = reqspec.when().get(resourceOfApi.getResource());
    	}
    
    	respspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    	
    	
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
		
	
		assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
	    // Write code here that turns the phrase above into concrete actions
	
		assertEquals(getJsonPath(response, keyValue),ExpectedValue);

	}


	@Then("Verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String ExpectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions

	String ExpectedPlace_id = getJsonPath(response,"place_id");
		reqspec=given().spec(RequestSpecMethod()).queryParam("place_id",ExpectedPlace_id);
		user_calls_using_http_request(resource,"GET");
		String ActualName =	getJsonPath(response,"name");
		assertEquals(ActualName,ExpectedName);
	}





}
