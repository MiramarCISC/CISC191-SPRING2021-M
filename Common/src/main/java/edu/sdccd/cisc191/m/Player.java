package edu.sdccd.cisc191.m;

/**
 * Author(s): Aiden Wise
 * Description: Players class was initially going to be used for accounts
 * with databases however it now only determines which player is what color.
 */
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