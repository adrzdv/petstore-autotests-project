package api.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long petId;
    private Long quantity;
    private String shipDate;
    private String status;
    private Boolean complete;
}
