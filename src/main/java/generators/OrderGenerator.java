package generators;

import dto.Order;

public class OrderGenerator {

    public static Order getNewOrder() {
        return Order.builder()
                .id(7L)
                .petId(0)
                .quantity(4)
                .shipDate("2022-05-10T10:04:03.580Z")
                .status("placed")
                .complete(true)
                .build();
    }
}