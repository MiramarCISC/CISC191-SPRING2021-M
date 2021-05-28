package edu.sdccd.cisc191.m;

public class Player {
    boolean isWhite;

    public Player() {
    }

    public Player(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

}