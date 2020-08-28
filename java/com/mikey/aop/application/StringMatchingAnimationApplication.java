package com.mikey.aop.application;

import com.mikey.aop.controllers.SMController;
import com.mikey.aop.sorting.components.SortingCanvas;
import com.mikey.aop.stringmatching.algorithms.GenericStringMatch;
import com.mikey.aop.stringmatching.components.SMCanvas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.Block;

import java.net.URL;


/**
 * The driver class for launching the string matching animation application.
 * @author Michael Nicholson
 */
public class StringMatchingAnimationApplication extends Application {

    /**
     * Starts the application.
     * @param primaryStage The stage for this application.
     * @throws Exception The exception thrown in the case of failure of the startup of this application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getResource("/SM.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Animation of String Matching");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        SMController controller = loader.getController();
        double height = controller.getSmBorderPane().getHeight();
        double width = controller.getSmBorderPane().getWidth();
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);

        controller.init(SM_CONSTANT.genericStringMatch);

        SM_CONSTANT.blockWidth.setBlockWidth(primaryStage.widthProperty().divide(SM_CONSTANT.maxSize*1.5).intValue());
        SMCanvas.getInstance().setHeight(primaryStage.heightProperty().multiply(0.4).get());
        SMCanvas.getInstance().setWidth(primaryStage.widthProperty().multiply(0.7).get());

        SM_CONSTANT.height.heightProperty().bind(primaryStage.heightProperty().multiply(0.4));
        SM_CONSTANT.width.widthProperty().bind(primaryStage.widthProperty().multiply(0.7));
        SMCanvas.getInstance().heightProperty().bind(primaryStage.heightProperty().multiply(0.4));
        SMCanvas.getInstance().widthProperty().bind(primaryStage.widthProperty().multiply(0.7));
        SM_CONSTANT.blockWidth.blockWidthProperty().bind(primaryStage.widthProperty().divide(SM_CONSTANT.maxSize*1.5));

    }

    /**
     * Gets the fxml layout with the path specified.
     * @param path Is the name of the layout file in String form.
     * @return The source of the path that was specified.
     * */
    public static URL getResource(String path){
        return SortingAnimationApplication.class.getResource(path);
    }

    /**
     * Sets the sorting algorithm in the global constants that is used to be animated.
     * @param sm Is the string matching algorithm that is used for animation.
     */
    public static void runAnimation(GenericStringMatch sm){
        SM_CONSTANT.genericStringMatch = sm;
        launch();
    }

    /**
     * Exits all threads on shutdown.
     * @throws Exception The exception that is thrown on application shutdown failure.
     */
    @Override
    public void stop() throws Exception {
        SM_CONSTANT.APPLICATION_EXIT = true;
        super.stop();
        System.exit(0);
    }
}
