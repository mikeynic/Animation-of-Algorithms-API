package com.mikey.aop.controllers;

import com.jfoenix.controls.JFXSlider;
import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.AALoggedTextArea;
import com.mikey.aop.application.SORTING_CONSTANT;
import com.mikey.aop.sorting.algorithms.GenericSort;
import com.mikey.aop.sorting.components.SortingBorderPane;
import com.mikey.aop.sorting.components.SortingCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The controller class used in the MVC design pattern.
 * Used to map the relevant components in the layout to the backend logic
 * @author Michael Nicholson
 */
public class SortingController {

    private SortingCanvas sortingCanvas;

    @FXML
    SortingBorderPane sortingBorderPane;

    /**
     * The method used to setup the sorting animation.
     * This method should only ever be run after the global constant for the GenericSort is set.
     * This method sets the speed slider and its corresponding listener.
     * The method also adds the AACurrentTextArea and the AALoggedTextArea, once instantiated, to the layout.
     * @param gs Contains the sorting algorithm used for animation
     *           (set through the animation application runAnimation method)
     */
    public void init(GenericSort gs){
        sortingCanvas = SortingCanvas.getInstance();
        sortingBorderPane.getToggleButton().setOnAction(this::changeRepresentation);
        sortingBorderPane.getPlayButton().setOnAction(this::onPlayButtonPress);
        sortingBorderPane.getPauseButton().setOnAction(this::onPauseButtonPress);
        sortingBorderPane.getStepButton().setOnAction(this::onStepButtonPress);
        sortingBorderPane.getResetButton().setOnAction(this::onResetButtonPress);

        gs.sort();
    }

    /**
     * Maps the toggle to the backend logic.
     * The backend logic results in the SortingCanvas representation of the array switching from
     * the array representation to the rectangle representation or vice versa.
     * @param e The event passed from the toggle press.
     */
    private void changeRepresentation(ActionEvent e){
        SORTING_CONSTANT.ARRAY_REPRESENTATION_IS_ON = !SORTING_CONSTANT.ARRAY_REPRESENTATION_IS_ON;
        sortingCanvas.drawArrayRepresentation();
    }

    /**
     * Maps the play button to the backend logic which results in the animation playing.
     * @param e The event passed from the button press.
     */
    private void onPlayButtonPress(ActionEvent e){
        sortingCanvas.play();
    }

    /**
     * Maps the pause button to the backend logic which results in the animation being paused.
     * @param e The event passed from the button press.
     */
    private void onPauseButtonPress(ActionEvent e){
        sortingCanvas.pause();
    }

    /**
     * Maps the step button to the backend logic which results in the canvas showing the animation of one operation.
     * @param e The event passed from the button press.
     */
    private void onStepButtonPress(ActionEvent e){
        sortingCanvas.step();
    }

    /**
     * Maps the reset button to the backend logic which results in the canvas showing the initial state of the data
     * structure.
     * @param e The event passed from the button press.
     */
    private void onResetButtonPress(ActionEvent e){
        sortingCanvas.reset();
    }

    /**
     * Getter for the custom border pane used in the view of this application.
     * @return The layout used as the view in this application.
     */
    public SortingBorderPane getSortingBorderPane() {
        return sortingBorderPane;
    }
}
