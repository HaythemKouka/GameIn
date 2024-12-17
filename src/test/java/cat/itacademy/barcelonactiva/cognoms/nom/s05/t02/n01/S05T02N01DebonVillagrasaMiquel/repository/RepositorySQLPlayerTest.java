//package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.repository;
//
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.Role;
//import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.model.entity.PlayerMySQL;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//public class RepositorySQLPlayerTest {
//
//    /**
//     * Test done with Junit
//     * Arrange Act Assert
//     * ID Autogenerated by Hibernate
//     */
//
//    @Autowired
//    private IplayerRepository playerMySQLRepository;
//
//    private PlayerMySQL playerMySQL_AllArguments;
//    private PlayerMySQL playerMySQL1;
//    private PlayerMySQL playerMySQL2;
//
//    @BeforeEach
//    public void setUp(){
//        playerMySQL_AllArguments = PlayerMySQL.builder()
//                .name("Miquel")
//                .surname("Debon")
//                .role(Role.USER)
//                .email("mdebonbcn@gmail.com")
//                .password("mimi")
//                .registerDate(new Date().toString())
//                .build();
//
//        playerMySQL1 = new PlayerMySQL("Miquel");
//        playerMySQL2 = new PlayerMySQL("Marta");
//    }
//
//
//    @Test
//    public void playerMySQLRepo_Save_ReturnSavedPlayer(){
//        //Arrange
//
//        //Act
//        PlayerMySQL savedPlayerSQL = playerMySQLRepository.save(playerMySQL1);
//
//        //Assert
//        Assertions.assertThat(savedPlayerSQL).isNotNull();
//        Assertions.assertThat(savedPlayerSQL.getId()).isGreaterThan(0);
//        log.info(savedPlayerSQL.toString());
//    }
//
//    @Test
//    public void playerMySQLRepo_GetAll_ReturnListOfPlayer(){
//        playerMySQLRepository.save(playerMySQL1);
//        playerMySQLRepository.save(playerMySQL2);
//
//        List<PlayerMySQL> playerMySQLList = playerMySQLRepository.findAll();
//        log.info(playerMySQLList.toString());
//
//        Assertions.assertThat(playerMySQLList).isNotNull();
//        Assertions.assertThat(playerMySQLList.size()).isEqualTo(2);
//    }
//
//    @Test
//    public void playerMySQLRepo_FindById_ReturnOne(){
//        playerMySQLRepository.save(playerMySQL1);
//        int id = playerMySQL1.getId();
//
//        PlayerMySQL savedPlayer = playerMySQLRepository.findById(id).get();
//
//        Assertions.assertThat(savedPlayer).isNotNull();
//        Assertions.assertThat(savedPlayer.getId()).isEqualTo(id);
//    }
//
//    @Test
//    public void playerMySQLRepo_UpdatePlayer_ReturnUpdatedPlayer(){
//        playerMySQLRepository.save(playerMySQL1);
//        int id = playerMySQL1.getId();
//
//        PlayerMySQL savedPlayer = playerMySQLRepository.findById(id).get();
//        String updatedName = "Manolo";
//        savedPlayer.setName(updatedName);
//
//        PlayerMySQL updatedPlayer = playerMySQLRepository.save(savedPlayer);
//
//        Assertions.assertThat(updatedPlayer).isNotNull();
//        Assertions.assertThat(updatedPlayer.getName()).isEqualTo(updatedName);
//    }
//
//    @Test
//    public void playerMySQLRepo_DeleteById_ReturnIsEmpty(){
//        playerMySQLRepository.save(playerMySQL1);
//        List<PlayerMySQL> list = playerMySQLRepository.findAll();
//
//        playerMySQLRepository.deleteById(playerMySQL1.getId());
//
//        List<PlayerMySQL> listEmpty = playerMySQLRepository.findAll();
//        Optional<PlayerMySQL> optional = playerMySQLRepository.findById(playerMySQL1.getId());
//
//        Assertions.assertThat(list).isNotEmpty();
//        Assertions.assertThat(listEmpty).isEmpty();
//        Assertions.assertThat(optional).isEmpty();
//    }
//
//
//    @Test
//    public void playerMySQLRepo_findByEmail_ReturnPlayer(){
//        //Static Player
//        playerMySQLRepository.save(playerMySQL_AllArguments);
//        Optional<PlayerMySQL> optional = playerMySQLRepository.findByEmail(playerMySQL_AllArguments.getEmail());
//
//        Assertions.assertThat(optional).isNotEmpty();
//        Assertions.assertThat(optional.get().getName()).isEqualTo("Miquel");
//    }
//
//
//    @Test
//    public void playerMySQLRepo_ExistByID_ReturnBoolean(){
//        playerMySQLRepository.save(playerMySQL1);
//
//        Boolean result = playerMySQLRepository.existsById(playerMySQL1.getId());
//
//        Assertions.assertThat(result).isTrue();
//    }
//
//    @Test
//    public void playerMySQLRepo_ExistByEmail_ReturnBoolean(){
//        playerMySQLRepository.save(playerMySQL_AllArguments);
//
//        Boolean result = playerMySQLRepository.existsByEmail(playerMySQL_AllArguments.getEmail());
//
//        Assertions.assertThat(result).isTrue();
//    }
//
//
//
//
//}