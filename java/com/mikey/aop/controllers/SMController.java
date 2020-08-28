package com.mikey.aop.controllers;

import com.mikey.aop.sorting.components.SortingBorderPane;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;
import com.mikey.aop.stringmatching.components.SMBorderPane;
import com.mikey.aop.stringmatching.components.SMCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;



/**
 * The controller class used in the MVC design pattern.
 * Used to map the relevant components in the layout to the backend logic.
 * @author Michael Nicholson
 */
public class SMController {

    private SMCanvas smCanvas;

    @FXML
    private SMBorderPane smBorderPane;

    /**
     * The method used to setup the string matching animation.
     * This method should only ever be run after the global constant for the GenericStringMatch is set.
     * The method also adds the AACurrentTextArea and the AALoggedTextArea, once instantiated, to the layout.
     * @param sm Contains the string matching algorithm used for animation
     *           (set through the animation application runAnimation method)
     */
    public void init(GenericStringMatch sm) {

        smCanvas = SMCanvas.getInstance();
        smBorderPane.getPlayButton().setOnAction(this::onPlayButtonClick);
        smBorderPane.getPauseButton().setOnAction(this::onPauseButtonClick);
        smBorderPane.getStepButton().setOnAction(this::onStepButtonClick);
        smBorderPane.getResetButton().setOnAction(this::onResetButtonClick);

        sm.stringMatch();
    }

    /**
     * Mapping of the play button to the play logic in SMCanvas which results in the animation playing.
     * @param e The event passed from the button press.
     */
    private void onPlayButtonClick(ActionEvent e){
        smCanvas.play();
    }

    /**
     * Mapping of the pause button to the pause logic in SMCanvas which results in the animation pausing.
     * @param e The event passed from the button press.
     */
    private void onPauseButtonClick(ActionEvent e){
        smCanvas.pause();
    }

    /**
     * Mapping of the step button to the step logic in SMCanvas which results in the animation of one operation.
     * @param e The event passed from the button press.
     */
    private void onStepButtonClick(ActionEvent e){
        smCanvas.step();
    }

    /**
     * Mapping of the reset button to the reset logic in SMCanvas which results in the initial state of the data
     * representation being restored.
     * @param e The event passed from the button press.
     */
    private void onResetButtonClick(ActionEvent e){
        smCanvas.reset();
    }

    /**
     * Getter for the layout used in the view of this application.
     * @return The layout used in this application.
     */
    public SMBorderPane getSmBorderPane() {
        return smBorderPane;
    }
}
