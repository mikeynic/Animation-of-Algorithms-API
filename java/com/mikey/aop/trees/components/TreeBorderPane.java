package com.mikey.aop.trees.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.AALoggedTextArea;
import com.mikey.aop.application.TREE_CONSTANTS;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The custom border pane layout used for the sorting application.
 * @author Michael Nicholson
 */
public class TreeBorderPane extends BorderPane {

    TreeCanvas canvas;
    TreeArrayCanvas arrayCanvas;
    JFXButton playButton;
    JFXButton pauseButton;
    JFXButton stepButton;
    JFXButton resetButton;
    Label sliderLabel;
    Slider slider;

    /**
     * The sole constructor for this class.
     * This sets up the width, height, buttons, canvas, array canvas, and logging areas in their respective places.
     * This also sets the theme of the application.
     */
    public TreeBorderPane() {
        setPadding(new Insets(50));
        setStyle("-fx-background-color: #e2dcca");
        setPadding(new Insets(30,5,5,5));

        canvas = TreeCanvas.getInstance();
        arrayCanvas = TreeArrayCanvas.getInstance();
        int width = TREE_CONSTANTS.WIDTH;
        int height = TREE_CONSTANTS.HEIGHT;
        canvas.minWidth(width);
        canvas.minHeight(height);

        playButton = createButton("Play");
        pauseButton = createButton("Pause");
        stepButton = createButton("Step");
        resetButton = createButton("Reset");
        sliderLabel = new Label("Speed:");
        sliderLabel.setAlignment(Pos.CENTER);
        slider = new JFXSlider(1, 100, 10);
        AACurrentTextArea currentTextArea = AACurrentTextArea.getInstance();
        AALoggedTextArea loggedTextArea = AALoggedTextArea.getInstance();

        slider.valueProperty().addListener((observable, oldValue, newValue) ->
                TREE_CONSTANTS.SWAP_FRAME_LENGTH = newValue.intValue());

        HBox top = new HBox(5);
        top.setPadding(new Insets(5));
        top.setStyle("-fx-background-color: #ffffff;");
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(playButton, pauseButton, stepButton, resetButton, sliderLabel, slider);
        VBox bottom = new VBox();
        bottom.setPadding(new Insets(5));
        bottom.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-radius: 50");
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(currentTextArea, loggedTextArea);

        HBox center = new HBox(5);
        center.setPadding(new Insets(5));
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(canvas, arrayCanvas);

        setTop(top);
        setBottom(bottom);
        setCenter(center);
    }

    /**
     * Helper method to generate the buttons used in the application.
     * @param buttonText The text that the button will display.
     * @return The button used in the UI.
     */
    private JFXButton createButton(String buttonText){
        JFXButton button = new JFXButton(buttonText);
        button.setStyle("-fx-background-color: #a8abff;" +
                "-fx-border-radius: 25;" +
                "-fx-border-color: #000000;" +
                "-fx-border-radius: 5");
        return button;
    }

    /**
     * Getter for the play button.
     * @return The play button.
     */
    public JFXButton getPlayButton() {
        return playButton;
    }

    /**
     * Getter for the pause button.
     * @return The pause button.
     */
    public JFXButton getPauseButton() {
        return pauseButton;
    }

    /**
     * Getter for the step button.
     * @return The step button.
     */
    public JFXButton getStepButton() {
        return stepButton;
    }

    /**
     * Getter for the reset button.
     * @return The reset button.
     */
    public JFXButton getResetButton() {
        return resetButton;
    }
}
