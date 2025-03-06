package com.gamewerks.blocky.engine;

import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int[][] grid;
    
    public Board() {
        grid = new int[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
    }
    
    // Checks collision.
    public boolean collides(Piece piece) {
        return collides(piece.getLayout(), piece.getPosition());
    }
    
    // Checks collision for a given layout placed at the given position.
    public boolean collides(int[][] layout, Position pos) {
        int rows = layout.length;
        int cols = layout[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (layout[i][j] != 0) {
                    int boardRow = pos.getRow() + i;
                    int boardCol = pos.getCol() + j;
                    // Check board boundaries.
                    if (boardRow < 0 || boardRow >= Constants.BOARD_HEIGHT || 
                        boardCol < 0 || boardCol >= Constants.BOARD_WIDTH) {
                        return true;
                    }
                    // Check if cell is already occupied.
                    if (grid[boardRow][boardCol] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // Adds the piece's layout into the grid (locking it in place).
    public void addToWell(Piece piece) {
        int[][] layout = piece.getLayout();
        Position pos = piece.getPosition();
        int rows = layout.length;
        int cols = layout[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (layout[i][j] != 0) {
                    int boardRow = pos.getRow() + i;
                    int boardCol = pos.getCol() + j;
                    if (boardRow >= 0 && boardRow < Constants.BOARD_HEIGHT &&
                        boardCol >= 0 && boardCol < Constants.BOARD_WIDTH) {
                        grid[boardRow][boardCol] = 1;
                    }
                }
            }
        }
    }
    
    // Returns an array of row indices that are completely filled.
    public int[] getCompletedRows() {
        List<Integer> completeRows = new ArrayList<>();
        for (int i = 0; i < Constants.BOARD_HEIGHT; i++) {
            boolean complete = true;
            for (int j = 0; j < Constants.BOARD_WIDTH; j++) {
                if (grid[i][j] == 0) {
                    complete = false;
                    break;
                }
            }
            if (complete) {
                completeRows.add(i);
            }
        }
        int[] rows = new int[completeRows.size()];
        for (int i = 0; i < completeRows.size(); i++) {
            rows[i] = completeRows.get(i);
        }
        return rows;
    }
    
    // Deletes the given rows and shifts the above rows downward.
    public void deleteRows(int[] rowsToDelete) {
        for (int row : rowsToDelete) {
            for (int i = row; i > 0; i--) {
                grid[i] = grid[i - 1].clone();
            }
            grid[0] = new int[Constants.BOARD_WIDTH];
        }
    }
    
    // Returns the current well as a boolean grid for rendering.
    public boolean[][] getWell() {
        boolean[][] well = new boolean[Constants.BOARD_HEIGHT][Constants.BOARD_WIDTH];
        for (int i = 0; i < Constants.BOARD_HEIGHT; i++) {
            for (int j = 0; j < Constants.BOARD_WIDTH; j++) {
                well[i][j] = (grid[i][j] != 0);
            }
        }
        return well;
    }
}