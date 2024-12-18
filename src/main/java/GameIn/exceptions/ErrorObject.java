package GameIn.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorObject {
    private int statusCode;
    private LocalDateTime date;
    private String description;
    private String path;
}
