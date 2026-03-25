package api.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;


import api.endpoints.userEndpoints;
import api.payload.userPayload;
import api.utilities.JsonUtils;
import io.restassured.response.Response;

public class UserTestJson {
    String filePath = "src/test/resources/testdata/userTestData.json";

    @Test(priority = 1)
    public void testCreateUser() {

        userPayload createUserData = JsonUtils.getTestDataByPath(
                filePath,
                "user.createUser",
                userPayload.class
        );

        Response response = userEndpoints.createUser(createUserData);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains("application/json"));

        Response getResponse = userEndpoints.getUser(createUserData.getUsername());
        getResponse.then().log().all();

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getString("username"), createUserData.getUsername());
        Assert.assertEquals(getResponse.jsonPath().getString("firstName"), createUserData.getFirstName());
        Assert.assertEquals(getResponse.jsonPath().getString("lastName"), createUserData.getLastName());
        Assert.assertEquals(getResponse.jsonPath().getString("email"), createUserData.getEmail());
    }

    @Test(priority = 2)
    public void testUpdateUser() {

        userPayload updateUserData = JsonUtils.getTestDataByPath(
                filePath,
                "user.updateUser",
                userPayload.class
        );

        Response response = userEndpoints.updateUser(updateUserData.getUsername(), updateUserData);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains("application/json"));

        Response getResponse = userEndpoints.getUser(updateUserData.getUsername());
        getResponse.then().log().all();

        Assert.assertEquals(getResponse.getStatusCode(), 200);
        Assert.assertEquals(getResponse.jsonPath().getString("firstName"), updateUserData.getFirstName());
        Assert.assertEquals(getResponse.jsonPath().getString("lastName"), updateUserData.getLastName());
        Assert.assertEquals(getResponse.jsonPath().getString("email"), updateUserData.getEmail());
    }

    

    @Test(priority = 3)
    public void testDeleteUser() {

        userPayload createUserData = JsonUtils.getTestDataByPath(
                filePath,
                "user.createUser",
                userPayload.class
        );

        Response response = userEndpoints.deleteUser(createUserData.getUsername());
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        Response getDeletedResponse = userEndpoints.getUser(createUserData.getUsername());
        getDeletedResponse.then().log().all();

        Assert.assertEquals(getDeletedResponse.getStatusCode(), 404);
    }
}