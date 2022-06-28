package UsersTest;

import dto.*;
import generators.OrderGenerator;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class OrderTest {

    @Test
    public void placeOrderTest() {
        Order order = OrderGenerator.getNewOrder();
        StoreService storeService = new StoreService();
        Response response = storeService.PlaceOrder(order);

        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Order.json"));

        OrderOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(OrderOut.class);

        OrderOut expected = OrderOut.builder()
                .id(7L)
                .petId(0)
                .quantity(4)
                .shipDate("2022-05-10T10:04:03.580+0000")
                .status("placed")
                .complete(true)
                .build();

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void findOrderTest() {
        Order order = OrderGenerator.getNewOrder();
        StoreService storeService = new StoreService();


        // проверяем код ответа сервера перед проверкой содержимого
        if (storeService.FindOrder(order).statusCode() != 200)
            storeService.PlaceOrder(order); //создем, если нет

        Response response = storeService.FindOrder(order);

        OrderOut actual = response.then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .body()
                .as(OrderOut.class);

        OrderOut expected = OrderOut.builder()
                .id(7L)
                .petId(0)
                .quantity(4)
                .shipDate("2022-05-10T10:04:03.580+0000")
                .status("placed")
                .complete(true)
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteOrderTest() {

        Order order = OrderGenerator.getNewOrder();
        StoreService storeService = new StoreService();

        // проверяем код ответа сервера перед проверкой содержимого
        if (storeService.FindOrder(order).statusCode() != 200)
            storeService.PlaceOrder(order); //создем, если нет

        Response response = storeService.DeleteOrder(order);
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
                .code(200)
                .message("7")
                .type("unknown")
                .build();

        Assertions.assertEquals(expected, actual);
    }

    @Test // каждый раз приходят разные данные, сверить полноценно не получается
    public void inventoryTest() {
        StoreService storeService = new StoreService();
        Response response = storeService.GetInventory();
        response
                .then()
                .assertThat()
                .statusCode(200); //оставила только проверку возвращаемого кода, т.к. содержимое динамически изменяется (названия полей)
    }

}
