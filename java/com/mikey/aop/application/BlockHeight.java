package com.mikey.aop.application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This creates the block height with a property that can be bound.
 * @author Michael Nicholson
 */
public class BlockHeight {
    private IntegerProperty blockHeight;


    /**
     * Getter for the block height.
     * @return The representative height of the block that this object represents.
     */
    public int getBlockHeight() {
        return blockHeight.get();
    }

    /**
     * Setter for the block height.
     * @param blockHeight The new representative height of the block that this object represents.
     */
    public void setBlockHeight(int blockHeight) {
        this.blockHeight.set(blockHeight);
    }

    /**
     * This is a getter for the property object that represents the block height.
     * @return The property that can be bound.
     */
    public IntegerProperty blockHeightProperty(){
        return blockHeight;
    }

    /**
     * This is the sole constructor for this class
     * @param blockHeight This is the initial height of the block that this object represents.
     */
    public BlockHeight(Integer blockHeight) {
        this.blockHeight = new SimpleIntegerProperty(blockHeight);
    }
}
