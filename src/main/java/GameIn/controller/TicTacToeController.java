package GameIn.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class TicTacToeController {

    // Initialiser la grille de jeu
    private void initializeGame(HttpSession session) {
        String[][] grid = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ""; // Cellules vides
            }
        }
        session.setAttribute("grid", grid);
        session.setAttribute("gameOver", false);
        session.setAttribute("winner", null);
        session.setAttribute("draw", false); // Pour gérer les matchs nuls
    }

    // Page de configuration
    @GetMapping("/tictactoe/setup")
    public String setupPage() {
        return "setup"; // Retourne la page de configuration
    }

    // Configurer le jeu selon les choix
    @PostMapping("/tictactoe/setup")
    public String setupGame(@RequestParam String playerChoice, @RequestParam String firstPlayer, HttpSession session) {
        session.setAttribute("playerChoice", playerChoice);
        session.setAttribute("computerChoice", playerChoice.equals("X") ? "O" : "X");
        session.setAttribute("firstPlayer", firstPlayer);

        initializeGame(session); // Réinitialiser la grille

        // Définir le joueur actuel
        String currentPlayer = firstPlayer.equals("Joueur") ? playerChoice : session.getAttribute("computerChoice").toString();
        session.setAttribute("currentPlayer", currentPlayer);

        // Si l'ordinateur commence, il joue immédiatement
        if (firstPlayer.equals("Ordinateur")) {
            playComputerTurn(session);
        }

        return "redirect:/tictactoe"; // Afficher la grille de jeu
    }

    // Afficher la page de jeu
    @GetMapping("/tictactoe")
    public String showGamePage(Model model, HttpSession session) {
        if (session.getAttribute("grid") == null) {
            initializeGame(session);
        }

        model.addAttribute("grid", session.getAttribute("grid"));
        model.addAttribute("currentPlayer", session.getAttribute("currentPlayer"));
        model.addAttribute("winner", session.getAttribute("winner"));
        model.addAttribute("gameOver", session.getAttribute("gameOver"));
        model.addAttribute("draw", session.getAttribute("draw"));

        return "tictactoe"; // Vue de la grille
    }

    // Jouer un tour
    @PostMapping("/tictactoe/play")
    public String play(@RequestParam String cell, HttpSession session) {
        String[][] grid = (String[][]) session.getAttribute("grid");
        String currentPlayer = (String) session.getAttribute("currentPlayer");
        boolean gameOver = (boolean) session.getAttribute("gameOver");

        // Vérifier si le jeu est déjà terminé
        if (gameOver) {
            return "redirect:/tictactoe";
        }

        // Obtenir les indices de la cellule
        String[] indices = cell.split(",");
        int row = Integer.parseInt(indices[0]);
        int col = Integer.parseInt(indices[1]);

        // Si la cellule est déjà occupée
        if (!grid[row][col].equals("")) {
            return "redirect:/tictactoe";
        }

        // Mettre à jour la cellule avec le symbole du joueur actuel
        grid[row][col] = currentPlayer;

        // Vérifier la victoire
        if (checkWinner(grid, currentPlayer)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("winner", currentPlayer);
        } else if (isDraw(grid)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("draw", true); // Match nul
        } else {
            // Passer au joueur suivant
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            session.setAttribute("currentPlayer", currentPlayer);

            // Si c'est au tour de l'ordinateur, il joue immédiatement
            if (currentPlayer.equals(session.getAttribute("computerChoice"))) {
                playComputerTurn(session);
            }
        }

        session.setAttribute("grid", grid);
        return "redirect:/tictactoe";
    }

    // Jouer pour l'ordinateur
    private void playComputerTurn(HttpSession session) {
        String[][] grid = (String[][]) session.getAttribute("grid");
        String computerChoice = (String) session.getAttribute("computerChoice");
        Random rand = new Random();
        int row, col;

        // Trouver une cellule vide
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!grid[row][col].equals(""));

        grid[row][col] = computerChoice;

        // Vérifier la victoire de l'ordinateur
        if (checkWinner(grid, computerChoice)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("winner", computerChoice);
        } else if (isDraw(grid)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("draw", true);
        } else {
            // Passer au tour du joueur
            session.setAttribute("currentPlayer", session.getAttribute("playerChoice"));
        }

        session.setAttribute("grid", grid);
    }

    // Vérifier si un joueur a gagné
    private boolean checkWinner(String[][] grid, String player) {
        // Vérifier les lignes, colonnes et diagonales
        for (int i = 0; i < 3; i++) {
            if (grid[i][0].equals(player) && grid[i][1].equals(player) && grid[i][2].equals(player)) return true;
            if (grid[0][i].equals(player) && grid[1][i].equals(player) && grid[2][i].equals(player)) return true;
        }
        if (grid[0][0].equals(player) && grid[1][1].equals(player) && grid[2][2].equals(player)) return true;
        if (grid[0][2].equals(player) && grid[1][1].equals(player) && grid[2][0].equals(player)) return true;
        return false;
    }

    // Vérifier si toutes les cellules sont remplies (match nul)
    private boolean isDraw(String[][] grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j].equals("")) return false; // Cellule vide
            }
        }
        return true;
    }

    // Réinitialiser le jeu
    @PostMapping("/tictactoe/reset")
    public String resetGame(HttpSession session) {
        initializeGame(session);
        return "redirect:/tictactoe/setup"; // Retourner à la configuration
    }
}
