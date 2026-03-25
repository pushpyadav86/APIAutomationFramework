package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.pet.PetPayload;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PetEndpoints {

	  public static Response createPet(PetPayload payload) {
	        return given()
	                .accept(ContentType.JSON)
	                .contentType(ContentType.JSON)
	                .body(payload)
	                .when()
	                .post(Routes.CREATE_PET_URL);
	    }

	    public static Response updatePet(PetPayload payload) {
	        return given()
	                .accept(ContentType.JSON)
	                .contentType(ContentType.JSON)
	                .body(payload)
	                .when()
	                .put(Routes.UPDATE_PET_URL);
	    }

	    public static Response getPet(int petId) {
	        return given()
	                .pathParam("petId", petId)
	                .when()
	                .get(Routes.GET_PET_URL);
	    }

	    public static Response deletePet(int petId) {
	        return given()
	                .pathParam("petId", petId)
	                .when()
	                .delete(Routes.DELETE_PET_URL);
	    }
	}