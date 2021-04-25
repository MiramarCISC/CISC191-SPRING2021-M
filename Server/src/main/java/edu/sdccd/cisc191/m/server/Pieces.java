package edu.sdccd.cisc191.m.server;

public abstract class Pieces {
    protected boolean white;
    protected boolean moved;

    public Pieces(boolean white, boolean moved) {
        this.white = white;
        this.moved = moved;
    }
    public Pieces(){

    }

    public abstract boolean validateMove(Board board, Square start, Square end);

    public abstract String toString();


    public boolean hasMoved() {
        return moved;

    }

    public boolean isWhite() {
        return white;
    }

    public void deletePiece() {

    }
}
