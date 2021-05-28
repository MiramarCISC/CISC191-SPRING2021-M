package edu.sdccd.cisc191.m.server;

public class Blank extends Pieces {

    public Blank(boolean isWhite, boolean moved) {
        super(isWhite, moved);
    }
    public Blank(){

    }

    public String toString() {
        return "Blank";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {
        return false;
    }
}