package edu.sdccd.cisc191.m.server;

import edu.sdccd.cisc191.m.Player;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: This class handles all of the games logic for chess including
 * if one's king is checked, one is checkmated, or they are stalemated.
 */

public class EndState {


    public EndState() {

    }

    //check if a player's next move results in their king being checked
    public static boolean isChecked(Board board, String turn, Square start, Square end) {
        boolean check = false;
        Square temp = null;


        Board board2 = board;
        if (start.getPiece().validateMove(board, start, end) && end.getPiece().toString() != "Blank") {//Checks if the initial move is valid
            Square tempPiece = new Square(end.getRow(), end.getColumn(), end.getPiece());


            board2.swapSquares(start, end); //Move the pieces on alternate board


            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if ("King" == board2.getSquare(r, c).getPiece().toString() && turn=="white" != board2.getSquare(r, c).getPiece().isWhite()) {
                        temp = board2.getSquare(r, c);


                    }
                }
            }

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    if (board2.getSquare(r, c).getPiece().isWhite() != temp.getPiece().isWhite()
                            && board2.getSquare(r, c).getPiece().toString() != "Blank"
                            && board2.getSquare(r, c).getPiece().toString() != "King"
                            && board2.getSquare(r, c).getPiece().validateMove(board2, board2.getSquare(r, c), temp)) {
                        //check if move results in King being checked on alternate board

                        check = true;

                    }

                }


            }


            board2.swapSquares(end, start); //swaps pieces back to original position

            board2.setSquare(end, tempPiece); //set square with the orginal piece


        } else if (start.getPiece().validateMove(board, start, end) && end.getPiece().toString() == "Blank") {

            board2.swapSquares(start, end); //Move the pieces on alternate board

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if ("King" == board2.getSquare(r, c).getPiece().toString() && turn=="white" != board2.getSquare(r, c).getPiece().isWhite()) {
                        temp = board2.getSquare(r, c);

                    }
                }
            }

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    if (board2.getSquare(r, c).getPiece().isWhite() != temp.getPiece().isWhite()
                            && board2.getSquare(r, c).getPiece().toString() != "Blank"
                            && board2.getSquare(r, c).getPiece().toString() != "King"
                            && board2.getSquare(r, c).getPiece().validateMove(board2, board2.getSquare(r, c), temp)) {
                        //check if move results in King being checked on alternate board

                        check = true;

                    }
                }


            }


            board2.swapSquares(end, start); //swaps pieces back to original position

        }

        return check;
    }

    //Check if the king has been checkmated by searching for all the valid moves. If there are no valid moves, then
    //it is a checkmate
    public static boolean isCheckmated(Board board, String turn) {

        Square king = null;
        boolean checkmate = false;


        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ("King" == board.getSquare(r, c).getPiece().toString() && turn=="white" == board.getSquare(r, c).getPiece().isWhite()) {
                    king = board.getSquare(r, c);

                }
            }
        }

        //Validating all moves for every piece
        //board.getSquare(r,c) is the selected Piece
        //board.getSquare(i,j) is ending square for the selected piece

        String otherTurn = "black";

        if (turn=="black") {
            otherTurn = "white";
        } else {
            otherTurn = "black";
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        if (board.getSquare(r, c).getPiece().toString() != "Blank" && board.getSquare(r, c).getPiece().toString() != "King"
                                && board.getSquare(r, c).getPiece().isWhite() == king.getPiece().isWhite()) {

                            if (board.getSquare(r, c).getPiece().validateMove(board, board.getSquare(r, c), board.getSquare(i, j))) {

                                //Checks if the move does not result in the piece being checked
                                if (isChecked(board, otherTurn, board.getSquare(r, c), board.getSquare(i, j)) == false) {

                                    return false;

                                }


                            }

                        }


                    }
                }
            }
        }

        return true;


    }

    //Checks if the king is not checked and player has no valid moves
    public static boolean stalemate(Board board, String turn) {

        Square king = null;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ("King" == board.getSquare(r, c).getPiece().toString() && turn == "white" != board.getSquare(r, c).getPiece().isWhite()) {
                    king = board.getSquare(r, c);

                }
            }
        }


        String otherTurn = "black";

        if (turn=="black") {
            otherTurn = "white";
        } else {
            otherTurn = "black";
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        if (board.getSquare(r, c).getPiece().toString() != "Blank" && board.getSquare(r, c).getPiece().toString() != "King"
                                && board.getSquare(r, c).getPiece().isWhite() == king.getPiece().isWhite()) {

                            if (board.getSquare(r, c).getPiece().validateMove(board, board.getSquare(r, c), board.getSquare(i, j)) == true) {

                                return false;

                            }
                        }


                    }
                }
            }
        }

        return true;
    }


}