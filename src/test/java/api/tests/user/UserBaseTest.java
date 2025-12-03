package api.tests.user;

import api.base.BaseSetup;
import api.client.UserApi;
import api.model.UserDto;
import org.junit.jupiter.api.BeforeEach;

public class UserBaseTest extends BaseSetup {
    protected final static UserApi userapi = new UserApi(API_KEY);
    protected static UserDto user;

    @BeforeEach
    void initUser() {
        user = UserDto.builder()
                .id(1L)
                .username("username")
                .email("example@mail.com")
                .userStatus(0)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phone("1234567")
                .password("password")
                .build();

        userapi.createUser(user);
    }
}
