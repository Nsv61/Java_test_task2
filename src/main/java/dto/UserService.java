package dto;

import com.atlassian.oai.validator.restassured.OpenApiValidationFilter;
import dto.User;
import io.restassured.http.ContentType;
import  io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.swagger.models.parameters.QueryParameter;


import javax.management.Query;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class UserService {

    OpenApiValidationFilter filter = new OpenApiValidationFilter("https://petstore.swagger.io/v2/swagger.json");
    RequestSpecification specNoFilter, spec;

    public UserService(){
        specNoFilter = given()
                .baseUri("https://petstore.swagger.io/v2")
                .log().all()
                .contentType(ContentType.JSON);
        spec =given()
                .baseUri("https://petstore.swagger.io/v2")
                .filter(filter)
                .log().all()
                .contentType(ContentType.JSON);
    }

    public Response CreateUser(User user){
        return given(spec)
                .body(user)
                .when()
                .post("/user");
    }

/*    public Response CreateUserWithName(String name){
        return given(spec)
                .body(user)
                .when()
                .post("/user");
    }*/

    public Response CreateWithList(ArrayList<User> users){
        return given(spec)
                .body(users)
                .when()
                .post("/user/createWithList");
    }

    public Response CreateWithArray(User[] users){
        return given(spec)
                .body(users)
                .when()
                .post("/user/createWithArray");
    }

    public Response GetUserByName (String name){
        return given(spec)
               .when()
                .get("/user/"+name);
    }

    public Response UpdatedUser (User user){
        return given(specNoFilter)
                .body(user)
               .when()
                .put("/user/"+ user.getUsername());
    }

    public Response DeleteUser (String name){
        return given(specNoFilter)
                .when()
                .delete("/user/"+name);
    }

    //public Response LogsUserInto(User user) {
  /*  public Response LogsUserInto(User user) {
        return given(specNoFilter)
                .queryParams("username", user.getUsername())
                .queryParams("password", user.getPassword())
                .when()
                .get("/user/login");
        //  .get("/user/login?username="+user.getUsername()+"&password=passe"+user.getPassword());
        // вариант, когда параметры в тексте
    }
*/

    public Response LogsUserInto(String name,String pass) {
        return given(specNoFilter)
                .queryParams("username", name)
                .queryParams("password", pass)
                .when()
                .get("/user/login");
    }

    public Response LogsOut()  {
            return given(spec)
                    .when()
                    .get("/user/logout");
        }

}
