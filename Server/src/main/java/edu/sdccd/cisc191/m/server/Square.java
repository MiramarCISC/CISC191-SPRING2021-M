package edu.sdccd.cisc191.m.server;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: The blocks that make up the board. Each square contains a piece and
 * the pieces location on the board.
 */


public class Square {
    private int row;
    private int column;
    private Pieces piece;

    public Square() {

    }

    public Square(int row, int column, Pieces piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Pieces getPiece() {
        return piece;
    }

    public void setPiece(Pieces piece) {
        this.piece = piece;
    }
}