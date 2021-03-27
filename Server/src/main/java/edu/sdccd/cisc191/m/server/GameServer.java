package edu.sdccd.cisc191.m.server;


import edu.sdccd.cisc191.m.MoveRequest;
import edu.sdccd.cisc191.m.MoveResponse;

import java.net.*;
import java.io.*;

/**
 * This program is a server that takes connection requests on
 * the port specified by the constant LISTENING_PORT.  When a
 * connection is opened, the program sends the current time to
 * the connected socket.  The program will continue to receive
 * and process connections until it is killed (by a CONTROL-C,
 * for example).  Note that this server processes each connection
 * as it is received, rather than creating a separate thread
 * to process the connection.
 */
public class GameServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while (null!=(inputLine = in.readLine())) {
            System.out.println(inputLine);
            boolean legal;
            Board board = new Board(800,800);
            board.setBoard();

            MoveRequest request = MoveRequest.fromJSON(inputLine);


            int srow = request.getSrow();
            int scol = request.getScol();
            int erow = request.getErow();
            int ecol = request.getEcol();
            System.out.println(srow);
            System.out.println(scol);
            System.out.println(erow);
            System.out.println(ecol);

            if(board.getSquare(srow,scol).getPiece().getLegalMoves(board,board.getSquare(srow,scol),board.getSquare(erow,ecol))){
                legal = true;
            }else{
                legal = false;
            }




            MoveResponse response = new MoveResponse(request,legal);
            out.println(MoveResponse.toJSON(response));
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        try {
            server.start(4444);
            server.stop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} //end class Server
