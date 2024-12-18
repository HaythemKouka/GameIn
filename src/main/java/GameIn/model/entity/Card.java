package GameIn.model.entity;

public class Card {
    private String colorCode; // Code couleur pour la carte
    private boolean isFlipped; // Etat de la carte (retournée ou non)

    // Constructeur
    public Card(String colorCode) {
        this.colorCode = colorCode;
        this.isFlipped = false; // La carte est par défaut non retournée
    }

    // Getters et setters
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }
}
