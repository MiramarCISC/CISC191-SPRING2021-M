package edu.sdccd.cisc191.m.client;

import edu.sdccd.cisc191.m.*;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Author(s): Aiden Wise, Austin Nguyen
 * Description: Requests a move from a user which is then sent to the Server to be proccessed.
 * The server then responds telling the user if the move was valid or not.
 * Make sure you allow multiple instances of the client to run to be able to test two players!
 * Only two clients can be running at the same time
 */

public class Client {
    private Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static Scanner scnr;
    private static GameView gameView;
    private static int srow;
    private static int scol;
    private static int erow;
    private static int ecol;
    private static MoveResponse respond;


    public Client() {
        scnr = new Scanner(System.in);
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }


    public static MoveResponse sendRequest() throws Exception {


        System.out.println("Enter Starting Row:");
        srow = scnr.nextInt();
        System.out.println("Enter Starting Column:");
        scol = scnr.nextInt();
        System.out.println("Enter Ending Row:");
        erow = scnr.nextInt();
        System.out.println("Enter Ending Column:");
        ecol = scnr.nextInt();


        out.println(MoveRequest.toJSON(new MoveRequest(srow, scol, erow, ecol)));
        respond = MoveResponse.fromJSON(in.readLine());


        return respond;


    }

    public static MoveResponse getResponse() {
        return respond;
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

            gameView = new GameView();


            while (true) {

                client.sendRequest();

                System.out.println(respond.toString());


                int srow = client.getResponse().getRequest().getSrow();
                int scol = client.getResponse().getRequest().getScol();

                int erow = client.getResponse().getRequest().getErow();
                int ecol = client.getResponse().getRequest().getEcol();


                if (respond.toString().contains("is legal")) {
                    assert gameView.getPiece(scol, srow) != null;
                    if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false
                            && gameView.getPiece(scol - 3, srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol - 2) { //Paint for King side castling
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves king on gui
                        gameView.getPiece(scol - 3, srow).move(scol - 1, srow); //moves rook on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol - 1, srow).setMoved(true);

                        gameView.paint();
                    } else if (gameView.getPiece(scol, srow).getName().equalsIgnoreCase("king")
                            && gameView.getPiece(scol, srow).hasMoved() == false && gameView.getPiece(scol + 4, srow).getName().equalsIgnoreCase("rook")
                            && ecol == scol + 2) { //paint for Queen side castling
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves king on gui
                        gameView.getPiece(scol + 4, srow).move(scol + 1, srow); //moves rook on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.getPiece(scol + 1, srow).setMoved(true);

                        gameView.paint();
                    } else { //paint for every other valid move
                        gameView.getPiece(scol, srow).move(ecol, erow); //moves piece on gui
                        gameView.getPiece(ecol, erow).setMoved(true);
                        gameView.paint();
                    }


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}