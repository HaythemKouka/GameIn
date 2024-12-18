package GameIn.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Summary PLayer + AVG(Games) DTO Information")
public class PlayerGameDTO {
    @Schema(defaultValue = "1", description = "Player ID")
    private Integer id;

    @Schema(defaultValue = "Name", description = "Player Name")
    private String name;

    @Schema(defaultValue = "0", description = "Average mark")
    private double averageMark;

    @Schema(defaultValue = "0", description = "Success rate")
    private String successRate;
}
