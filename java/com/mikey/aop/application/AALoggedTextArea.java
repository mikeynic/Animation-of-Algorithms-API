package com.mikey.aop.application;

import com.jfoenix.controls.JFXTextArea;
import javafx.scene.text.Font;


/**
 * Inherits from JFXTextArea.
 * This class maintains a log of all strings passed to it until reset.
 * @author Michael Nicholson
 */
public class AALoggedTextArea extends JFXTextArea {

    /**
     * Enforces the singleton design pattern.
     */
    private static AALoggedTextArea instance;
    public static synchronized AALoggedTextArea getInstance(){
        if(instance == null)
            instance = new AALoggedTextArea();
        return instance;
    }

    /**
     * The sole constructor for this class.
     * */
    public AALoggedTextArea() {
        this.setEditable(false);
        this.setStyle("-fx-text-inner-color: #757575");
        this.setPrefHeight(100);
        this.setFont(new Font(16));
    }

    /**
     * Adds the new text passed in to the top of the TextArea.
     * @param text Is the new text to be passed to the top of the text area.
     */
    public void addNewText(String text){
        this.setText(text + "\n" + this.getText());
    }
}