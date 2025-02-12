/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamewerks.blocky.engine;

/**
 *
 * @author brianhe
 */
public class RandomPieceGenerator {
    private PieceKind[] bag;
    private int currentIndex;
    
    public RandomPieceGenerator() {
        bag = new PieceKind[PieceKind.ALL.length];
        System.arraycopy(PieceKind.ALL, 0, bag, 0, PieceKind.ALL.length);
        shuffleBag();
        currentIndex = 0;
    }
    
    private void shuffleBag() {
        for(int i = bag.length - 1; i > 0; i--){
            int j = (int) (Math.random() * (i + 1));
            PieceKind temp = bag[i];
            bag[i] = bag[j];
            bag[j] = temp;
        }
    }
    public PieceKind getNextPieceKind() {
        PieceKind next = bag[currentIndex];
        currentIndex++;
        if (currentIndex >= bag.length) {
            shuffleBag();
            currentIndex = 0;
        }
        return next;
    }
}
