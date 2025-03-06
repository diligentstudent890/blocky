package com.gamewerks.blocky.engine;

import com.gamewerks.blocky.util.Constants;
import com.gamewerks.blocky.util.Position;

public class BlockyGame {
    private static final int LOCK_DELAY_LIMIT = 30;
    
    private Board board;
    private Piece activePiece;
    private Direction movement;
    
    private int lockCounter;
    
    private RandomPieceGenerator randomGenerator;
    
    public BlockyGame() {
        board = new Board();
        movement = Direction.NONE;
        lockCounter = 0;
        randomGenerator = new RandomPieceGenerator();
        trySpawnBlock();
    }
    
    // Spawns a new piece at the top center.
    private void trySpawnBlock() {
        if (activePiece == null) {
            activePiece = new Piece(randomGenerator.getNextPieceKind(),
                                new Position(0, Constants.BOARD_WIDTH / 2 - 2));
            if (board.collides(activePiece)) {
                System.exit(0);
            }
        }
    }
    
    // Processes lateral movement (left/right) based on the current movement.
    private void processMovement() {
        if (activePiece == null) return;
        Position nextPos;
        switch(movement) {
            case NONE:
                nextPos = activePiece.getPosition();
                break;
            case LEFT:
                nextPos = activePiece.getPosition().add(0, -1);
                break;
            case RIGHT:
                nextPos = activePiece.getPosition().add(0, 1);
                break;
            default:
                throw new IllegalStateException("Unrecognized direction: " + movement.name());
        }
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            activePiece.moveTo(nextPos);
        }
        // Reset movement so that lateral moves occur only once per key press.
        movement = Direction.NONE;
    }
    
    // Processes gravity: moves the active piece downward.
    private void processGravity() {
        if (activePiece == null) return;
        Position nextPos = activePiece.getPosition().add(1, 0);
        if (!board.collides(activePiece.getLayout(), nextPos)) {
            lockCounter = 0;
            activePiece.moveTo(nextPos);
        } else {
            if (lockCounter < LOCK_DELAY_LIMIT) {
                lockCounter++;
            } else {
                board.addToWell(activePiece);
                lockCounter = 0;
                activePiece = null;
            }
        }
    }
    
    // Clears any complete lines.
    private void processClearedLines() {
        board.deleteRows(board.getCompletedRows());
    }
    
    // One step of the game loop.
    public void step() {
        trySpawnBlock();
        processMovement();
        processGravity();
        processClearedLines();
    }
    
    public boolean[][] getWell() {
        return board.getWell();
    }
    
    public Piece getActivePiece() { 
        return activePiece; 
    }
    
    public void setDirection(Direction movement) { 
        this.movement = movement; 
    }
    
    public void rotatePiece(boolean dir) { 
        if(activePiece != null) {
            activePiece.rotate(dir);
        }
    }
}