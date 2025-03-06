package com.gamewerks.blocky.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPieceGenerator {
    private List<PieceKind> bag;
    private int index;
    private Random random;
    
    public RandomPieceGenerator() {
        random = new Random();
        refillBag();
    }
    
    // Refill the bag with all PieceKinds and shuffle using Fisher–Yates.
    private void refillBag() {
        bag = new ArrayList<>();
        for (PieceKind kind : PieceKind.values()) {
            bag.add(kind);
        }
        for (int i = bag.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            PieceKind temp = bag.get(i);
            bag.set(i, bag.get(j));
            bag.set(j, temp);
        }
        index = 0;
    }
    
    // Returns the next PieceKind from the bag.
    public PieceKind getNextPieceKind() {
        if (index >= bag.size()) {
            refillBag();
        }
        return bag.get(index++);
    }
}