package api.tests.user;

import api.model.ApiResponseDto;
import api.model.UserDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserGetTest extends UserBaseTest {

    @Test
    @DisplayName("GET /user/{username}: successfully get user by username")
    void testUserGetUserByUsername() {
        Response response = userapi.getUserByUsername(user.getUsername());
        UserDto userResponse = response.getBody().as(UserDto.class);

        assertAll(
                "Validating get response",
                () -> assertEquals(200, response.statusCode(),
                        "Expected code/got: 200/" + response.statusCode()),
                () -> assertEquals(user, userResponse, "User object mismatch")
        );
    }

    @Test
    @DisplayName("GET /user/{username}: fall on unknown user")
    void testUserGetUnknownUserByUsername() {
        Response response = userapi.getUserByUsername("unknown");

        assertAll(
                "Validating got response with 'unknown' username",
                () -> assertEquals(404, response.statusCode(),
                        "Expected code/got: 404/" + response.statusCode())
        );

        Response nullResponse = userapi.getUserByUsername(null);

        assertAll(
                "Validating get response with 'null' username",
                () -> assertEquals(404, nullResponse.statusCode(),
                        "Expected code/got: 404/" + nullResponse.statusCode())
        );
    }

    @Test
    @DisplayName("GET /user/login: successfully login user")
    void testUserLoginUserSuccess() {
        Response response = userapi.loginUser(user.getUsername(), user.getPassword());

        assertAll(
                "Validate login response: code, headers",
                () -> assertTrue(response.getHeaders().hasHeaderWithName("X-Expires-After"),
                        "Response doesn't have header 'X-Expires-After'"),
                () -> assertTrue(response.getHeaders().hasHeaderWithName("X-Rate-Limit"),
                        "Response doesn't have header 'X-Rate-Limit'")
        );
    }

    @Test
    @DisplayName("GET /user/logout: successfully logout")
    void testUserLogoutUser() {
        Response response = userapi.logoutUser();
        ApiResponseDto apiResponseDto = response.getBody().as(ApiResponseDto.class);

        assertEquals(200, response.statusCode(),
                "Expected code/got code 200/" + response.statusCode());
        assertTrue(apiResponseDto.message().contains("ok"), "Message mismatch");
    }
}
