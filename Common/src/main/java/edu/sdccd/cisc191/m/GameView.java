package edu.sdccd.cisc191.m;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Author(s): Austin Nguyen
 * Description: The Graphic User Interface (GUI) of the Chess game. This class paints the board and all the pieces
 * in a frame.
 * Ensure that the correct file path is inputted in line 27 for the chess.png
 */

public class GameView {

    JFrame frame;

    public static LinkedList<GuiPiece> ps = new LinkedList<>();

    public GameView() throws IOException {

        BufferedImage all = ImageIO.read(new File("C:\\Users\\Aiden Wise\\Documents\\GitHub\\Online-Chess-Code\\chess.png"));

        Image imgs[] = new Image[12];
        int ind = 0;

        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                imgs[ind] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }

        //paints all the pieces on the GUI Chess Board
        GuiPiece wR = new GuiPiece(0, 0, true, false, "Rook", ps);
        GuiPiece wN = new GuiPiece(1, 0, true, false, "Knight", ps);
        GuiPiece wB = new GuiPiece(2, 0, true, false, "Bishop", ps);
        GuiPiece wK = new GuiPiece(3, 0, true, false, "King", ps);
        GuiPiece wQ = new GuiPiece(4, 0, true, false, "Queen", ps);
        GuiPiece wB2 = new GuiPiece(5, 0, true, false, "Bishop", ps);
        GuiPiece wN2 = new GuiPiece(6, 0, true, false, "Knight", ps);
        GuiPiece wR2 = new GuiPiece(7, 0, true, false, "Rook", ps);
        GuiPiece wP = new GuiPiece(0, 1, true, false, "Pawn", ps);
        GuiPiece wP2 = new GuiPiece(1, 1, true, false, "Pawn", ps);
        GuiPiece wP3 = new GuiPiece(2, 1, true, false, "Pawn", ps);
        GuiPiece wP4 = new GuiPiece(3, 1, true, false, "Pawn", ps);
        GuiPiece wP5 = new GuiPiece(4, 1, true, false, "Pawn", ps);
        GuiPiece wp6 = new GuiPiece(5, 1, true, false, "Pawn", ps);
        GuiPiece wp7 = new GuiPiece(6, 1, true, false, "Pawn", ps);
        GuiPiece wp8 = new GuiPiece(7, 1, true, false, "Pawn", ps);

        GuiPiece bR = new GuiPiece(0, 7, false, false, "Rook", ps);
        GuiPiece bN = new GuiPiece(1, 7, false, false, "Knight", ps);
        GuiPiece bB = new GuiPiece(2, 7, false, false, "Bishop", ps);
        GuiPiece bK = new GuiPiece(3, 7, false, false, "King", ps);
        GuiPiece bQ = new GuiPiece(4, 7, false, false, "Queen", ps);
        GuiPiece bB2 = new GuiPiece(5, 7, false, false, "Bishop", ps);
        GuiPiece bN2 = new GuiPiece(6, 7, false, false, "Knight", ps);
        GuiPiece bR2 = new GuiPiece(7, 7, false, false, "Rook", ps);
        GuiPiece bP = new GuiPiece(0, 6, false, false, "Pawn", ps);
        GuiPiece bP2 = new GuiPiece(1, 6, false, false, "Pawn", ps);
        GuiPiece bP3 = new GuiPiece(2, 6, false, false, "Pawn", ps);
        GuiPiece bP4 = new GuiPiece(3, 6, false, false, "Pawn", ps);
        GuiPiece bP5 = new GuiPiece(4, 6, false, false, "Pawn", ps);
        GuiPiece bp6 = new GuiPiece(5, 6, false, false, "Pawn", ps);
        GuiPiece bp7 = new GuiPiece(6, 6, false, false, "Pawn", ps);
        GuiPiece bp8 = new GuiPiece(7, 6, false, false, "Pawn", ps);


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
                for (GuiPiece p : ps) {
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

        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);


    }


    public static GuiPiece getPiece(int x, int y) {

        for (GuiPiece p : ps) {
            if (p.xp == x && p.yp == y) {
                return p;
            }
        }
        return null;
    }

    public void paint() {

        frame.repaint();

    }


}
