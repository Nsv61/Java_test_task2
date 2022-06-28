package dto;

import lombok.*;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Getter
@Setter
@Builder
@JsonSerialize
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class UserOut {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Long userStatus;
}

