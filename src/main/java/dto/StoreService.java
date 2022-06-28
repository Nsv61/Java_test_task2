package dto;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StoreService {

    OpenApiValidationFilter filter = new OpenApiValidationFilter("https://petstore.swagger.io/v2/swagger.json");
    RequestSpecification specNoFilter,spec;

    public StoreService(){
       specNoFilter = given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON);

       spec = given()
                .baseUri("https://petstore.swagger.io/v2")
                .filter(filter)
                .log().all()
                .contentType(ContentType.JSON);
    }

    public Response PlaceOrder(Order order){
        return given(spec)
                .body(order)
                .when()
                .post("/store/order");
    }

    public Response FindOrder(Order order){
        return given(spec)
                .when()
                .get("/store/order/"+order.getId());
    }

    public Response DeleteOrder(Order order){
        return given(specNoFilter)
                .body(order)
                .when()
                .delete("/store/order/"+order.getId());
    }

    public Response GetInventory(){
        return given(specNoFilter)
                .when()
                .get("/store/inventory");
    }

}
