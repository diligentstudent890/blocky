package com.gamewerks.blocky.engine;

import com.gamewerks.blocky.util.Position;

public class Piece {
    private PieceKind kind;
    private Position position;
    private int rotation;   // 0, 1, 2, or 3 representing the rotation state.
    private int[][] layout;
    
    // Constructor: takes a PieceKind and a spawn Position.
    public Piece(PieceKind kind, Position spawnPosition) {
        this.kind = kind;
        this.position = spawnPosition;
        this.rotation = 0;
        this.layout = generateLayout(kind, rotation);
    }
    
    public int[][] getLayout() {
        return layout;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void moveTo(Position newPos) {
        this.position = newPos;
    }
    
    // Rotate the piece. 'true' means clockwise; 'false' rotates counterclockwise.
    public void rotate(boolean dir) {
        if (dir) {
            rotation = (rotation + 1) % 4;
        } else {
            rotation = (rotation + 3) % 4; // Equivalent to rotating counterclockwise once.
        }
        layout = generateLayout(kind, rotation);
    }
    
    // Returns the layout (2D int array) for a given piece kind and rotation.
    private int[][] generateLayout(PieceKind kind, int rotation) {
        int[][] base;
        switch(kind) {
            case I:
                base = new int[][] {
                    {0, 0, 0, 0},
                    {1, 1, 1, 1},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
                };
                break;
            case J:
                base = new int[][] {
                    {1, 0, 0},
                    {1, 1, 1},
                    {0, 0, 0}
                };
                break;
            case L:
                base = new int[][] {
                    {0, 0, 1},
                    {1, 1, 1},
                    {0, 0, 0}
                };
                break;
            case O:
                base = new int[][] {
                    {1, 1},
                    {1, 1}
                };
                break;
            case S:
                base = new int[][] {
                    {0, 1, 1},
                    {1, 1, 0},
                    {0, 0, 0}
                };
                break;
            case T:
                base = new int[][] {
                    {0, 1, 0},
                    {1, 1, 1},
                    {0, 0, 0}
                };
                break;
            case Z:
                base = new int[][] {
                    {1, 1, 0},
                    {0, 1, 1},
                    {0, 0, 0}
                };
                break;
            default:
                base = new int[][] { {1} };
        }
        return rotateMatrix(base, rotation);
    }
    
    // Rotates the given matrix 'times' times by 90 deg clockwise.
    private int[][] rotateMatrix(int[][] matrix, int times) {
        int[][] result = matrix;
        for (int t = 0; t < times; t++) {
            result = rotateOnce(result);
        }
        return result;
    }
    
    // Rotates the matrix once (90 deg clockwise).
    private int[][] rotateOnce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] rotated = new int[n][m];
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                rotated[j][m - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }
}