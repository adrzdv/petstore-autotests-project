package api.client;

import api.model.UserDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {
    private static final String USER = "/user";
    private final String apiKey;

    public UserApi(String apiKey) {
        this.apiKey = apiKey;
    }

    public Response createWithList(Object body) {
        RequestSpecification spec = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);

        if (body != null) {
            spec.body(body);
        }

        return spec.post(USER + "/createWithList");
    }

    public Response getUserByUsername(Object username) {
        RequestSpecification spec = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);

        if (username != null) {
            spec.body(username);
        }

        return spec.get(USER + "/" + username);
    }

    public Response updateUserByUsername(Object username, Object body) {
        RequestSpecification spec = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);

        if (body != null) {
            spec.body(body);
        }

        return spec.put(USER + "/" + username);
    }

    public Response deleteUserByUsername(Object username) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .delete(USER + "/" + username);
    }

    public Response loginUser(Object username, Object password) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .queryParam("username", username)
                .queryParam("password", password)
                .get(USER + "/login");
    }

    public Response logoutUser() {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .get(USER + "/logout");
    }

    public Response createUser(UserDto userDto) {

        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(userDto)
                .post(USER);
    }

}
