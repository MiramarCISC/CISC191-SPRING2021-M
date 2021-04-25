package edu.sdccd.cisc191.m;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);


    public GameView(){
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

   private class BoardPanel extends JPanel {
        final List<TilePanel>boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < 64; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

   }

   private class TilePanel extends JPanel{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();

        }

       private void assignTileColor() {
            int counter = 0;
            for(int i = 0; i < 8; i++) {
                for (int k = counter; k < 8; k++) {
                    if (k % 2 == 0) {
                        setBackground(Color.WHITE);
                    } else {
                        setBackground(Color.BLACK);
                    }
                }
                counter++;
            }
       }

   }
}
