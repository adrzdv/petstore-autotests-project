package api.tests.user;

import api.model.UserDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPostTest extends UserBaseTest {

    @Test
    @DisplayName("POST /user/createWithList: successfully creation with given input list")
    void testUserCreateUsersWithListSuccessfully() {

        List<UserDto> userList = List.of(user);

        Response response = userapi.createWithList(userList);
        assertEquals(200, response.statusCode(), "Expected code/got code: 200/" + response.statusCode());
    }

    @Test
    @DisplayName("POST /user/createWithList: should fail with empty or null body")
    void testUserCreateUsersWithListShouldFall() {
        List<Object> userList = List.of("");

        Response response = userapi.createWithList(null);
        assertEquals(405, response.statusCode(), "Expected code/got code: 405/" + response.statusCode());
        response = userapi.createWithList(userList);
        assertEquals(500, response.statusCode(), "Expected code/got code: 405/" + response.statusCode());
    }

    @Test
    @DisplayName("POST /user: create new user successfully")
    void testUserCreateNewUser() {
        UserDto newUser = UserDto.builder()
                .id(2L)
                .username("another")
                .email("example@mail.com")
                .userStatus(0)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("1234567")
                .password("password")
                .build();

        Response postResponse = userapi.createUser(newUser);
        Response response = userapi.getUserByUsername("another");

        assertEquals(200, postResponse.statusCode(),
                "Expected code/got code: 200/" + postResponse.statusCode());
        assertEquals(newUser, response.getBody().as(UserDto.class),
                "Mismatch user objects");
    }

}
