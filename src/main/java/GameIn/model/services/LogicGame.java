package GameIn.model.services;

public class LogicGame {
    public static int PLAY(){
        int range = 12;
        int min = 1;
        return (int)(Math.random() * range) + min;
    }
}
