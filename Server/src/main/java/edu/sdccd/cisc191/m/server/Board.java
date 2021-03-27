package edu.sdccd.cisc191.m.server;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    private final int width;
    private final int height;
    private final Container contentPane;
    private final JTextArea message;
    private final Square[][] board;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        contentPane = this.getContentPane();
        message = new JTextArea();
        board = new Square[8][8];
    }


    public void setBoard() {

        board[0][0] = new Square(0, 0, new Rook(true, false));
        board[0][1] = new Square(0, 1, new Knight(true, false));
        board[0][2] = new Square(0, 2, new Bishop(true, false));
        board[0][3] = new Square(0, 3, new King(true, false));
        board[0][4] = new Square(0, 4, new Queen(true, false));
        board[0][5] = new Square(0, 5, new Bishop(true, false));
        board[0][6] = new Square(0, 6, new Knight(true, false));
        board[0][7] = new Square(0, 7, new Rook(true, false));
        board[1][0] = new Square(1, 0, new Blank(true, false));
        board[1][1] = new Square(1, 1, new Pawn(true, false));
        board[1][2] = new Square(1, 2, new Pawn(true, false));
        board[1][3] = new Square(1, 3, new Pawn(true, false));
        board[1][4] = new Square(1, 4, new Pawn(true, false));
        board[1][5] = new Square(1, 5, new Pawn(true, false));
        board[1][6] = new Square(1, 6, new Pawn(true, false));
        board[1][7] = new Square(1, 7, new Pawn(true, false));

        for (int r = 2; r < 6; r++) {
            for (int c = 0; c < 8; c++) {
                board[r][c] = new Square(r, c, new Blank());
            }
        }

        board[7][0] = new Square(7, 0, new Rook(false, false));
        board[7][1] = new Square(7, 1, new Knight(false, false));
        board[7][2] = new Square(7, 2, new Bishop(false, false));
        board[7][3] = new Square(7, 3, new King(false, false));
        board[7][4] = new Square(7, 4, new Queen(false, false));
        board[7][5] = new Square(7, 5, new Bishop(false, false));
        board[7][6] = new Square(7, 6, new Knight(false, false));
        board[7][7] = new Square(7, 7, new Rook(false, false));
        board[6][0] = new Square(6, 0, new Pawn(false, false));
        board[6][1] = new Square(6, 1, new Pawn(false, false));
        board[6][2] = new Square(6, 2, new Pawn(false, false));
        board[6][3] = new Square(6, 3, new Pawn(false, false));
        board[6][4] = new Square(6, 4, new Pawn(false, false));
        board[6][5] = new Square(6, 5, new Pawn(false, false));
        board[6][6] = new Square(6, 6, new Pawn(false, false));
        board[6][7] = new Square(6, 7, new Pawn(false, false));


    }

    public Square getSquare(int row, int column) {
        return board[row][column];
    }

    public Square[][] getBoard() {
        return board;
    }

    public void displayBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < (board[0].length); j++) {


                if (("Rook").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a rook at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");


                } else if (("Knight").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a Knight at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");


                } else if (("Pawn").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a Pawn at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");


                } else if (("Bishop").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a Bishop at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");

                } else if (("Queen").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a Queen at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");


                } else if (("King").equals(board[i][j].getPiece().toString())) {
                    System.out.print("this is a King at " + (i + 1) + "," + (j + 1) + " + " + board[i][j].getPiece() + "    ");


                } else {

                    System.out.print("This is a blank square at " + (i + 1) + "," + (j + 1) + "    ");

                }


            }
            System.out.println();

        }
        System.out.println();
        System.out.println();
    }

    public void setUpGUI() {
        this.setSize(width, height);
        this.setTitle("Chess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(8, 8));
        contentPane.add(message);
        message.setText("Creating Chess in Java");
        message.setWrapStyleWord(true);
        message.setLineWrap(true);
        message.setEditable(false);
        this.setVisible(true);


    }

    public static void main(String[] args) {
        Board b = new Board(800, 800);
        b.setBoard();
        //b.setUpGUI();
        b.displayBoard();

        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(5,0)));//expect can move
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(6,0)));//expect attack
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(7,0)));//expect attack
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(0,1)));//expect break: allied piece
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(0,3)));//expect false
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().getLegalMoves(b,b.getSquare(0,0),b.getSquare(3,3)));//expect column mismatch

    }

}
