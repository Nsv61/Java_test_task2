package generators;

import dto.User;

public class UserGenerator {
    private  static long COUNT = 0;

    public static User getNewUser(){
        COUNT++;
        return User.builder()
                .id(COUNT)
                .email("EMAIL")
                .firstName("First name")
                .lastName("Last Name")
                .password("pass")
                .phone("4564")
                .username("userName"+COUNT)
                .userStatus(2L)
                .build();
    }
    public static User getExistUser(){
        return User.builder()
                .id(COUNT)
                .email("EMAIL")
                .firstName("First Name")
                .lastName("Last Name")
                .password("PASSWORD")
                .phone("8(999)999-99-99")
                .username("USERNAME" + COUNT)
                .userStatus(2L)
                .build();
    }

}
