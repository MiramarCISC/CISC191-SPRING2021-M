package edu.sdccd.cisc191.m.server;


import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;

import java.util.ArrayList;


/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: This program is a server that processes the move requests from 2 clients and determines
 * whether the move is legal or not according to the game logic of the game.
 */


public class GameServer {
    //sockets
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket clientSocket2;

    //send out to client
    private PrintWriter out;

    //read input from client
    private BufferedReader in;

    //game states in arrays
    private ArrayList<Board> boardStates = new ArrayList<>();
    private ArrayList<Square> boardPosition = new ArrayList<>();

    // input from clients
    private String inputLine;

    //legal move
    private boolean legal;

    //board
    private Board board = new Board();

    //start row col end row col
    private int srow, scol, erow, ecol;

    //whos turn it is
    private String turn = "white";

    //gameover
    private static GameServer server;

    private static MoveRequest request;

    //constructor
    public GameServer() {

    }

    // initialize color for both clients
    public void init(int port) throws Exception {

        //setting up server socket
        serverSocket = new ServerSocket(port);

        System.out.println("Waiting for connections...");
        //accepting client1 and client2
        clientSocket = serverSocket.accept();

        clientSocket2 = serverSocket.accept();

        System.out.println("We now have 2 players. No longer accepting connections.");
        //**** CLIENT 1 ****
        //outputting into client
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        //inputting to client
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //send to client about which color they are
        out.println("true");
        out.println("white");
        out = new PrintWriter(clientSocket2.getOutputStream(), true);
        System.out.println("Switched to Client 2");
        out.println("false");
        out.println("black");

        board.resetBoard();
        board.displayBoard();
        board.setList();
        boardPosition = board.getList();
    }

    //gets response from client 1
    public void get_response_c1() throws Exception {
        turn = "white";
        //output stream
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        //input stream
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //read info from input stream
        inputLine = in.readLine();
        System.out.println(inputLine);

        //requested move
        request = MoveRequest.fromJSON(inputLine);

        //store moves from input stream
        srow = request.getSrow();
        scol = request.getScol();
        erow = request.getErow();
        ecol = request.getEcol();

        System.out.println(srow);
        System.out.println(scol);
        System.out.println(erow);
        System.out.println(ecol);


    }

