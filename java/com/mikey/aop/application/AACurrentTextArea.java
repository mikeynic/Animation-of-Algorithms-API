package com.mikey.aop.application;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.text.Font;

/**
 * AACurrentTextArea inherits the TextField class.
 * Allows the text area displayed on the AnimationApplication to be written to
 * @author Michael Nicholson
 */
public class AACurrentTextArea extends JFXTextField {

    private static AACurrentTextArea instance;

    /**
     * Enforces the singleton design pattern.
     * @return The singleton instance of the AACurrentTextArea.
     */
    public static synchronized AACurrentTextArea getInstance(){
        if(instance == null)
            instance = new AACurrentTextArea();
        return instance;
    }

    /**
    * The sole constructor for this AACurrentTextArea.
    */
    public AACurrentTextArea() {
        this.setEditable(false);
        this.setPrefHeight(20);
        this.setStyle("-fx-text-inner-color: #000000");
        this.setFont(new Font(20));
    }

    /**
    * Takes the current text from the AACurrentTextArea and adds this as the newest line to the AALoggedTextArea.
    * @param message The message to set as the current message on the AACurrentTextArea.
    */
    public void setCurrentMessage(String message){
        AALoggedTextArea.getInstance().addNewText(this.getText());
        this.setText(message);
    }

    /**
     * Resets the current message being displayed to an empty String.
     */
    public void reset(){
        this.setText("");
    }
}
