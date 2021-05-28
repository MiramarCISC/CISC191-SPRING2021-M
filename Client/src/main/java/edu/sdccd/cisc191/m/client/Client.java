package edu.sdccd.cisc191.m.client;

import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This program opens a connection to a computer specified
 * as the first command-line argument.  If no command-line
 * argument is given, it prompts the user for a computer
 * to connect to.  The connection is made to
 * the port specified by LISTENING_PORT.  The program reads one
 * line of text from the connection and then closes the
 * connection.  It displays the text that it read on
 * standard output.  This program is meant to be used with
 * the server program, DateServer, which sends the current
 * date and time on the computer where the server is running.
 */

public class Client{
    private Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Scanner scnr;
    private static GameView gameView;
    static Object obj = new Object();
    private static int srow;
    private static int scol;
    private static int erow;
    private static int ecol;

    public Client() {
        scnr = new Scanner(System.in);





    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static MoveResponse sendRequest() throws Exception {
       /* Object process = new Object();
        synchronized (process){
            while((gameView.getSrow()==-1)&& gameView.getErow()==-1) {
                try {
                    process.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        process.notify();

        */


        System.out.println("Enter Starting Row:");
        srow = scnr.nextInt();
        System.out.println("Enter Starting Column:");
        scol = scnr.nextInt();
        System.out.println("Enter Ending Row:");
        erow = scnr.nextInt();
        System.out.println("Enter Ending Column:");
        ecol = scnr.nextInt();



        out.println(MoveRequest.toJSON(new MoveRequest(srow,scol,erow,ecol)));
        return MoveResponse.fromJSON(in.readLine());


    }



    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static void main(String[] args) {
        Client client = new Client();

        try {
            client.startConnection("127.0.0.1", 4444);
            String response;

            //GameDisplay board = new GameDisplay();
            //board.getGameDisplay();



            gameView = new GameView();



            while (true) {

                MoveResponse requests = client.sendRequest();

                response = requests.toString();

                System.out.println(response);
                if (response.contains("is legal")) {
                    assert gameView.getPiece(scol, srow) != null;
                    if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false
                            && gameView.getPiece(scol - 3, srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol - 2) { //Paint for King side castling
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves king
                        gameView.getPiece(scol - 3, srow).move(scol - 1, srow); //moves rook
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol - 1, srow).setMoved(true);

                        gameView.paint();
                    } else if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false && gameView.getPiece(scol +4 , srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol +2) { //paint for Queen side castling
                        gameView.getPiece(scol, srow).move(ecol, erow);
                        gameView.getPiece(scol + 4, srow).move(scol + 1, srow);
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol + 1, srow).setMoved(true);

                        gameView.paint();
                    }else{ //paint for every other valid move
                        gameView.getPiece(scol, srow).move(ecol, erow);
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.paint();
                    }
                }






            }


            //client.stopConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
} //end class Client