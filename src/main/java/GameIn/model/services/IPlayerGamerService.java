package GameIn.model.services;

import java.util.List;

import GameIn.controller.auth.RegisterRequest;
import GameIn.exceptions.customExceptions.UserNotFoundException;
import GameIn.model.dto.GameDTO;
import GameIn.model.dto.PlayerGameDTO;
import GameIn.model.entity.PlayerMySQL;

public interface IPlayerGamerService {

    List<PlayerGameDTO> getAllPlayersDTO();
    List<PlayerGameDTO> getAllPlayersDTORanking();
    PlayerGameDTO updatePlayer(RegisterRequest updatedPlayer, int id);
    GameDTO saveGame(int id);
    List<GameDTO> deleteGamesByPlayerId(int id);
    PlayerGameDTO findPlayerDTOById(int id);
    List<GameDTO> findGamesByPlayerId(int id) throws UserNotFoundException;
    PlayerGameDTO getWorstPlayer();
    PlayerGameDTO getBestPlayer();
    Double averageTotalMarks();
    List<PlayerMySQL> getAllPlayerMySQL();
    PlayerMySQL getPlayer(int id);
    List<GameDTO> getGamePagination(int id, int pageNo, int pageSize);

}
