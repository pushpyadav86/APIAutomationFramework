package api.endpoints;

import api.payload.userPayload;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

public class userEndpoints {

    public static Response createUser(userPayload payload) {
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.PostURL);

        return response;
    }
    
    public static Response getUser(String username) {
        Response response = given()
        		.accept(ContentType.JSON)
                .pathParam("username", username)
                .when()
                .get(Routes.getURL);

        return response;
    }
    
    public static Response updateUser(String username, userPayload payload) {
        Response response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .body(payload)
                .when()
                .put(Routes.putURL);

        return response;
    }
    
    public static Response deleteUser(String username) {
        Response response = given()
                .pathParam("username", username)
                .when()
                .delete(Routes.deleteURL);

        return response;
    }
}
