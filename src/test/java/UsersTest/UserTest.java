package UsersTest;

import dto.MessageOut;
import dto.User;
import dto.UserOut;
import dto.UserService;
import generators.UserGenerator;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;


public class UserTest {

    @Test
    public void createUserTest() {
        User user = UserGenerator.getNewUser();

        UserService userService = new UserService();
        Response response = userService.CreateUser(user);

        MessageOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200L)
                .message(user.getId().toString())
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createArrayUsersTest() {

        User[] users = {UserGenerator.getNewUser(), UserGenerator.getNewUser(), UserGenerator.getNewUser(), UserGenerator.getNewUser(), UserGenerator.getNewUser()};

        UserService userService = new UserService();
        Response response = userService.CreateWithArray(users);

        MessageOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200L)
                .message("ok")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void createListUsersTest() {

        ArrayList<User> users = new ArrayList<>();
        for (int i = 1; i == 4; i++) {
            users.add(UserGenerator.getNewUser());
        }

        UserService userService = new UserService();
        Response response = userService.CreateWithList(users);

        MessageOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200L)
                .message("ok")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }


    @ParameterizedTest //пример параметризированного теста
    @ValueSource(strings = {"admin", "moderator", "user"})
    public void getUserByName(String name) {

        UserService userService = new UserService();
        Response response = userService.GetUserByName(name);

        MessageOut actual = response.then()
                .log().all()
                .statusCode(404)
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(1L)
                .message("User not found")
                .type("error")
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getOneUserByName() {
        User user = UserGenerator.getNewUser();
        UserService userService = new UserService();

        // проверяем код ответа сервера перед обновлением
        if (userService.GetUserByName(user.getUsername()).statusCode() != 200)
            userService.CreateUser(user); //создаем

        Response response = userService.GetUserByName(user.getUsername());
        // проверка на соответствие схеме
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/User.json"));

        UserOut actual = response.then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(UserOut.class);
    }

    @Test
    public void updatedUserTest() {

        User user = UserGenerator.getNewUser();
        UserService userService = new UserService();

        // проверяем код ответа сервера перед обновлением
        if (userService.GetUserByName(user.getUsername()).statusCode() != 200)
            userService.LogsUserInto(user.getUsername(), user.getPassword()); //логиним

        Response response = userService.UpdatedUser(user);
        // проверка на соответствие схеме
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Message.json"));

        MessageOut actual = response.then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200)
                .message("1")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteUserTest() {

        User user = UserGenerator.getNewUser();

        UserService userService = new UserService();

        // проверяем код ответа сервера перед тем, как удалять
        if (userService.GetUserByName(user.getUsername()).statusCode() != 200)
            userService.CreateUser(user); //создаем

        Response response = userService.DeleteUser(user.getUsername());
        response
                .then()
                .assertThat() // проверяем ожидаемый код
                .statusCode(200);
    }

    @Test
    public void logsUserIntoTest() {

        User user = UserGenerator.getNewUser();

        UserService userService = new UserService();
        Response response = userService.LogsUserInto(user.getUsername(), user.getPassword());

        // проверка на соответствие схеме
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Message.json"));

        MessageOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200L)
                .message("logged in user session:")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected.getCode(), actual.getCode());
        Assertions.assertEquals(expected.getType(), actual.getType());
        // message сверяем по началу строки, т.к. номер сессии динамический
        Assertions.assertTrue(actual.getMessage().startsWith(expected.getMessage()));
    }

    @Test
    public void logsOutTest() {
        User user = UserGenerator.getNewUser();

        UserService userService = new UserService();
        Response response = userService.LogsOut();

        MessageOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(MessageOut.class);

        MessageOut expected = MessageOut.builder()
                .code(200L)
                .message("ok")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }

}
