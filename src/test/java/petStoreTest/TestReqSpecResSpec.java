package petStoreTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pets.pojo.Category;
import com.pets.pojo.PetPojo;
import com.pets.pojo.Tags;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestReqSpecResSpec {

long petId;
RequestSpecification reqSpec;
ResponseSpecification resSpec;

	@BeforeTest
	public void beforeEveryTestAPI() {
		
		RequestSpecBuilder reqSpecbuilder = new RequestSpecBuilder();
		reqSpecbuilder.setBaseUri("https://petstore.swagger.io");
		reqSpecbuilder.setContentType("application/json");
		reqSpecbuilder.setAccept("application/json");
		reqSpec = reqSpecbuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectStatusCode(200);
		resBuilder.expectContentType("application/json");
		resSpec = resBuilder.build();
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
		
		Response response = given().spec(reqSpec)
				          .body(pet).expect().spec(resSpec)
		                  .when().post("/v2/pet");
		
		System.out.println(response.asPrettyString());

		
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
		
		Response response = given().spec(reqSpec).expect().spec(resSpec)
		.when().get("v2/pet/"+petId);
		Assert.assertEquals(response.statusCode(), 200);
		System.out.println(response.asPrettyString());
	}
	
	@Test(dependsOnMethods="petStorePost")
	public void petPostUploadFile() {
		
		Response response = given().header("accept", "application/json").header("Content-Type", "multipart/form-data")
		.formParam("additionalMetadata", "Mine Screenshot")
		.multiPart("file",new File("/Users/labuser/Downloads/Screenshot (416).png"),"image/png")
		.when().post("/v2/pet/"+petId+"/uploadImage");
		
		
		System.out.println(response.asPrettyString());
		
	}
	
	
}
