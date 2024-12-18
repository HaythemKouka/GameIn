package GameIn.model.services;

import GameIn.controller.auth.LoginRequest;
import GameIn.controller.auth.RegisterRequest;

public interface IAuthenticationService {
    void register(RegisterRequest request);
    void authenticate (LoginRequest request);

}
