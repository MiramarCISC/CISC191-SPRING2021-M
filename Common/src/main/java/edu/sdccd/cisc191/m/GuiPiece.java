package edu.sdccd.cisc191.m;

import java.util.LinkedList;

/**
 * Author(s): Austin Nguyen
 * Description: This class makes up the GUI for the all the pieces on the board. It also allows the pieces to
 * move on the GUI board.
 */

public class GuiPiece {
    int xp;
    int yp;

    int x;
    int y;
    boolean isWhite;
    LinkedList<GuiPiece> ps;
    String name;

    boolean moved;


    public GuiPiece(int xp, int yp, boolean isWhite, boolean moved, String name, LinkedList<GuiPiece> ps) {
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
        this.isWhite = isWhite;
        this.ps = ps;
        this.name = name;
        this.moved = moved;
        ps.add(this);


    }

    public void move(int xp, int yp) {
        if (GameView.getPiece(xp, yp) != null) {
            if (GameView.getPiece(xp, yp).isWhite != isWhite) {
                GameView.getPiece(xp, yp).kill();

            } else {
                x = this.xp * 64;
                y = this.yp * 64;
                return;
            }

        }
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
    }


    public void kill() {
        ps.remove(this);
    }

    public boolean hasMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
