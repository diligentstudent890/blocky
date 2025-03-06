package com.gamewerks.blocky.gfx;

import com.gamewerks.blocky.engine.BlockyGame;
import com.gamewerks.blocky.engine.Piece;
import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BlockyPanel extends JPanel {
    
    private BlockyGame game;
    
    public BlockyPanel(BlockyGame game) {
        this.game = game;
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH * Constants.BLOCK_SIZE,
                                        Constants.BOARD_HEIGHT * Constants.BLOCK_SIZE));
        setBackground(Color.BLACK);
        // Key events are handled in the frame, so no need to add local key listeners.
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawWell(g);
        drawActivePiece(g);
    }
    
    // Draw locked blocks from the board.
    private void drawWell(Graphics g) {
        boolean[][] well = game.getWell();
        for (int i = 0; i < well.length; i++) {
            for (int j = 0; j < well[i].length; j++) {
                if (well[i][j]) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * Constants.BLOCK_SIZE, i * Constants.BLOCK_SIZE,
                               Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                }
            }
        }
    }
    
    // Draw the current falling piece.
    private void drawActivePiece(Graphics g) {
        Piece piece = game.getActivePiece();
        if (piece != null) {
            int[][] layout = piece.getLayout();
            // Use Position's row and col: row corresponds to y, col corresponds to x.
            Position pos = piece.getPosition();
            g.setColor(Color.RED);
            for (int i = 0; i < layout.length; i++) {
                for (int j = 0; j < layout[i].length; j++) {
                    if (layout[i][j] != 0) {
                        int x = (pos.getCol() + j) * Constants.BLOCK_SIZE;
                        int y = (pos.getRow() + i) * Constants.BLOCK_SIZE;
                        g.fillRect(x, y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
                    }
                }
            }
        }
    }
}