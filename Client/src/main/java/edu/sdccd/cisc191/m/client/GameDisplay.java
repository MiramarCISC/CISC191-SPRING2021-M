package edu.sdccd.cisc191.m.client;

import edu.sdccd.cisc191.m.GameView;

public class GameDisplay {
    private GameView gameDisplay;

    public GameDisplay(){
        gameDisplay = new GameView();
    }

    public GameView getGameDisplay(){
        return gameDisplay;
    }
}
