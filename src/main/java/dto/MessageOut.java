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

public class MessageOut {
    private long code;
    private String message;
    private String type;
}

