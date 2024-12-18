package GameIn.services;
  
import org.springframework.stereotype.Service;

import GameIn.model.entity.GameResult;
import GameIn.model.repository.GameResultRepository;

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
