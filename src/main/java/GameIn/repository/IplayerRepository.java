package GameIn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import GameIn.model.entity.PlayerMySQL;

import java.util.Optional;

@Repository
public interface IplayerRepository extends JpaRepository<PlayerMySQL, Integer> {
    boolean existsByEmail(String email);
    Optional<PlayerMySQL> findByEmail(String email);

}
