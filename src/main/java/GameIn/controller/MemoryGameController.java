package GameIn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MemoryGameController {

    private List<String> colors; // Liste des couleurs pour le jeu
    private List<String> visibleCards; // Liste pour stocker l'état des cartes visibles
    private List<Integer> selectedCards; // Liste pour stocker les cartes sélectionnées
    private boolean gameOver; // État du jeu (si le jeu est terminé)

    public MemoryGameController() {
        // Liste de couleurs, chaque couleur est dupliquée pour faire des paires
        colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("purple");
        colors.add("orange");
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("purple");
        colors.add("orange");
    }

    @GetMapping("/memory")
    public String startGame(Model model) {
        gameOver = false;

        // Mélanger les couleurs pour créer une grille aléatoire
        Collections.shuffle(colors);

        // Initialiser les cartes visibles (cachées)
        visibleCards = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            visibleCards.add("hidden");
        }

        selectedCards = new ArrayList<>();

        model.addAttribute("colors", visibleCards);
        model.addAttribute("gameOver", gameOver);
        return "memory"; // Vue du jeu
    }

    @PostMapping("/memory/reveal")
    public String revealCard(@RequestParam int index, Model model) {
        if (gameOver) {
            model.addAttribute("message", "You have completed the game!");
            return "memory";
        }

        // Révéler la carte sélectionnée
        visibleCards.set(index, colors.get(index));
        selectedCards.add(index);

        if (selectedCards.size() == 2) {
            // Vérifier si les deux cartes correspondent
            int index1 = selectedCards.get(0);
            int index2 = selectedCards.get(1);

            if (!colors.get(index1).equals(colors.get(index2))) {
                // Si les cartes ne correspondent pas, les masquer à nouveau après un délai
                model.addAttribute("result", "No match!");
                // Vous pouvez utiliser une logique de temporisation ici pour masquer les cartes après un délai
                visibleCards.set(index1, "hidden");
                visibleCards.set(index2, "hidden");
            } else {
                model.addAttribute("result", "Match found!");
            }

            // Réinitialiser les cartes sélectionnées
            selectedCards.clear();

            // Vérifier si toutes les cartes ont été trouvées
            gameOver = !visibleCards.contains("hidden");

            if (gameOver) {
                model.addAttribute("message", "Congratulations, you completed the game!");
            }
        }

        model.addAttribute("colors", visibleCards);
        model.addAttribute("gameOver", gameOver);
        return "memory";
    }
}
