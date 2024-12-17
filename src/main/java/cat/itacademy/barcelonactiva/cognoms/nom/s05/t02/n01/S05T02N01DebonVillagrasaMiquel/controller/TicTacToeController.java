package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
@Controller
public class TicTacToeController {

    // Initialiser la partie
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
    }

    // Afficher la page de configuration initiale (choix du symbole et du premier joueur)
    @GetMapping("/tictactoe/setup")
    public String setupPage() {
        return "setup";
    }

    // Configuration du jeu avec le choix de X ou O et qui commence
    @PostMapping("/tictactoe/setup")
    public String setupGame(@RequestParam String playerChoice, @RequestParam String firstPlayer, HttpSession session) {
        // Enregistrer le choix du joueur
        session.setAttribute("playerChoice", playerChoice);
        session.setAttribute("firstPlayer", firstPlayer);

        // Initialiser la grille et les autres paramètres du jeu
        initializeGame(session);

        // Déterminer qui commence
        String currentPlayer = firstPlayer.equals("human") ? playerChoice : (playerChoice.equals("X") ? "O" : "X");
        session.setAttribute("currentPlayer", currentPlayer);

        // Si l'ordinateur commence, il joue tout de suite
        if (firstPlayer.equals("computer")) {
            playComputerTurn(session);
        }

        return "redirect:/tictactoe"; // Afficher la grille de jeu après la configuration
    }

    // Afficher la grille de jeu
    @GetMapping("/tictactoe")
    public String showGamePage(Model model, HttpSession session) {
        if (session.getAttribute("grid") == null) {
            initializeGame(session);
        }

        String[][] grid = (String[][]) session.getAttribute("grid");
        String currentPlayer = (String) session.getAttribute("currentPlayer");
        String winner = (String) session.getAttribute("winner");
        boolean gameOver = (boolean) session.getAttribute("gameOver");

        model.addAttribute("grid", grid);
        model.addAttribute("currentPlayer", currentPlayer);
        model.addAttribute("winner", winner);
        model.addAttribute("gameOver", gameOver);

        return "tictactoe"; // Retourner la vue du jeu
    }

    // Jouer un coup
    @PostMapping("/tictactoe/play")
    public String play(@RequestParam String cell, HttpSession session, Model model) {
        String[][] grid = (String[][]) session.getAttribute("grid");
        String currentPlayer = (String) session.getAttribute("currentPlayer");
        boolean gameOver = (boolean) session.getAttribute("gameOver");
        String winner = (String) session.getAttribute("winner");

        if (gameOver) {
            return "redirect:/tictactoe"; // Si le jeu est terminé, on redirige vers la page d'affichage
        }

        String[] indices = cell.split(",");
        int row = Integer.parseInt(indices[0]);
        int col = Integer.parseInt(indices[1]);

        // Vérifier si la case est déjà occupée
        if (!grid[row][col].equals("")) {
            model.addAttribute("error", "Cette case est déjà occupée !");
            model.addAttribute("grid", grid);
            model.addAttribute("currentPlayer", currentPlayer);
            model.addAttribute("winner", winner);
            return "tictactoe"; // Retourner sans effectuer d'action si la case est occupée
        }

        // Mettre à jour la case avec le symbole du joueur
        grid[row][col] = currentPlayer;

        // Vérifier si le joueur a gagné
        if (checkWinner(grid, row, col, currentPlayer)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("winner", currentPlayer);
            model.addAttribute("winner", "Le joueur " + currentPlayer + " a gagné !");
        } else {
            // Passer au joueur suivant
            currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
            session.setAttribute("currentPlayer", currentPlayer);

            // Si c'est l'ordinateur qui doit jouer, on déclenche son coup
            if (currentPlayer.equals("O") && session.getAttribute("firstPlayer").equals("human")) {
                playComputerTurn(session);
            }
        }

        // Mettre à jour la session avec la grille mise à jour
        session.setAttribute("grid", grid);

        // Retourner la vue de jeu avec la grille mise à jour
        model.addAttribute("grid", grid);
        model.addAttribute("currentPlayer", currentPlayer);
        return "tictactoe";
    }


    // Jouer un coup pour l'ordinateur
    private void playComputerTurn(HttpSession session) {
        String[][] grid = (String[][]) session.getAttribute("grid");
        Random rand = new Random();
        int row, col;

        // L'ordinateur fait un coup aléatoire jusqu'à ce qu'il trouve une case vide
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!grid[row][col].equals("")); // Trouver une case vide

        String computerChoice = (String) session.getAttribute("playerChoice");
        grid[row][col] = computerChoice; // L'ordinateur joue le symbole attribué

        // Vérifier si l'ordinateur a gagné
        if (checkWinner(grid, row, col, computerChoice)) {
            session.setAttribute("gameOver", true);
            session.setAttribute("winner", computerChoice);
        } else {
            // Passer au joueur humain
            session.setAttribute("currentPlayer", "X");
        }

        session.setAttribute("grid", grid);
    }

    // Vérifier si un joueur a gagné
    private boolean checkWinner(String[][] grid, int row, int col, String currentPlayer) {
        // Vérifier la ligne
        if (grid[row][0].equals(currentPlayer) && grid[row][1].equals(currentPlayer) && grid[row][2].equals(currentPlayer)) {
            return true;
        }
        // Vérifier la colonne
        if (grid[0][col].equals(currentPlayer) && grid[1][col].equals(currentPlayer) && grid[2][col].equals(currentPlayer)) {
            return true;
        }
        // Vérifier la diagonale principale
        if (row == col && grid[0][0].equals(currentPlayer) && grid[1][1].equals(currentPlayer) && grid[2][2].equals(currentPlayer)) {
            return true;
        }
        // Vérifier la diagonale secondaire
        if (row + col == 2 && grid[0][2].equals(currentPlayer) && grid[1][1].equals(currentPlayer) && grid[2][0].equals(currentPlayer)) {
            return true;
        }
        return false;
    }

    // Réinitialiser le jeu
    @PostMapping("/tictactoe/reset")
    public String resetGame(HttpSession session, Model model) {
        initializeGame(session); // Réinitialiser le jeu
        model.addAttribute("grid", session.getAttribute("grid"));
        model.addAttribute("currentPlayer", session.getAttribute("currentPlayer"));
        return "tictactoe"; // Retourner la vue du jeu
    }
}
