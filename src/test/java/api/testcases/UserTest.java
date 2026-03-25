package api.testcases;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndpoints;
import api.payload.userPayload;
import io.restassured.response.Response;

public class UserTest {

	Faker faker;
	userPayload payload;
	public static Logger logger;

	@BeforeClass
	public void generateTestData() {
		faker = new Faker();
		payload = new userPayload();
		logger = LogManager.getLogger("ResrAssuredAutomationFramework");

		payload.setId(faker.idNumber().hashCode());
		payload.setUsername(faker.name().username());
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().emailAddress());
		payload.setPassword(faker.internet().password());
		payload.setPhone(faker.phoneNumber().phoneNumber());
	}

	@Test(priority=1)
	public void tsetCreateUser() {
		Response response = userEndpoints.createUser(this.payload);

		System.out.println("Read Create user data.");
		//log response
		response.then().log().all();

		//validation
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertTrue(response.getTime() < 3000, "Response time is too high");

		String type = response.jsonPath().getString("type");
		String message = response.jsonPath().getString("message");

		Assert.assertEquals(type, "unknown");
		Assert.assertNotNull(message);
		Assert.assertFalse(message.isEmpty(), "Message should not be empty");
		
		//Logger
		logger.info("Created user");
	}

	@Test(priority=2)
	public void testGetUser() {
		Response response = userEndpoints.getUser(this.payload.getUsername());

		System.out.println("Read get user data.");
		//log response
		response.then().log().all();

		//validation
		Assert.assertEquals(response.getStatusCode(), 200);
	    Assert.assertEquals(response.getContentType(), "application/json");
	    Assert.assertTrue(response.getTime() < 3000, "Response time is too high");
	    
//	    Assert.assertEquals(response.jsonPath().getString("username"), this.payload.getUsername());
	    Assert.assertEquals(response.jsonPath().getString("firstName"), this.payload.getFirstName());
	    Assert.assertEquals(response.jsonPath().getString("lastName"), this.payload.getLastName());
	    Assert.assertEquals(response.jsonPath().getString("email"), payload.getEmail());
	    Assert.assertEquals(response.jsonPath().getString("password"), payload.getPassword());
	    Assert.assertEquals(response.jsonPath().getString("phone"), payload.getPhone());

	    int actualId = response.jsonPath().getInt("id");
	    Assert.assertEquals(actualId, payload.getId());
	    
	    //log
	    logger.info("Got user info.");
	}

	@Test(priority=3)
	public void testUpdateUser() {
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().emailAddress());

		Response response = userEndpoints.updateUser(this.payload.getUsername(), this.payload );

		System.out.println("Read update user data.");
		//log response
		response.then().log().all();

		//validation
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getContentType(), "application/json");
		Assert.assertTrue(response.getTime() < 3000, "Response time is too high");

		Assert.assertEquals(response.jsonPath().getString("type"), "unknown");
		Assert.assertNotNull(response.jsonPath().getString("message"));

		// verify updated data using GET
		Response getUpdatedResponse = userEndpoints.getUser(this.payload.getUsername());
		getUpdatedResponse.then().log().all();

		Assert.assertEquals(getUpdatedResponse.getStatusCode(), 200);
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("firstName"), payload.getFirstName());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("lastName"), payload.getLastName());
		Assert.assertEquals(getUpdatedResponse.jsonPath().getString("email"), payload.getEmail());

		//log
		logger.info("Updated user");
	}

	@Test(priority=4)
	public void testDeleteUser() {
		Response response = userEndpoints.deleteUser(this.payload.getUsername());

		System.out.println("Read delete user data.");
		//log response
		response.then().log().all();

		//validation
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//log
		logger.info("Deleted user");
	}
}
