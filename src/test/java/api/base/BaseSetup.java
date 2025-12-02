package api.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseSetup {

    protected static final String API_KEY = "special_key";
    protected static final String BASE_URL = "https://petstore.swagger.io/v2";
    protected static final String FILE_SRC = "src/test/resources/test.png";
    protected static final String USER_ID = "test";
    protected static final String USERNAME = "test";
    protected static final String PASSWORD = "abc123";

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
