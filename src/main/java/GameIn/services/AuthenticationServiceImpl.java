package GameIn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import GameIn.controller.auth.LoginRequest;
import GameIn.controller.auth.RegisterRequest;
import GameIn.exceptions.customExceptions.DuplicateUserEmailException;
import GameIn.exceptions.customExceptions.DuplicateUserNameException;
import GameIn.model.entity.PlayerMySQL;
import GameIn.model.entity.Role;
import GameIn.model.services.IAuthenticationService;
import GameIn.repository.IplayerRepository;

import java.text.SimpleDateFormat;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private IplayerRepository repository;
    private PasswordEncoder passwordEncoder;
    private IplayerRepository playerRepository;

    @Autowired
    public AuthenticationServiceImpl(
            IplayerRepository repository,
            PasswordEncoder passwordEncoder,
            IplayerRepository playerRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.playerRepository = playerRepository;
    }


    public void register(RegisterRequest request){
        try{
            checkDuplicatedEmail(request.getEmail());
            checkDuplicatedName(request.getFirstname());

            PlayerMySQL user;
            if(!request.getEmail().contains("@admin.com")){
                user = buildPlayer(request, Role.USER);
                repository.save(user);
            }else{
                user = buildPlayer(request, Role.ADMIN);
            }
            repository.save(user);

        }catch (DuplicateUserEmailException e){
            throw new DuplicateUserEmailException("Error duplicated email");
        }catch (DuplicateUserNameException e){
            throw new DuplicateUserNameException("Error duplicated name");
        }
    }

    public void authenticate (LoginRequest request){

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

    }



    public void checkDuplicatedEmail(String email){
        if(playerRepository.findAll()
                .stream().map(PlayerMySQL::getEmail)
                .anyMatch((n)-> n.equalsIgnoreCase(email))
        ){
            throw new DuplicateUserEmailException("Duplicated name");
        }
    }

    public void checkDuplicatedName(String name){
        if (
            !name.equalsIgnoreCase("ANONYMOUS")
            &&
            playerRepository.findAll()
                    .stream().map(PlayerMySQL::getName)
                    .anyMatch((n)-> n.equalsIgnoreCase(name))
        ){
            throw new DuplicateUserNameException("Duplicated name");
        }
    }

    public PlayerMySQL buildPlayer(RegisterRequest request, Role role){
        return PlayerMySQL.builder()
                .name(request.getFirstname())
                .surname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .registerDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                        .format(new java.util.Date()))
                .build();
    }



}
