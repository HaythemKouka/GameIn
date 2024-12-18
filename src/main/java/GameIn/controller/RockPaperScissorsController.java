package GameIn.controller;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import GameIn.services.GameResultService;

@Controller
@RequestMapping("/rps")
public class RockPaperScissorsController {

@Autowired
private GameResultService gameResultService;
    private String result = "";

    @GetMapping
    public String game(Model model) {
        model.addAttribute("result", result);
        return "rps";
    }


    @PostMapping("/play")
    public String playGame(@RequestParam String choice, @RequestParam String username,  Model model) {
        // Choix de l'ordinateur
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        String computerChoice = choices[random.nextInt(3)];

        // Déterminer le résultat
        String result = determineWinner(choice, computerChoice);

        // Sauvegarder le résultat (vous pouvez ajouter un service pour ça)
        saveGameResult(username, computerChoice, result);

        // Passer les informations au modèle
        model.addAttribute("userChoice", choice);  // Choix de l'utilisateur
        model.addAttribute("computerChoice", computerChoice);  // Choix de l'ordinateur
        model.addAttribute("result", result);  // Résultat du jeu

        return "rps"; // La vue avec le résultat
    }

    private String determineWinner(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {
            return "It's a tie!";
        }

        if ((userChoice.equals("rock") && computerChoice.equals("scissors")) ||
            (userChoice.equals("paper") && computerChoice.equals("rock")) ||
            (userChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "You win!";
        } else {
            return "You lose!";
        }
    }

    private void saveGameResult(String email, String computerChoice, String result) {
        // Sauvegarder le résultat dans la base de données si nécessaire
        // gameResultService.saveGameResult(username, "Rock Paper Scissors", result);
        // Vous pouvez adapter la méthode saveGameResult selon vos besoins.
    	if(result.equals("It's a tie!")) {
    		result="Draw";
    	}
    	if(result.equals("You win!")) {
    		result="Win";
    	}
    	if(result.equals("You lose!")) {
    		result="Lose";
    	}
gameResultService.saveGameResult(email, "RPS", result);

        
     }
}
