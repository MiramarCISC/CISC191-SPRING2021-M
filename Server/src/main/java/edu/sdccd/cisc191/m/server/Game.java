package edu.sdccd.cisc191.m.server;

import edu.sdccd.cisc191.m.Player;

import java.util.ArrayList;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;
    private ArrayList<Square> blackPiecesAttackingKing = new ArrayList<Square>();
    private ArrayList<Square> whitePiecesAttackingKing = new ArrayList<Square>();


    public Game() {
        player1 = new Player(true);
        player2 = new Player(false);


        // blackPiecesAttackingKing.add(new Square(-1, -1, new Blank()));
        // whitePiecesAttackingKing.add(new Square(-1, -1, new Blank()));
    }

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        //blackPiecesAttackingKing.add(new Square(-1, -1, new Blank()));
        //whitePiecesAttackingKing.add(new Square(-1, -1, new Blank()));
    }


    public boolean isChecked(Board board, Player player, Square start, Square end) {
        boolean check = false;
        Square temp = null;


        Board board2 = board;
        if (start.getPiece().validateMove(board, start, end) && end.getPiece().toString() != "Blank") {//Checks if the initial move is valid
            Square tempPiece = new Square(end.getRow(), end.getColumn(), end.getPiece());
            //board2.displayBoard();

            board2.swapSquares(start, end); //Move the pieces on alternate board
            //board2.displayBoard();


            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if ("King" == board2.getSquare(r, c).getPiece().toString() && player.isWhite() != board2.getSquare(r, c).getPiece().isWhite()) {
                        temp = board2.getSquare(r, c);
                        System.out.println(temp.getPiece().toString());
                        System.out.println("Row: " + temp.getRow());
                        System.out.println("Column: " + temp.getColumn());


                    }
                }
            }

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    //System.out.println("This is Board2");
                    // board2.displayBoard();



                    /*
                    System.out.println(board2.getSquare(5,2).getPiece().toString() +" " +
                            board2.getSquare(5,2).getRow() + "," + board2.getSquare(5,2).getColumn());
                    System.out.println(board2.getSquare(6,2).getPiece().toString() +" " +
                            board2.getSquare(6,2).getRow() + "," + board2.getSquare(6,2).getColumn());



                    System.out.println("Variable r: " + r + " Variable c: " + c);
                    System.out.println("Attacking Piece at " + board2.getSquare(r, c).getRow() + ", " + board2.getSquare(r, c).getColumn() + ": "

                            + board2.getSquare(r, c).getPiece().toString());

                     */


                    if (board2.getSquare(r, c).getPiece().isWhite() != temp.getPiece().isWhite()
                            && board2.getSquare(r, c).getPiece().toString() != "Blank"
                            && board2.getSquare(r, c).getPiece().toString() != "King"
                            && board2.getSquare(r, c).getPiece().validateMove(board2, board2.getSquare(r, c), temp)) {
                        //check if move results in King being checked on alternate board

                        System.out.println("Move results in King being Checked");

                        check = true;

                    }

                }


            }


            board2.swapSquares(end, start); //swaps pieces back to original position

            board2.setSquare(end, tempPiece);


        } else if (start.getPiece().validateMove(board, start, end) && end.getPiece().toString() == "Blank") {

            board2.swapSquares(start, end); //Move the pieces on alternate board

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if ("King" == board2.getSquare(r, c).getPiece().toString() && player.isWhite() != board2.getSquare(r, c).getPiece().isWhite()) {
                        temp = board2.getSquare(r, c);
                        System.out.println(temp.getPiece().toString());
                        System.out.println("Row: " + temp.getRow());
                        System.out.println("Column: " + temp.getColumn());

                    }
                }
            }

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {

                    // System.out.println("This is Board2");
                    // board2.displayBoard();



                    /*
                    System.out.println(board2.getSquare(5,2).getPiece().toString() +" " +
                            board2.getSquare(5,2).getRow() + "," + board2.getSquare(5,2).getColumn());
                    System.out.println(board2.getSquare(6,2).getPiece().toString() +" " +
                            board2.getSquare(6,2).getRow() + "," + board2.getSquare(6,2).getColumn());
                    */


                   /* System.out.println("Variable r: " + r + " Variable c: " + c);
                    System.out.println("Attacking Piece at " + board2.getSquare(r, c).getRow() + ", " + board2.getSquare(r, c).getColumn() + ": "

                            + board2.getSquare(r, c).getPiece().toString());

                    */


                    if (board2.getSquare(r, c).getPiece().isWhite() != temp.getPiece().isWhite()
                            && board2.getSquare(r, c).getPiece().toString() != "Blank"
                            && board2.getSquare(r, c).getPiece().toString() != "King"
                            && board2.getSquare(r, c).getPiece().validateMove(board2, board2.getSquare(r, c), temp)) {
                        //check if move results in King being checked on alternate board
                        System.out.println("Move results in King being Checked");

                        check = true;

                    }
                }


            }


            board2.swapSquares(end, start); //swaps pieces back to original position

        }
        System.out.println("Check: " + check);
        return check;
    }


    public boolean isCheckmated(Board board, Player player) {

        Square king = null;
        boolean checkmate = false;


        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ("King" == board.getSquare(r, c).getPiece().toString() && player.isWhite() == board.getSquare(r, c).getPiece().isWhite()) {
                    king = board.getSquare(r, c);
                    System.out.println(king.getPiece().toString());
                    System.out.println("Row: " + king.getRow());
                    System.out.println("Column: " + king.getColumn());

                }
            }
        }

        //Validating all moves for every piece
        //board.getSquare(r,c) is the selected Piece
        //board.getSquare(i,j) is ending square for the selected piece
        Player tempP = new Player();

        if(player.isWhite()==false){
            tempP.setWhite(true);
        }else {
            tempP.setWhite(false);
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        if (board.getSquare(r, c).getPiece().toString() != "Blank" && board.getSquare(r, c).getPiece().toString() != "King"
                                && board.getSquare(r, c).getPiece().isWhite() == king.getPiece().isWhite()) {

                            if (board.getSquare(r, c).getPiece().validateMove(board, board.getSquare(r, c), board.getSquare(i, j))) {

                                if (isChecked(board, tempP, board.getSquare(r, c), board.getSquare(i, j)) == false) {

                                    System.out.println(king.getPiece().isWhite());
                                    System.out.println("King r,c : " + king.getRow() + "," + king.getColumn());


                                    System.out.println(board.getSquare(r, c).getPiece().toString());
                                    System.out.println(board.getSquare(r,c).getPiece().isWhite());
                                    System.out.println(r + "," + c);

                                    System.out.println(board.getSquare(i, j).getPiece().toString());
                                    System.out.println(board.getSquare(i,j).getPiece().isWhite());
                                    System.out.println(i + "," + j);

                                    return false;


                                }


                            }

                        }


                    }
                }
            }
        }
        System.out.println(king.getPiece().isWhite());
        System.out.println("King r,c : " + king.getRow() + "," + king.getColumn());

        return true;


    }

    public boolean stalemate(Board board, Player player){

        Square king = null;
        boolean validMoves = false;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ("King" == board.getSquare(r, c).getPiece().toString() && player.isWhite() != board.getSquare(r, c).getPiece().isWhite()) {
                    king = board.getSquare(r, c);
                    System.out.println(king.getPiece().toString());
                    System.out.println("Row: " + king.getRow());
                    System.out.println("Column: " + king.getColumn());

                }
            }
        }


        Player tempP = new Player();

        if(player.isWhite()==false){
            tempP.setWhite(true);
        }else {
            tempP.setWhite(false);
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        if (board.getSquare(r, c).getPiece().toString() != "Blank" && board.getSquare(r, c).getPiece().toString() != "King"
                                && board.getSquare(r, c).getPiece().isWhite() == king.getPiece().isWhite()) {

                            if (board.getSquare(r, c).getPiece().validateMove(board, board.getSquare(r, c), board.getSquare(i, j))==true) {

                                System.out.println(board.getSquare(r,c).getPiece());
                                System.out.println(r+","+c);

                                System.out.println(board.getSquare(i,j).getPiece());
                                System.out.println(i+","+j);

                                return false;

                            }


                        }


                    }
                }
            }
        }


        return true;
    }


    public boolean isDraw() {
        return false;
    }

    public void clear() {
        for (int i = 0; i < blackPiecesAttackingKing.size(); i++) {
            blackPiecesAttackingKing.remove(i);
        }
        for (int i = 0; i < whitePiecesAttackingKing.size(); i++) {
            whitePiecesAttackingKing.remove(i);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }


    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}