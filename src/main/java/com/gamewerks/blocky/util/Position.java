package com.gamewerks.blocky.util;

public class Position {
    private int row;
    private int col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    // Returns a new Position with updated row and column.
    public Position add(int deltaRow, int deltaCol) {
        return new Position(this.row + deltaRow, this.col + deltaCol);
    }
    
    // Optionally, update the position in place.
    public void set(int row, int col) {
        this.row = row;
        this.col = col;
    }
}