    //gets response from client 2
    public void get_response_c2() throws Exception {

        turn = "black";
        //output stream
        out = new PrintWriter(clientSocket2.getOutputStream(), true);

        //input stream
        in = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));

        //read info from input stream
        inputLine = in.readLine();
        System.out.println(inputLine);

        //requested move
        request = MoveRequest.fromJSON(inputLine);

        //store moves from input stream
        srow = request.getSrow();
        scol = request.getScol();
        erow = request.getErow();
        ecol = request.getEcol();

        System.out.println(srow);
        System.out.println(scol);
        System.out.println(erow);
        System.out.println(ecol);
    }

    public void send_resp(MoveRequest request)throws Exception{
        MoveResponse response = new MoveResponse(request, legal, turn);
        if (legal){
            //send to both client
            //response to the move

            //Send move to client 1
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(MoveResponse.toJSON(response));
            out = new PrintWriter(clientSocket2.getOutputStream(), true);
            out.println(MoveResponse.toJSON(response));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            checkGameIsOver();

        }else{
            //send back asking for new move
            if( turn == "white"){
                out.println(MoveResponse.toJSON(response));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            }else{
                out.println(MoveResponse.toJSON(response));
                out = new PrintWriter(clientSocket2.getOutputStream(), true);
            }
        }
    }

    public boolean check_valid_move() {

        boolean isWhite;

        if (turn == "white") {
            isWhite = true;
        } else {
            isWhite = false;
        }

        //check if the move is within the boundaries of the board and if the piece is the correct color
        if (((srow < 8 && srow > -1) && (scol < 8 && scol > -1) && (erow < 8 && erow > -1) && (ecol < 8 && ecol > -1))
                && (board.getSquare(srow, scol).getPiece().isWhite() == isWhite)) {

            System.out.println("Selected Piece: " + board.getSquare(srow, scol).getPiece().toString());

            if (board.getSquare(srow, scol).getPiece().validateMove(board, board.getSquare(srow, scol), board.getSquare(erow, ecol))
                    && (EndState.isChecked(board, turn, board.getSquare(srow, scol), board.getSquare(erow, ecol)) != true)) { //checks if move is valid
                legal = true;

                if (castlingCheck()) {
                    System.out.println("castling check good");
                } else {
                    swapPieces();
                }

            } else {
                legal = false;
                board.displayBoard();
            }
        } else {
            legal = false;
            board.displayBoard();
        }
        System.out.println(legal);
        return legal;
    }


    public void checkGameIsOver() {
        if (legal & EndState.stalemate(board, turn)) {
            if (turn.equals("black")) {
                System.out.println("White is Stalemated");

                try {
                    server.stop();
                } catch (Exception e) {

                }


            } else {
                System.out.println("Black is Stalemated");
                try {
                    server.stop();
                } catch (Exception e) {


                }

            }
        }


        if (legal && EndState.isCheckmated(board, turn)) {
            if (turn.equals("black")) {
                System.out.println("White is Checkmated");

                try {
                    server.stop();
                } catch (Exception e) {

                }

            } else {
                System.out.println("Black is Checkmated");

                try {
                    server.stop();
                } catch (Exception e) {

                }
            }
        }
    }

    public boolean castlingCheck() {
        int spIndex = 0;
        int epIndex = 0;
        int spIndexRook = 0;
        int epIndexRook = 0;
        if ((board.getSquare(srow, scol).getPiece().toString() == "King") && (board.getSquare(srow, scol).getPiece().hasMoved() == false) && (scol + 2 == ecol)) {
            //Movement for castling queen side. Swaps 2 pieces at once

            for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                    spIndex = i;
                }
                if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                    epIndex = i;
                }

            }

            for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces for rook
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol + 4)) { //gets index of Starting Piece
                    spIndexRook = i;
                }
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol + 1)) { //gets index of Ending Piece
                    epIndexRook = i;
                }

            }


            board.getSquare(srow, scol).getPiece().setMoved(true);//King

            board.getSquare(srow, (scol + 4)).getPiece().setMoved(true);//Rook

            //King
            board.getSquare(srow, scol).setRow(erow);
            board.getSquare(srow, scol).setColumn(ecol);
            board.getSquare(erow, ecol).setRow(srow);
            board.getSquare(erow, ecol).setColumn(scol);

            //Rook
            board.getSquare(srow, (scol + 4)).setColumn((scol + 1));
            board.getSquare(erow, (scol + 1)).setRow((scol + 4));

            //King
            boardPosition.get(spIndex).getPiece().setMoved(true);
            boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
            boardPosition.get(epIndex).setRow(erow);
            boardPosition.get(epIndex).setColumn(ecol);
            boardPosition.set(spIndex, new Square(srow, scol, new Blank())); // sets Starting Square to blank square
            board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList

            //Rook
            boardPosition.get(spIndexRook).getPiece().setMoved(true);
            boardPosition.set(epIndexRook, boardPosition.get(spIndexRook)); //sets Ending Square to Starting square
            boardPosition.get(epIndexRook).setRow(erow);
            boardPosition.get(epIndexRook).setColumn(scol + 1);
            boardPosition.set(spIndexRook, new Square(srow, (scol + 4), new Blank())); // sets Starting Square to blank square

            board.setBoard(boardPosition, srow, (scol + 4), erow, (scol + 1), spIndexRook, epIndexRook); //sets the position of the Pieces on the 2D Board using the LinkedList


            boardStates.add(board); //saves the current board state
            board.displayBoard();
            return true;


        } else if ((board.getSquare(srow, scol).getPiece().toString() == "King") && (board.getSquare(srow, scol).getPiece().hasMoved() == false) && (scol - 2 == ecol)) {
            //Movement for castling King side. Swaps 2 pieces at once

            for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                    spIndex = i;
                }
                if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                    epIndex = i;
                }

            }

            for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces for rook
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol - 3)) { //gets index of Starting Piece
                    spIndexRook = i;
                }
                if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == (scol - 1)) { //gets index of Ending Piece
                    epIndexRook = i;
                }

            }


            board.getSquare(srow, scol).getPiece().setMoved(true);//King

            board.getSquare(srow, (scol - 3)).getPiece().setMoved(true);//Rook

            //King
            board.getSquare(srow, scol).setRow(erow);
            board.getSquare(srow, scol).setColumn(ecol);
            board.getSquare(erow, ecol).setRow(srow);
            board.getSquare(erow, ecol).setColumn(scol);

            //Rook
            board.getSquare(srow, (scol - 3)).setColumn((scol - 1));
            board.getSquare(erow, (scol - 1)).setRow((scol - 3));

            //King
            boardPosition.get(spIndex).getPiece().setMoved(true);
            boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
            boardPosition.get(epIndex).setRow(erow);
            boardPosition.get(epIndex).setColumn(ecol);
            boardPosition.set(spIndex, new Square(srow, scol, new Blank())); // sets Starting Square to blank square
            board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList

            //Rook
            boardPosition.get(spIndexRook).getPiece().setMoved(true);
            boardPosition.set(epIndexRook, boardPosition.get(spIndexRook)); //sets Ending Square to Starting square
            boardPosition.get(epIndexRook).setRow(erow);
            boardPosition.get(epIndexRook).setColumn(scol - 1);
            boardPosition.set(spIndexRook, new Square(srow, (scol - 3), new Blank())); // sets Starting Square to blank square

            board.setBoard(boardPosition, srow, (scol - 3), erow, (scol - 1), spIndexRook, epIndexRook); //sets the position of the Pieces on the 2D Board using the LinkedList


            boardStates.add(board); //saves the current board state
            board.displayBoard();
            return true;
        }
        return false;
    }

    public void swapPieces() {
        int spIndex = 0;
        int epIndex = 0;
        for (int i = 0; i < boardPosition.size(); i++) { //swaps pieces
            if (boardPosition.get(i).getRow() == srow && boardPosition.get(i).getColumn() == scol) { //gets index of Starting Piece
                spIndex = i;
            }
            if (boardPosition.get(i).getRow() == erow && boardPosition.get(i).getColumn() == ecol) { //gets index of Ending Piece
                epIndex = i;
            }

        }

        board.getSquare(srow, scol).getPiece().setMoved(true);

        board.getSquare(srow, scol).setRow(erow);
        board.getSquare(srow, scol).setColumn(ecol);
        board.getSquare(erow, ecol).setRow(srow);
        board.getSquare(erow, ecol).setColumn(scol);
        boardPosition.get(spIndex).getPiece().setMoved(true);


        if ((erow == 0 || erow == 7) && boardPosition.get(spIndex).getPiece().toString().equals("Pawn")) { //promotion

            boardPosition.set(epIndex, new Square(erow, ecol, new Queen(boardPosition.get(spIndex).getPiece().isWhite(), true)));
            boardPosition.get(epIndex).setRow(erow);
            boardPosition.get(epIndex).setColumn(ecol);
            // sets Ending Square's piece to square with a Queen

        } else {

            boardPosition.set(epIndex, boardPosition.get(spIndex)); //sets Ending Square to Starting square
            boardPosition.get(epIndex).setRow(erow);
            boardPosition.get(epIndex).setColumn(ecol);

        }

        boardPosition.set(spIndex, new Square(srow, scol, new Blank())); //sets Starting Square to Blank Square

        board.setBoard(boardPosition, srow, scol, erow, ecol, spIndex, epIndex); //sets the position of the Pieces on the 2D Board using the LinkedList
        boardStates.add(board); //saves the current board state
        board.displayBoard();
    }


    //start server given port
    public void start(int port) throws Exception {





        /*

        //setting up server socket
        serverSocket = new ServerSocket(port);

        System.out.println("Waiting for connections...");
        //accepting client1 and client2
        clientSocket = serverSocket.accept();
        clientSocket2 = serverSocket.accept();

        System.out.println("We now have 2 players. No longer accepting connections.");

        //**** CLIENT 1 ****
        //outputting into client
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        //inputting to client
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        out.println("white");
        out = new PrintWriter(clientSocket2.getOutputStream(), true);
        out.println("black");


        init(port);


        Player player1 = new Player(true);
        Player player2 = new Player(false);
        // change turns
        Player hold = player1;

        while (true) {

            //read info from input
            inputLine = in.readLine();
            System.out.println(inputLine);

            //requested move
            MoveRequest request = MoveRequest.fromJSON(inputLine);

            //store moves
            srow = request.getSrow();
            scol = request.getScol();
            erow = request.getErow();
            ecol = request.getEcol();

            System.out.println(srow);
            System.out.println(scol);
            System.out.println(erow);
            System.out.println(ecol);

            //if white change booleans
            boolean isWhite;
            board.resetBoard();
            board.displayBoard();
            board.setList();
            boardPosition = board.getList();

            if (turn.equals("white")) {
                isWhite = true;
            } else {
                isWhite = false;
            }


            //check if the move is within the boundaries of the board and if the piece is the correct color
            if (((srow < 8 && srow > -1) && (scol < 8 && scol > -1) && (erow < 8 && erow > -1) && (ecol < 8 && ecol > -1))
                    && (board.getSquare(srow, scol).getPiece().isWhite() == isWhite)) {

                System.out.println("Selected Piece: " + board.getSquare(srow, scol).getPiece().toString());

                if (board.getSquare(srow, scol).getPiece().validateMove(board, board.getSquare(srow, scol), board.getSquare(erow, ecol))
                        && (EndState.isChecked(board, turn, board.getSquare(srow, scol), board.getSquare(erow, ecol)) != true)) { //checks if move is valid
                    int spIndex = 0;
                    int epIndex = 0;
                    legal = true;


                    if (castlingCheck()) {
                        System.out.println("castling check good");
                    } else {
                        swapPieces();

                    }

                } else {
                    legal = false;
                    board.displayBoard();
                }
            } else {

                legal = false;
                board.displayBoard();
            }

            //response to the move
            MoveResponse response = new MoveResponse(request, legal, turn);
            //Send move to client 1
            out.println(MoveResponse.toJSON(response));
            out = new PrintWriter(clientSocket2.getOutputStream(), true);
            out.println(MoveResponse.toJSON(response));
            out = new PrintWriter(clientSocket.getOutputStream(), true);


            //checks for white end game
            if (legal & EndState.stalemate(board, turn)) {
                if (turn.equals("black")) {
                    System.out.println("Black is Stalemated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }


                } else {
                    System.out.println("White is Stalemated");
                    try {
                        server.stop();
                    } catch (Exception e) {

                    }

                }
            }


            if (legal && EndState.isCheckmated(board, turn)) {
                if (turn.equals("black")) {
                    System.out.println("Black is Checkmated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }

                } else {
                    System.out.println("White is Checkmated");

                    try {
                        server.stop();
                    } catch (Exception e) {

                    }
                }
            }

        }

         */
    }



    // exceptions





    public void stop() throws IOException {

        in.close();
        out.close();
        clientSocket.close();
        clientSocket2.close();
        serverSocket.close();


    }


    public static void main(String[] args) {
        server = new GameServer();
        try {

            server.init(4444);

            while (true){
                server.get_response_c1();
                System.out.println("After getResponse");
                while(!server.check_valid_move()){
                    server.send_resp(request);
                    server.get_response_c1();
                }
                System.out.println("After Valid Move");
                server.send_resp(request);
                System.out.println("After send resp");
                server.get_response_c2();
                System.out.println("After getResponse2");
                while(!server.check_valid_move()){
                    server.send_resp(request);
                    server.get_response_c2();
                }
                System.out.println("After Valid Move2");
                server.send_resp(request);
            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}