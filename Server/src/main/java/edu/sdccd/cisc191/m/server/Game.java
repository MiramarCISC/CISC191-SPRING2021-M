package edu.sdccd.cisc191.m.server;

import edu.sdccd.cisc191.m.Player;

public class Game {
    private Player player1;
    private Player player2;
    private Board board;

    public Game(){
        player1 = new Player(true);
        player2 = new Player(false);
    }
    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }


    public boolean isChecked(Board board, Player player) {
        Square temp = null;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ("King" == board.getSquare(r, c).getPiece().toString() && player.isWhite() == board.getSquare(r, c).getPiece().isWhite()) {
                    temp = board.getSquare(r,c);
                    System.out.println(temp.getPiece().toString());
                    System.out.println("Row: " + temp.getRow());
                    System.out.println("Column: " + temp.getColumn());

                }
            }
        }
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((board.getSquare(r, c).toString() != "Blank" || board.getSquare(r, c).toString() != "King")
                        && board.getSquare(r, c).getPiece().isWhite() != temp.getPiece().isWhite()) {
                    if (board.getSquare(r, c).getPiece().validateMove(board, board.getSquare(r, c), temp)) {
                        System.out.println(board.getSquare(r,c).getPiece().toString());
                        System.out.println("King is Checked");
                        System.out.println(board.getSquare(r,c).getPiece().isWhite());
                        return true;
                    }

                }
            }

        }
        return false;
    }

    public boolean isCheckmated() {
        return false;
    }

    public boolean isDraw() {
        return false;
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
