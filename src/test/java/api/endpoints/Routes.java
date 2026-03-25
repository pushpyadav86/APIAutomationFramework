package api.endpoints;

public class Routes {

	// User Routes
	public static String baseURL = "https://petstore.swagger.io/v2";
	public static String getURL = baseURL + "/user/{username}";
	public static String PostURL = baseURL + "/user";
	public static String putURL = baseURL + "/user/{username}";
	public static String deleteURL = baseURL + "/user/{username}";
	
	// Pet
    public static String CREATE_PET_URL = baseURL + "/pet";
    public static String GET_PET_URL = baseURL + "/pet/{petId}";
    public static String UPDATE_PET_URL = baseURL + "/pet";
    public static String DELETE_PET_URL = baseURL + "/pet/{petId}";

    // Store / Order
    public static String CREATE_ORDER_URL = baseURL + "/store/order";
    public static String GET_ORDER_URL = baseURL + "/store/order/{orderId}";
}
