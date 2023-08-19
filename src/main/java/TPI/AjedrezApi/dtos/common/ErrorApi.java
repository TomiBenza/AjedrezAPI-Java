package TPI.AjedrezApi.dtos.common;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorApi {
    private String timestamp;
    private Integer status;
    private String error;
    private String message;


}
