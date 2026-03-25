package api.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import api.endpoints.PetEndpoints;
import api.payload.pet.PetPayload;
import api.utilities.JsonUtils;
import io.restassured.response.Response;

public class PetTestJson {
	
	Logger logger;

    String filePath = "src/test/resources/testdata/petTestData.json";
    
    @BeforeClass
    public void beforeClass() {
    	logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testCreatePet() {

        PetPayload createPetData = JsonUtils.getTestDataByPath(
                filePath,
                "pet.createPet",
                PetPayload.class
        );

        Response response = PetEndpoints.createPet(createPetData);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains("application/json"));

        Response getResponse = PetEndpoints.getPet(createPetData.getId());
        getResponse.then().log().all();

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getInt("id"), createPetData.getId());
        Assert.assertEquals(getResponse.jsonPath().getString("name"), createPetData.getName());
        Assert.assertEquals(getResponse.jsonPath().getString("status"), createPetData.getStatus());

        Assert.assertEquals(getResponse.jsonPath().getInt("category.id"), createPetData.getCategory().getId());
        Assert.assertEquals(getResponse.jsonPath().getString("category.name"), createPetData.getCategory().getName());

        Assert.assertEquals(getResponse.jsonPath().getString("photoUrls[0]"), createPetData.getPhotoUrls().get(0));

        Assert.assertEquals(getResponse.jsonPath().getInt("tags[0].id"), createPetData.getTags().get(0).getId());
        Assert.assertEquals(getResponse.jsonPath().getString("tags[0].name"), createPetData.getTags().get(0).getName());
        
        //log
        logger.info("Created Pet");
    }

    @Test(priority = 2)
    public void testUpdatePet() {

        PetPayload updatePetData = JsonUtils.getTestDataByPath(
                filePath,
                "pet.updatePet",
                PetPayload.class
        );

        Response response = PetEndpoints.updatePet(updatePetData);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains("application/json"));

        Response getResponse = PetEndpoints.getPet(updatePetData.getId());
        getResponse.then().log().all();

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getInt("id"), updatePetData.getId());
        Assert.assertEquals(getResponse.jsonPath().getString("name"), updatePetData.getName());
        Assert.assertEquals(getResponse.jsonPath().getString("status"), updatePetData.getStatus());

        Assert.assertEquals(getResponse.jsonPath().getInt("category.id"), updatePetData.getCategory().getId());
        Assert.assertEquals(getResponse.jsonPath().getString("category.name"), updatePetData.getCategory().getName());

        Assert.assertEquals(getResponse.jsonPath().getString("photoUrls[0]"), updatePetData.getPhotoUrls().get(0));

        Assert.assertEquals(getResponse.jsonPath().getInt("tags[0].id"), updatePetData.getTags().get(0).getId());
        Assert.assertEquals(getResponse.jsonPath().getString("tags[0].name"), updatePetData.getTags().get(0).getName());
        
      //Logger
      		logger.info("Updated Pet info.");
    }

    @Test(priority = 3)
    public void testDeletePet() {

        PetPayload createPetData = JsonUtils.getTestDataByPath(
                filePath,
                "pet.createPet",
                PetPayload.class
        );

        Response response = PetEndpoints.deletePet(createPetData.getId());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        Response getDeletedResponse = PetEndpoints.getPet(createPetData.getId());
        getDeletedResponse.then().log().all();

        Assert.assertEquals(getDeletedResponse.getStatusCode(), 404);
        
      //Logger
      		logger.info("Deleted Pet");
    }
}