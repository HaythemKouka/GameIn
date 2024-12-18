package GameIn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import GameIn.services.GameResultService;

@Controller
@RequestMapping("/math")
public class MathGameController {

    private int level = 1; // Niveau de difficulté
    private String result = "";
    private double answer;
    private String equation;
    private int correctAnswersCount = 0; // Compteur des bonnes réponses

    @Autowired
    private GameResultService gameResultService;

    @GetMapping
    public String game(Model model) {
        generateQuestion();
        model.addAttribute("question", equation);
        model.addAttribute("result", result);
        return "math";
    }

    @PostMapping("/submit")
    public String submitAnswer(@RequestParam double userAnswer, 
                                @RequestParam String username, 
                                Model model) {
        // Comparaison de la réponse de l'utilisateur avec une petite tolérance pour les nombres flottants
        boolean isCorrect = Math.abs(userAnswer - answer) < 0.001;
        result = isCorrect ? "Correct!" : "Incorrect! The correct answer was " + answer;

        // Sauvegarder le résultat
        String gameResult = isCorrect ? "Win" : "Lose";
        gameResultService.saveGameResult(username, "MathGame", gameResult);

        if (isCorrect) {
            correctAnswersCount++;
            if (correctAnswersCount % 3 == 0) {
                // Augmenter la difficulté tous les 3 bonnes réponses
                level++;
            }
        } else {
            correctAnswersCount = 0; // Réinitialiser le compteur en cas de mauvaise réponse
        }

        // Générer une nouvelle question avec une difficulté accrue
        generateQuestion();
        model.addAttribute("question", equation);
        model.addAttribute("result", result);
        return "math";
    }

    private void generateQuestion() {
        // Le nombre d'opérandes augmente avec le niveau
        int numOperands = 2 + level; // Le nombre d'opérandes augmente avec le niveau
        equation = "";
        answer = 0;

        // Variables pour générer les opérandes et les opérations
        int num1 = (int) (Math.random() * 10 + 1);
        int num2 = (int) (Math.random() * 10 + 1);
        char[] operators;
        
        // Au début, on utilise seulement + et -, mais ensuite on ajoute * et /
        if (level == 1) {
            operators = new char[] {'+', '-'};
        } else if (level == 2) {
            operators = new char[] {'+', '-', '*'};
        } else {
            operators = new char[] {'+', '-', '*', '/'};
        }

        char currentOperator = operators[(int) (Math.random() * operators.length)];

        equation = num1 + " " + currentOperator + " " + num2;
        answer = calculateAnswer(num1, currentOperator, num2);

        // Ajouter plus d'opérandes et d'opérations pour augmenter la complexité
        for (int i = 1; i < numOperands; i++) {
            num1 = (int) (Math.random() * 10 + 1);
            currentOperator = operators[(int) (Math.random() * operators.length)];
            equation += " " + currentOperator + " " + num1;
            answer = calculateAnswer(answer, currentOperator, num1);
        }
    }

    private double calculateAnswer(double num1, char operator, double num2) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                return num1 / num2; // Assurez-vous que num2 ne soit pas zéro
            default:
                return 0;
        }
    }
}
