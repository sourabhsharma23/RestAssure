package Automation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.http.Cookies;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.cookie.Cookie;
import org.testng.annotations.Test;

public class validateMe{

	String jwt="";
	@Test
	public void Login() {

		RestAssured.baseURI = "https://systest.validateme.online/api/v1/login";
		
		/*	RequestSpecification request = RestAssured.given();
		
		String Creds = "validateme23+06@gmail.com:Qait@123";
		byte[] encodedCreds = Base64.encodeBase64(Creds.getBytes());
		String encodedCredsAsString = new String(encodedCreds);
		
		
		Response resp = request.header("Authorization","Basic "+encodedCredsAsString).get().thenReturn();
		
	System.out.println(resp);
*/
		
		String username = "validateme23+04@gmail.com";
        String password = "Qait@123";

         Response resp = given().log().all().auth().preemptive().basic(username, password)
                .when()
                .get()
                .then()
                .statusCode(200).extract().response();
       
         System.out.println(resp.getBody().jsonPath().prettify());
		Map<String, String> cookies = resp.getCookies();
		
		for(Map.Entry<String, String> entry: cookies.entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue());
			
		
		}
		
		
		
		
	}
}
	


