package petStoreTest;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pets.pojo.Category;
import com.pets.pojo.PetPojo;
import com.pets.pojo.Tags;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestingPetStoreAPIs {

	long petId;
	@BeforeTest
	public void beforeEveryTestAPI() {
		RestAssured.baseURI="https://petstore.swagger.io";
		
	}
	
	@Test
	public void petStorePost() {
		
//---------------------------For Serialization		
		PetPojo pet = new PetPojo();
		pet.setId(2001);
		pet.setName("human");
		pet.setStatus("available");
	
		Category category = new Category();
		category.setId(5708255);
		category.setName("human being");
		pet.setCategory(category);
		
		Tags tag = new Tags();
		tag.setId(0);
		tag.setName("whatever");
		
		List<Tags> tags = new ArrayList();
		tags.add(tag);
		pet.setTags(tags);
		
		List<String> photourl = new ArrayList();
		photourl.add("Sourabh.com");
		photourl.add("www.google.com");	
		
		pet.setPhotoUrls(photourl);
		
		/*
		 * String json = "{\n" + "  \"id\": 9223372000001092000,\n" +
		 * "  \"category\": {\n" + "    \"id\": 5708255,\n" +
		 * "    \"name\": \"string\"\n" + "  },\n" + "  \"name\": \"human\",\n" +
		 * "  \"photoUrls\": [\n" + "    \"string\"\n" + "  ],\n" + "  \"tags\": [\n" +
		 * "    {\n" + "      \"id\": 0,\n" + "      \"name\": \"string\"\n" + "    }\n"
		 * + "  ],\n" + "  \"status\": \"available\"\n" + "}";
		 */
		
		Response response = given().header("Content-Type", "application/json").header("Accept", "application/json")
				.body(pet)
		.when().post("/v2/pet");
		System.out.println(response.asPrettyString());
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.contentType(), "application/json");
		
		response.then().body("category.id", equalTo(5708255))
		.body("name", equalTo("human"))
		.body("tags[0].name", equalTo("whatever"));
		
		//petId = response.path("id");
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		 petId = js.getLong("id");
		 
//---------------------------For deserialization----------
		 
		 PetPojo serResponse = response.body().as(PetPojo.class);
		 System.out.println(serResponse.getName());
		 System.out.println(serResponse.getStatus());
		 System.out.println(serResponse.getPhotoUrls());
		 System.out.println(serResponse.getTags().get(0).getName());
		 
		 Assert.assertEquals(serResponse.getStatus(), "available");
		 Assert.assertEquals(serResponse.getTags().get(0).getName(), "whatever");
		 
		 System.out.println();
	}
	
	@Test(dependsOnMethods="petStorePost")
	public void petGetAPI() {
		
		Response response = given().header("Content-Type", "application/json")
		.when().get("v2/pet/"+petId);
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test(dependsOnMethods="petStorePost")
	public void petPostUploadFile() {
		
		Response response = given().header("accept", "application/json").header("Content-Type", "multipart/form-data")
		.formParam("additionalMetadata", "Mine Screenshot")
		.multiPart("file",new File("/Users/labuser/Downloads/Screenshot (416).png"),"image/png")
		.when().post("/v2/pet/"+petId+"/uploadImage");
		
		System.out.println(response.statusCode());
		System.out.println(response.asPrettyString());
		
	}
	
	
	
}
