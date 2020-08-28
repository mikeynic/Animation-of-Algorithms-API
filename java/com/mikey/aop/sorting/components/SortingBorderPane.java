package com.mikey.aop.sorting.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.AALoggedTextArea;
import com.mikey.aop.application.SORTING_CONSTANT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The custom border pane layout used for the sorting application.
 * @author Michael Nicholson
 */
public class SortingBorderPane extends BorderPane {

    SortingCanvas canvas;
    JFXButton playButton;
    JFXButton pauseButton;
    JFXButton stepButton;
    JFXButton resetButton;
    JFXToggleButton toggleButton;
    Label sliderLabel;
    Slider slider;


    /**
     * The sole constructor for this class.
     * This sets up the width, height, buttons, toggle, canvas and logging areas in their respective places.
     * This also sets the theme of the application.
     */
    public SortingBorderPane() {
        setPadding(new Insets(50));
        setStyle("-fx-background-color: #e2dcca");

        int width = SORTING_CONSTANT.width.getWidth();
        int height = SORTING_CONSTANT.height.getHeight();
        canvas = SortingCanvas.getInstance();
        canvas.minWidth(width);
        canvas.minHeight(height);

        playButton = createButton("Play");
        pauseButton = createButton("Pause");
        stepButton = createButton("Step");
        resetButton = createButton("Reset");
        toggleButton = new JFXToggleButton();
        sliderLabel = new Label("Speed:");
        sliderLabel.setAlignment(Pos.CENTER);
        slider = new JFXSlider(1, 100, 10);
        AACurrentTextArea currentTextArea = AACurrentTextArea.getInstance();
        AALoggedTextArea loggedTextArea = AALoggedTextArea.getInstance();

        toggleButton.setText("Array Representation");
        slider.valueProperty().addListener((observable, oldValue, newValue) ->
                SORTING_CONSTANT.ANIMATION_LENGTH = newValue.intValue());

        HBox top = new HBox(5);
        top.setStyle("-fx-background-color: #ffffff;");
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(playButton, pauseButton, stepButton, resetButton, toggleButton, sliderLabel, slider);
        VBox bottom = new VBox();
        bottom.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-radius: 50");
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(currentTextArea, loggedTextArea);

        setTop(top);
        setBottom(bottom);
        setCenter(canvas);
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

    /**
     * Getter for the array toggle.
     * @return The array toggle.
     */
    public ToggleButton getToggleButton() {
        return toggleButton;
    }
}
