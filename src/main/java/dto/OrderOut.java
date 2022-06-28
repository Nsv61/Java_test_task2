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

public class OrderOut {
    private Long id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
    private String status;
    private Boolean complete;

}
