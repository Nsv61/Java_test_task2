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

public class Order {
    private Long id;
    private Integer petId;
    private Integer quantity;
    private String shipDate;
 //   private DateTime shipDate;
    private String status;
    private Boolean complete;
}
