package GameIn.model.services;
 
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemoryGameService {

    private List<String> cards = new ArrayList<>();
    private Set<Integer> flippedCards = new HashSet<>();
    private Set<Integer> matchedCards = new HashSet<>();
    private boolean gameWon = false;

    // Mélanger les cartes
    public List<String> getShuffledCards() {
        if (cards.isEmpty()) {
            initializeCards();  // Initialiser les cartes si ce n'est pas déjà fait
        }
        return cards;
    }

    // Initialiser les cartes (par exemple des paires de couleurs ou d'images)
    private void initializeCards() {
        List<String> cardPairs = Arrays.asList("Card 1", "Card 1", "Card 2", "Card 2", "Card 3", "Card 3", "Card 4", "Card 4");
        Collections.shuffle(cardPairs);
        cards.addAll(cardPairs);
    }

    // Obtenir les indices des cartes retournées
    public Set<Integer> getFlippedCards() {
        return flippedCards;
    }

    // Retourner la carte à l'indice donné
    public void flipCard(int index) {
        if (!flippedCards.contains(index) && !matchedCards.contains(index)) {
            flippedCards.add(index);  // Ajouter l'indice de la carte retournée

            if (flippedCards.size() % 2 == 0) {
                checkForMatch();
            }
        }
    }

    // Vérifier si deux cartes retournées sont une paire
    private void checkForMatch() {
        List<Integer> flippedList = new ArrayList<>(flippedCards);
        int firstCardIndex = flippedList.get(flippedList.size() - 2);
        int secondCardIndex = flippedList.get(flippedList.size() - 1);

        if (cards.get(firstCardIndex).equals(cards.get(secondCardIndex))) {
            matchedCards.add(firstCardIndex);
            matchedCards.add(secondCardIndex);
        } else {
            // Si elles ne sont pas une paire, les retourner après un délai
            // Vous pouvez ajouter un délai ici pour un effet visuel
        }
    }

    // Vérifier si le jeu est gagné
    public boolean isGameWon() {
        return matchedCards.size() == cards.size();
    }

    // Réinitialiser le jeu
    public void resetGame() {
        cards.clear();
        flippedCards.clear();
        matchedCards.clear();
        gameWon = false;
    }
}
