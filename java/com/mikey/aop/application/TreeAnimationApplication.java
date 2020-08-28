package com.mikey.aop.application;

import com.mikey.aop.controllers.TreeController;
import com.mikey.aop.trees.algorithms.GenericTreeAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * The driver class for launching the tree animation application.
 * @author Michael Nicholson
 */
public class TreeAnimationApplication extends Application {

    /**
     * Starts the application.
     * @param primaryStage The stage used in the application.
     * @throws Exception The exception thrown in the case of application startup failure.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getResource("/tree.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tree Algorithm Animation");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        TreeController controller = loader.getController();
        controller.init(TREE_CONSTANTS.genericTreeAlgorithm);
    }

    /**
     * Gets the fxml layout with the path specified.
     * @param path Is the name of the layout file in String form.
     * @return The path to the resource that is specified as a parameter.
     */
    public static URL getResource(String path){
        return SortingAnimationApplication.class.getResource(path);
    }

    /**
     * Sets the sorting algorithm in the global constants that is used to be animated.
     * @param genericTreeAlgorithm Is the tree algorithm that is used for animation.
     */
    public static void runAnimation(GenericTreeAlgorithm genericTreeAlgorithm){
        TREE_CONSTANTS.genericTreeAlgorithm = genericTreeAlgorithm;
        launch();
    }

    /**
     * Exits all threads on shutdown.
     * @throws Exception The exception thrown in the case of application shutdown failure.
     */
    @Override
    public void stop() throws Exception {
        SM_CONSTANT.APPLICATION_EXIT = true;
        super.stop();
        System.exit(0);
    }
}
