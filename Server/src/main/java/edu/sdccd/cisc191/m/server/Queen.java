package edu.sdccd.cisc191.m.server;

public class Queen extends Pieces {
    public Queen(boolean isWhite, boolean moved){
        super(isWhite, moved);

    }
    public String toString(){
    return "Queen";
    }
    @Override
    public boolean getLegalMoves(Board board, Square start, Square end) {
return false;
    }



}
