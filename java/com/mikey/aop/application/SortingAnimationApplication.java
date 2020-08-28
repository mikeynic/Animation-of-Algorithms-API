package com.mikey.aop.application;

import com.mikey.aop.controllers.SortingController;
import com.mikey.aop.sorting.algorithms.GenericSort;
import com.mikey.aop.sorting.components.SortingCanvas;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * The driver class for launching the sorting animation application.
 * @author Michael Nicholson
 */
public class SortingAnimationApplication extends Application {

    /**
     * Starts the application.
     * @param primaryStage The stage for this application.
     * @throws Exception The exception that can be thrown on failure of application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getResource("/Sorting.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Animation of Sorting");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        SortingController controller = loader.getController();
        double width = controller.getSortingBorderPane().getWidth();
        double height = controller.getSortingBorderPane().getHeight();
        primaryStage.setMinWidth(width);
        primaryStage.setMinHeight(height);

        controller.init(SORTING_CONSTANT.genericSort);
        // todo: fix problem with the resizing
        SORTING_CONSTANT.width.widthProperty().bind(primaryStage.widthProperty().multiply(0.7));
        SORTING_CONSTANT.height.heightProperty().bind(primaryStage.heightProperty().multiply(0.5));
        SORTING_CONSTANT.blockHeight.blockHeightProperty().bind(primaryStage.heightProperty().multiply(0).add(SORTING_CONSTANT.height.getHeight()/(SORTING_CONSTANT.maxElement + 1))); //0.05
        SORTING_CONSTANT.blockWidth.blockWidthProperty().bind(primaryStage.widthProperty().multiply(0.03));
        SortingCanvas.getInstance().widthProperty().bind(primaryStage.widthProperty().multiply(0.7));
        SortingCanvas.getInstance().heightProperty().bind(primaryStage.heightProperty().multiply(0.5));
    }

    /**
     * Gets the fxml layout with the path specified.
     * @param path Is the name of the layout file in String form.
     * @return The resource path for the argument provided.
     * */
    public static URL getResource(String path){
        return SortingAnimationApplication.class.getResource(path);
    }

    /**
     * Sets the sorting algorithm in the global constants that is used to be animated.
     * @param sort Is the sorting algorithm that is used for animation.
     */
    public static void runAnimation(GenericSort sort){
        SORTING_CONSTANT.genericSort = sort;
        launch();
    }

    /**
     * Exits all threads on shutdown.
     * @throws Exception The exception that can be thrown on failure of stop being executed.
    */
    @Override
    public void stop() throws Exception {
        SORTING_CONSTANT.APPLICATION_EXIT.set(true);
        super.stop();
        System.exit(0);
    }
}
