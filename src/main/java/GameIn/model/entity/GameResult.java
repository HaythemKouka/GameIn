package GameIn.model.entity;

 
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "game_results")
public class GameResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(name = "game_name")
    private String gameName;

    private String result;

    @Column(name = "played_at")
    private LocalDateTime playedAt = LocalDateTime.now();

    // Getters and Setters
  

}
