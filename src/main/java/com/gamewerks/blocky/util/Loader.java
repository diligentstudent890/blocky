package com.gamewerks.blocky.util;

import com.gamewerks.blocky.engine.BlockyGame;
import com.gamewerks.blocky.gfx.BlockyPanel;
import java.awt.Rectangle;
import javax.swing.JFrame;

public class Loader {
    private static final int FPS = 10;
    private static final double SPF = 1000000000.0 / FPS;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Blocky Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BlockyGame game = new BlockyGame();
        BlockyPanel panel = new BlockyPanel(game);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Main game loop (runs on the main thread)
        long timeElapsed = 0;
        long prevTime = System.nanoTime();
        while (true) {
            long currentTime = System.nanoTime();
            timeElapsed += currentTime - prevTime;
            prevTime = currentTime;
            if (timeElapsed > SPF) {
                game.step();
                panel.paintImmediately(new Rectangle(0, 0, panel.getWidth(), panel.getHeight()));
                timeElapsed -= SPF;
            }
        }
    }
}