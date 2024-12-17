package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.services.GameResultService;

@Controller
@RequestMapping("/math")
public class MathGameController {

    private int num1;
    private int num2;
    private int answer;
    private String result = "";
@Autowired
private GameResultService gameResultService;
    @GetMapping
    public String game(Model model) {
        generateQuestion();
        model.addAttribute("question", num1 + " + " + num2);
        model.addAttribute("result", result);
        return "math";
    }
    @PostMapping("/math/saveResult")
    public String saveMathResult(@RequestParam String username, @RequestParam String result) {
        gameResultService.saveGameResult(username, "MathGame", result);
        return "redirect:/page/home";
    }


    @PostMapping("/submit")
    public String submitAnswer(@RequestParam int userAnswer, 
                                @RequestParam String username, 
                                Model model) {
        boolean isCorrect = userAnswer == answer;
        result = isCorrect ? "Correct!" : "Incorrect! The correct answer was " + answer;

        // Sauvegarder le résultat
        String gameResult = isCorrect ? "Win" : "Lose";
        gameResultService.saveGameResult(username, "MathGame", gameResult);

        // Générer une nouvelle question
        generateQuestion();
        model.addAttribute("question", num1 + " + " + num2);
        model.addAttribute("result", result);
        return "math";
    }

    private void generateQuestion() {
        num1 = (int) (Math.random() * 10);
        num2 = (int) (Math.random() * 10);
        answer = num1 + num2;
    }
}
