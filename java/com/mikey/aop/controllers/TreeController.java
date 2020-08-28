package com.mikey.aop.controllers;

import com.jfoenix.controls.JFXSlider;
import com.mikey.aop.application.TREE_CONSTANTS;
import com.mikey.aop.exceptions.NotALeafNodeException;
import com.mikey.aop.trees.algorithms.GenericTreeAlgorithm;
import com.mikey.aop.trees.components.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.reflect.generics.tree.Tree;

/**
 * The controller class used in the MVC design pattern.
 * Used to map the relevant components in the layout to the backend logic
 * @author Michael Nicholson
 */
public class TreeController {

    TreeCanvas canvas;

    @FXML
    TreeBorderPane treeBorderPane;
    /**
     * The method used to setup the tree animation.
     * This method should only ever be run after the global constant for the GenericTreeAlgorithm is set.
     * This method sets the speed slider and its corresponding listener.
     * The method also adds the AACurrentTextArea and the AALoggedTextArea, once instantiated, to the layout.
     * @param treeAlgorithm Contains the tree algorithm used for animation
     *           (set through the animation application runAnimation method)
     */
    public void init(GenericTreeAlgorithm treeAlgorithm){
        canvas = TreeCanvas.getInstance();
        treeBorderPane.getPlayButton().setOnAction(this::onPlayButtonClick);
        treeBorderPane.getPauseButton().setOnAction(this::onPauseButtonClick);
        treeBorderPane.getStepButton().setOnAction(this::onStepButtonClick);
        treeBorderPane.getResetButton().setOnAction(this::onResetButtonClick);
        treeAlgorithm.algorithm();
    }

    /**
     * Maps the play button to the backend logic which results in the animation playing.
     * @param e The event passed from the button press.
     */
    private void onPlayButtonClick(ActionEvent e){
        canvas.stepAll();
    }

    /**
     * Maps the pause button to the backend logic which results in the animation pausing.
     * @param e The event passed from the button press.
     */
    private void onPauseButtonClick(ActionEvent e){
        canvas.pause();
    }

    /**
     * Maps the step button to the backend logic which results in the canvas animating a single operation.
     * @param e The event passed from the button press.
     */
    private void onStepButtonClick(ActionEvent e){
        canvas.step();
    }

    /**
     * Maps the reset button to the backend logic which results in the canvas showing the initial state of the data
     * structure.
     * @param e The event passed from the button press.
     */
    private void onResetButtonClick(ActionEvent e){
        canvas.reset();
    }
}
