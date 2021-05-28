package edu.sdccd.cisc191.m;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.removeMouseListener;

public class GameView {


    int srow;
    int scol;
    int erow;
    int ecol;
    JFrame frame;

    public static LinkedList<Piece> ps = new LinkedList<>();
    public static Piece selectedPiece = null;

    public GameView() throws IOException {


        BufferedImage all = ImageIO.read(new File("D:\\Users\\Aiden Wise\\IdeaProjects\\CISC191-SPRING2021-M\\chess.png"));

        Image imgs[] = new Image[12];
        int ind = 0;

        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                imgs[ind] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

        Piece wR= new Piece(0, 0, true,false, "Rook",ps);
        Piece wN = new Piece(1, 0,true, false,"Knight",ps);
        Piece wB = new Piece(2, 0,true ,false, "Bishop",ps);
        Piece wK = new Piece(3, 0, true ,false,"King",ps);
        Piece wQ= new Piece(4, 0,true, false,"Queen",ps);
        Piece wB2= new Piece(5, 0, true,false,"Bishop",ps);
        Piece wN2= new Piece(6, 0,true ,false, "Knight",ps);
        Piece wR2= new Piece(7, 0, true ,false,"Rook",ps);
        Piece wP= new Piece(0, 1, true,false, "Pawn",ps);
        Piece wP2= new Piece(1, 1, true ,false,"Pawn",ps);
        Piece wP3= new Piece(2, 1, true ,false,"Pawn",ps);
        Piece wP4= new Piece(3, 1, true ,false,"Pawn",ps);
        Piece wP5= new Piece(4, 1, true ,false,"Pawn",ps);
        Piece wp6= new Piece(5, 1, true ,false,"Pawn",ps);
        Piece wp7= new Piece(6, 1, true ,false,"Pawn",ps);
        Piece wp8= new Piece(7, 1, true ,false,"Pawn",ps);

        Piece bR= new Piece(0, 7, false,false, "Rook",ps);
        Piece bN = new Piece(1, 7,false,false, "Knight",ps);
        Piece bB = new Piece(2, 7,false ,false, "Bishop",ps);
        Piece bK = new Piece(3, 7, false ,false,"King",ps);
        Piece bQ= new Piece(4, 7,false,false, "Queen",ps);
        Piece bB2= new Piece(5, 7, false,false,"Bishop",ps);
        Piece bN2= new Piece(6, 7,false ,false, "Knight",ps);
        Piece bR2= new Piece(7, 7, false ,false,"Rook",ps);
        Piece bP= new Piece(0, 6, false,false, "Pawn",ps);
        Piece bP2= new Piece(1, 6, false ,false,"Pawn",ps);
        Piece bP3= new Piece(2, 6, false ,false,"Pawn",ps);
        Piece bP4= new Piece(3, 6, false ,false,"Pawn",ps);
        Piece bP5= new Piece(4, 6, false ,false,"Pawn",ps);
        Piece bp6= new Piece(5, 6, false ,false,"Pawn",ps);
        Piece bp7= new Piece(6, 6, false ,false,"Pawn",ps);
        Piece bp8= new Piece(7, 6, false ,false,"Pawn",ps);



        srow = -1;
        scol = -1;

        erow = -1;
        ecol = -1;


        frame = new JFrame();
        frame.setBounds(10, 10, 512, 512);
        //frame.setUndecorated(true);

        JPanel panel = new JPanel() {
            public void paint(Graphics g) {
                boolean white = true;
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (white) {
                            g.setColor(new Color(235, 235, 208));

                        } else {
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(x * 64, y * 64, 64, 64);
                        white = !white;

                    }
                    white = !white;
                }
                for (Piece p : ps) {
                    int ind = 0;
                    if (p.name.equalsIgnoreCase("king")) {
                        ind = 0;
                    }
                    if (p.name.equalsIgnoreCase("queen")) {
                        ind = 1;
                    }
                    if (p.name.equalsIgnoreCase("bishop")) {
                        ind = 2;
                    }
                    if (p.name.equalsIgnoreCase("knight")) {
                        ind = 3;
                    }
                    if (p.name.equalsIgnoreCase("rook")) {
                        ind = 4;
                    }
                    if (p.name.equalsIgnoreCase("pawn")) {
                        ind = 5;
                    }
                    if (!p.isWhite) {
                        ind += 6;

                    }
                    g.drawImage(imgs[ind], p.x, p.y, this);


                }
            }
        };





        frame.add(panel);


        frame.addMouseMotionListener(new


                                             MouseMotionListener() {
                                                 @Override
                                                 public void mouseDragged (MouseEvent e){
                                                     if(selectedPiece!=null){
                                                         selectedPiece.x = e.getX()-32;
                                                         selectedPiece.y = e.getY()-32;
                                                         frame.repaint();
                                                     }

                                                 }

                                                 @Override
                                                 public void mouseMoved (MouseEvent e){

                                                 }

                                             });





        frame.addMouseListener(new

                                       MouseListener() {




                                           @Override
                                           public void mouseClicked (MouseEvent e){

                                           }

                                           @Override
                                           public void mousePressed (MouseEvent e){

                                               selectedPiece = getPiece(e.getX(), e.getY());
                                               srow = e.getY() / 64;
                                               scol = e.getX() / 64;

                                           }

                                           @Override
                                           public void mouseReleased (MouseEvent e) {
                                               selectedPiece.move(e.getX() / 64, e.getY() / 64);
                                               erow = e.getY() / 64;
                                               ecol = e.getX() / 64;

                                           }

                                           @Override
                                           public void mouseEntered (MouseEvent e){

                                           }

                                           @Override
                                           public void mouseExited (MouseEvent e){

                                           }
                                       });




        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);



    }
    public int getSrow(){
        return srow;
    }
    public int getScol(){
        return scol;
    }
    public int getErow(){
        return srow;
    }public int getEcol(){
        return ecol;
    }


    public static Piece getPiece(int x, int y){
        //int xp = x/64;
        //int yp = y/64;
        for(Piece p: ps){
            if(p.xp ==x&& p.yp==y){
                return p;
            }
        }
        return null;
    }

    public void paint(){

        frame.repaint();

    }






    public static void main (String[]args) throws IOException {

    }





}
