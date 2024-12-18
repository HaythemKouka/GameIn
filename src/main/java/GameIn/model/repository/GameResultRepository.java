package GameIn.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GameIn.model.entity.GameResult;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {
}