package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rps")
public class RockPaperScissorsController {

    private String result = "";

    @GetMapping
    public String game(Model model) {
        model.addAttribute("result", result);
        return "rps";
    }

    @PostMapping("/play")
    public String play(@RequestParam String choice, Model model) {
        String[] options = {"rock", "paper", "scissors"};
        String computerChoice = options[(int) (Math.random() * 3)];

        if (choice.equals(computerChoice)) {
            result = "Draw! You both chose " + choice;
        } else if ((choice.equals("rock") && computerChoice.equals("scissors")) ||
                   (choice.equals("scissors") && computerChoice.equals("paper")) ||
                   (choice.equals("paper") && computerChoice.equals("rock"))) {
            result = "You win! " + choice + " beats " + computerChoice;
        } else {
            result = "You lose! " + computerChoice + " beats " + choice;
        }

        model.addAttribute("result", result);
        return "rps";
    }
}
