package api.tests.user;

import api.model.UserDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPutTest extends UserBaseTest {

    @Test
    @DisplayName("PUT /user/{username}: successfully update an existing user")
    void testUserUpdateExistingUser() {
        UserDto updateUserDto = UserDto.builder()
                .id(1L)
                .username("username")
                .email("example@mail.com")
                .userStatus(0)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("9876543")
                .password("password")
                .build();

        Response response = userapi.updateUserByUsername("username", updateUserDto);
        UserDto actualUser = userapi.getUserByUsername("username").getBody().as(UserDto.class);

        assertEquals(200, response.statusCode(), "Expected code/got code: 200/" + response.statusCode());
        assertEquals(updateUserDto, actualUser, "User object mismatch");
    }

    @Test
    @DisplayName("PUT /user/{username}: update user with null username or body")
    void testUserUpdateUnknownUser() {
        Response responseBothNull = userapi.updateUserByUsername(null, null);

        assertEquals(405, responseBothNull.statusCode(),
                "Path and body are null. Expected code/got code: 405/" + responseBothNull.statusCode());

        Response responsePathNull = userapi.updateUserByUsername(null, "null");

        assertEquals(405, responsePathNull.statusCode(),
                "Path is null. Expected code/got code: 405/" + responseBothNull.statusCode());

        Response responseBodyNull = userapi.updateUserByUsername("null", null);

        assertEquals(405, responseBodyNull.statusCode(),
                "Body is null. Expected code/got code: 405/" + responseBothNull.statusCode());
    }


}
