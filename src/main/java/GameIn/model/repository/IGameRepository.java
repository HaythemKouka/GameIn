package GameIn.model.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GameIn.model.entity.GameMySQL;

import java.util.List;
@Repository
public interface IGameRepository extends JpaRepository<GameMySQL, Integer> {

    @Transactional
    List<GameMySQL> deleteByPlayerId(int id);
    List<GameMySQL> findByPlayerId(int id);
    Page<GameMySQL> findByPlayerId(int id, Pageable pageable);

}
