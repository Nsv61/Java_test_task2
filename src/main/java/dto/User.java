package dto;

import lombok.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;
//import org.codehaus.map.annotate.JsonSerialize;

@Getter
@Setter
@Builder
@JsonSerialize
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String email;
    private String firstName;
    private Long id;
    private String lastName;
    private String password;
    private String phone;
    private Long userStatus;
    private String username;
}
