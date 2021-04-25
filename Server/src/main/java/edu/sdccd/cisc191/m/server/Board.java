package edu.sdccd.cisc191.m.server;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Board extends JFrame implements Comparable<Square> {
    private final int width;
    private final int height;
    private final Container contentPane;
    private final JTextArea message;
    private final Square[][] board;
    private final LinkedList<Square>boardPosition = new LinkedList<Square>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        contentPane = this.getContentPane();
        message = new JTextArea();
        board = new Square[8][8];
    }


    public void resetBoard() {

        board[0][0] = new Square(0, 0, new Rook(true, false));
        board[0][1] = new Square(0, 1, new Knight(true, false));
        board[0][2] = new Square(0, 2, new Bishop(true, false));
        board[0][3] = new Square(0, 3, new King(true, false));
        board[0][4] = new Square(0, 4, new Queen(true, false));
        board[0][5] = new Square(0, 5, new Bishop(true, false));
        board[0][6] = new Square(0, 6, new Knight(true, false));
        board[0][7] = new Square(0, 7, new Rook(true, false));
        board[1][0] = new Square(1, 0, new Blank());
        board[1][1] = new Square(1, 1, new Blank());
        board[1][2] = new Square(1, 2, new Blank());
        board[1][3] = new Square(1, 3, new Blank());
        board[1][4] = new Square(1, 4, new Blank());
        board[1][5] = new Square(1, 5, new Blank());
        board[1][6] = new Square(1, 6, new Blank());
        board[1][7] = new Square(1, 7, new Blank());

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
        board[6][0] = new Square(6, 0, new Blank());
        board[6][1] = new Square(6, 1, new Blank());
        board[6][2] = new Square(6, 2, new Blank());
        board[6][3] = new Square(6, 3, new Blank());
        board[6][4] = new Square(6, 4, new Blank());
        board[6][5] = new Square(6, 5, new Blank());
        board[6][6] = new Square(6, 6, new Blank());
        board[6][7] = new Square(6, 7, new Blank());




    }
    public void setBoard(LinkedList<Square>bp, int srow, int scol, int erow, int ecol, int spIndex, int epIndex){
        board[srow][scol] = bp.get(spIndex);
        board[erow][ecol]= bp.get(epIndex);

    }
    public void setList(){
        for(int r = 0; r < board.length;r++){
            for(int c = 0; c< board[r].length;c++){
                boardPosition.add(board[r][c]);
            }
        }
    }
    public LinkedList<Square> getList(){
        return boardPosition;
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
            System.out.printf("%-16s at %d, %d   " ,"This is a "+ board[i][j].getPiece(),(i),(j));

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
        b.resetBoard();
        //b.setUpGUI();
        b.displayBoard();

        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(5,0)));//expect can move
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(6,0)));//expect attack
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(7,0)));//expect attack
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(0,1)));//expect break: allied piece
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(0,3)));//expect false
        System.out.println();
        System.out.println(b.getSquare(0,0).getPiece().validateMove(b,b.getSquare(0,0),b.getSquare(3,3)));//expect column mismatch

    }

    @Override
    public int compareTo(Square o) {
        return 0;
    }
}
