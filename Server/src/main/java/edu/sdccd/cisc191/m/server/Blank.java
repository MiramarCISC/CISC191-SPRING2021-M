package edu.sdccd.cisc191.m.server;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: Used to fill the board with pieces that don't do anything
 * on spaces that don't need to do anything
 */
public class Blank extends Pieces {


    public Blank() {

    }

    public String toString() {
        return "Blank";
    }

    @Override
    public boolean validateMove(Board board, Square start, Square end) {
        return false;
    }
}