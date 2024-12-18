package GameIn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import GameIn.model.services.MemoryGameService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MemoryGameController {

    @Autowired
    private MemoryGameService memoryGameService;

    // Liste des cartes et paires retournées
    private List<Integer> flippedCards = new ArrayList<>();
    private List<String> cardValues = List.of("A", "B", "C", "D", "A", "B", "C", "D");  // Exemple de cartes

    @GetMapping("/memory")
    public String memoryGame(Model model) {
        model.addAttribute("flippedCards", flippedCards);
        model.addAttribute("cardValues", cardValues);
        return "memory";  // Retourne la page du jeu
    }

    @PostMapping("/game/flipCard")
    @ResponseBody
    public List<Integer> flipCard(@RequestParam("index") String index) {
        // Vérification que l'index est un nombre valide
        if (index == null || index.equals("NaN")) {
            // Gérer l'erreur si l'index est invalide
            System.out.println("Invalid index received: " + index);
            return flippedCards;  // Ne pas faire de changement si l'index est invalide
        }

        try {
            int indexInt = Integer.parseInt(index);  // Convertir l'index en entier

            // Si la carte n'est pas encore retournée, on l'ajoute
            if (!flippedCards.contains(indexInt)) {
                flippedCards.add(indexInt);
            }

            // Vérifier si deux cartes ont été retournées
            if (flippedCards.size() == 2) {
                int firstIndex = flippedCards.get(0);
                int secondIndex = flippedCards.get(1);

                // Si les deux cartes sont identiques, on les garde visibles
                if (cardValues.get(firstIndex).equals(cardValues.get(secondIndex))) {
                    // Ces cartes restent visibles
                } else {
                    // Sinon, les retourner après une petite pause (simulation d'un délai)
                    try {
                        Thread.sleep(1000);  // 1 seconde de délai avant de les retourner
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // On retire les cartes retournées
                    flippedCards.clear();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing index: " + index);  // Log l'erreur si l'index ne peut pas être converti
        }

        return flippedCards;  // Retourner l'état actuel des cartes retournées
    }

    @PostMapping("/memory/reset")
    public String resetGame(Model model) {
        flippedCards.clear();  // Réinitialiser la liste des cartes retournées
        return "redirect:/memory";  // Rediriger vers la page du jeu
    }
}
