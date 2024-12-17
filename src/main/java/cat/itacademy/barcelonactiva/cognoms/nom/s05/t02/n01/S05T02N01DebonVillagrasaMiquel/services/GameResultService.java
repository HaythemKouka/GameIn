package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.services;
  
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.GameResult;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository.GameResultRepository;

@Service
public class GameResultService {

    private final GameResultRepository repository;

    public GameResultService(GameResultRepository repository) {
        this.repository = repository;
    }

    public void saveGameResult(String username, String gameName, String result) {
        GameResult gameResult = new GameResult();
        gameResult.setUsername(username);
        gameResult.setGameName(gameName);
        gameResult.setResult(result);
        repository.save(gameResult);
    }
}
