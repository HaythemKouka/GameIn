package GameIn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/games")
    public String showGamesPage() {
        return "games"; // Page des jeux disponibles
    }

    @GetMapping("/games/tictactoe")
    
    public String startTicTacToe() {
        // Logique d'initialisation de Tic Tac Toe (si nécessaire)
        return "setup"; // Page du jeu Tic Tac Toe
    }

    @GetMapping("/games/rps")
    public String startRPS() {
        // Logique d'initialisation pour le jeu Rock Paper Scissors
        return "rps"; // Page du jeu Rock Paper Scissors
    }

    @GetMapping("/games/math")
    public String startMathGame() {
        // Logique d'initialisation pour le jeu Math
        return "math"; // Page du jeu Math
    }
}
