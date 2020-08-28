package com.mikey.aop.trees.components;

import com.mikey.aop.application.AACurrentTextArea;
import com.mikey.aop.application.TREE_CONSTANTS;
import com.mikey.aop.exceptions.NotALeafNodeException;
import com.mikey.aop.trees.datastructures.CanvasNode;
import com.mikey.aop.trees.datastructures.HandlerQueueThread;
import com.mikey.aop.trees.datastructures.TreeHandler;
import com.mikey.aop.trees.datastructures.queue.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.aspectj.weaver.ast.Not;


/**
 * TreeArrayCanvas extends the JavaFx Canvas class.
 * This class is used to reflect the changes in the tree data structure which is stored as an array. The
 * visual should animate the changes called by the user.
 * @author Michael Nicholson
 */
public class TreeCanvas extends Canvas{

    private static TreeCanvas instance;
    private final HandlerQueueThread thread;
    private TreeHandler treeHandler;
    private final GraphicsContext gc;
    private int[] userTree;

    /*
    * Variables used only for changing the state of the queue e.g. to restore a previous state/ reset the queue
    * */
    private final QueueActions queueActions;


    /**
     * Enforces the singleton design pattern.
     * @return The singleton instance of the TreeCanvas.
     */
    public static synchronized TreeCanvas getInstance(){
        if(instance == null)
            instance = new TreeCanvas();
        return instance;
    }

    /**
     * This is the sole constructor of the class and contains setup code for the operation queues and threads.
     */
    public TreeCanvas(){
        super(TREE_CONSTANTS.WIDTH, TREE_CONSTANTS.HEIGHT);
        gc = this.getGraphicsContext2D();
        queueActions = new QueueActions();
        thread = new HandlerQueueThread();
        thread.start();
    }


// aspect methods ------------------------------------------------------------------------------------------------------

    /**
     * This is the method that the TreeAspect calls in order to enqueue a SetArray operation in to the internal
     * queue. The array passed in to this method creates an internal representation of the tree with additional
     * information for parsing.
     * @param array Is the array that represents the tree.
     */
    public void setArray(int[] array){
        this.userTree = new int[array.length];
        System.arraycopy(array, 0, this.userTree, 0, array.length);
        drawBackground();
        TreeHandler dummyTree = new TreeHandler(array);
        TreeArrayCanvas.getInstance().drawArr(array);
        drawTree(dummyTree.getRoot());
        queueActions.add(new SetArray(array));
        setArrayHelper(array);
    }

    /**
     * This is the method that the SortingAspect calls in order to enqueue a marking operation of the default colour
     * in to the internal queue. The marking will persist on the index rather than the element.
     * @param index Is the index of one the element being marked.
     * @param message   Is the optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     */
    public void enqueueMarkIndexInTree(int index, String message) throws ArrayIndexOutOfBoundsException{
        QueueNode node = new Mark(index);
        node.setMessage(message);
        queueActions.add(node);
        enqueueMarkIndexInTreeHelper(index, message);
    }

    /**
     * This is the method that the TreeAspect calls in order to enqueue a marking operation of a specified colour
     * in to the internal queue. The marking will persist on the index rather than the element.
     * @param index     Is the index of one the element being marked.
     * @param color     Is the custom colour of the marking.
     * @param message   Is the optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly..
     */
    public void enqueueMarkIndexWithColorInTree(int index, Color color, String message) throws ArrayIndexOutOfBoundsException{
        QueueNode node = new Mark(index, color);
        node.setMessage(message);
        queueActions.add(node);
        enqueueMarkIndexInTreeHelper(index, color, message);
    }

    /**
     * This is the method that the TreeAspect calls in order to enqueue an unmark operation in to the internal queue.
     * @param index         Is the index of the element being unmarked.
     * @param message       Is the optional parameter for the overloading of methods in the GenericTreeAlgorithm
     *                      class used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                      handled accordingly.
     */
    public void enqueueUnmarkIndexInTree(int index, String message) throws ArrayIndexOutOfBoundsException{
        QueueNode node = new Unmark(index);
        node.setMessage(message);
        queueActions.add(node);
        enqueueUnmarkIndexInTreeHelper(index, message);
    }

    /**
     * This is the method that the TreeAspect calls in order to enqueue a Swap operation in to the internal
     * queue. When swapping, the markings (if any) will stay in place while the elements move.
     * @param index1    Is an index of one of the elements in the swap. Order does not matter.
     * @param index2    Is an index of one of the elements in the swap. Order does not matter.
     * @param message   Is the optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     */
    public void enqueueSwap(int index1, int index2, String message){
        QueueNode node = new Swap(index1, index2);
        node.setMessage(message);
        queueActions.add(node);
        enqueueSwapHelper(index1, index2, message);
    }

    /**
     * This is the method that the TreeAspect calls in order to enqueue an Insertion operation in to the internal queue.
     * When inserting, the tree will first expand to create room for the insertion at the leaf node, then pause,
     * then the new leaf will float in from the top right corner to its desired place in the tree.
     * @param element   The element being inserted.
     * @param index     The index in the array that the index will be inserted to.
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     */
    public void enqueueInsert(int element, int index, String message){
        QueueNode node = new Insertion(element, index);
        node.setMessage(message);
        queueActions.add(node);
        enqueueInsertHelper(element, index, message);
    }

    /**
     * This is the method that the TreeAspect calls in order to enqueue a Deletion operation on the internal queue.
     * Deletions are only supported for leaf nodes.
     * When deleting, the leaf node will fade away, there will be a pause, then the tree will shrink back to the desired
     * shape that will only accommodate for the right amount of nodes.
     * @param index     The index of the element being removed by the tree.
     * @param message   The message that is shown when the element at the index is being deleted.
     */
    public void enqueueDelete(int index, String message){
        QueueNode node = new Deletion(index);
        node.setMessage(message);
        queueActions.add(node);
        enqueueDeleteHelper(index, message);
    }

    public void enqueueMessage(String message){
        Runnable r = () -> enqueueMessageHelper(message);
        thread.enqueueRunnable(r);
    }

// thread enqueue methods ----------------------------------------------------------------------------------------------
    /**
     * This method creates a Runnable to show a message on the AACurrentTextArea.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param message Is the parameter used to reflect changes in the AACurrentTextArea.
     */
    public void enqueueMessageHelper(String message){
        Runnable executor = () -> Platform.runLater(() -> AACurrentTextArea.getInstance().setCurrentMessage(message));
        thread.enqueueToExecutor(executor);
    }

    /**
     * This is the method that creates a Runnable for the operation to reflect the changes in the GUI.
     * The Runnable is enqueued to the HandlerQueueThread to then be executed.
     * @param array Is the array that is used to create the tree representation on the GUI.
     */
    public void setArrayHelper(int[] array){
        Runnable r = () -> {
            Runnable executor = () -> {
                int len = array.length;
                this.userTree = new int[len];
                System.arraycopy(array, 0, this.userTree, 0, len);
                treeHandler = new TreeHandler(this.userTree);
                updateCanvas();
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    /**
     * This is the method that creates a Runnable to animate the marking of an element in the tree. The marking wil be
     * of the default colour.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index The index being marked in the tree array
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   If the index specified for marking is out of the range of the array,
     *                                          this exception is thrown.
     */
    public void enqueueMarkIndexInTreeHelper(int index, String message) throws ArrayIndexOutOfBoundsException{
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{int t = userTree[index];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                treeHandler.markIndexInTree(index, TREE_CONSTANTS.DEFAULT_MARKING_COLOR);
                updateCanvas();
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    /**
     * This is the method that creates a Runnable to animate the marking of an element in the tree. The marking wil be
     * of the custom, specified colour.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index     The index being marked in the tree array
     * @param color     The colour of the marking.
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   If the index specified for marking is out of the range of the array,
     *                                          this exception is thrown.
     */
    public void enqueueMarkIndexInTreeHelper(int index, Color color, String message) throws ArrayIndexOutOfBoundsException{
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{int t = userTree[index];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                treeHandler.markIndexInTree(index, color);
                updateCanvas();
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    /**
     * This is the method that creates a Runnable to animate the un-marking of an element in the tree. If the index is
     * already unmarked, it will stay this way.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index     The index being un-marked in the tree array
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   Thrown if the marking index that is specified is out of the range of the array.
     */
    public void enqueueUnmarkIndexInTreeHelper(int index, String message) throws ArrayIndexOutOfBoundsException{
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{int t = userTree[index];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                treeHandler.unmarkIndexInTree(index);
                updateCanvas();
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    private void updateCanvas(){
        Runnable animation = () -> {
            drawBackground();
            drawTree(treeHandler.getRoot());
        };
        Platform.runLater(animation);
    }

    /**
     * This method creates a Runnable to animate the swapping of two elements in the tree array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index1    Is an index of one of the elements in the swap. Order does not matter.
     * @param index2    Is an index of one of the elements in the swap. Order does not matter.
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   Thrown if either of the indices supplied to this method are out of the
     *                                          range of the array.
     */
    public void enqueueSwapHelper(int index1, int index2, String message) throws ArrayIndexOutOfBoundsException{
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{
                    int i1 = userTree[index1];
                    int i2 = userTree[index2];
                }catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
                CanvasNode node1 = treeHandler.findNodeWithIndex(index1);
                CanvasNode node2 = treeHandler.findNodeWithIndex(index2);

                final int finalNode1x = node2.getXOrd();
                final int finalNode1y = node2.getYOrd();
                final int finalNode2x = node1.getXOrd();
                final int finalNode2y = node1.getYOrd();
                int prevNode1x = node1.getXOrd();
                int prevNode1y = node1.getYOrd();
                int prevNode2x = node2.getXOrd();
                int prevNode2y = node2.getYOrd();

                while (prevNode1x != finalNode1x
                        || prevNode1y != finalNode1y
                        || prevNode2x != finalNode2x
                        || prevNode2y != finalNode2y) {
                    int finalPrevNode1x = prevNode1x;
                    int finalPrevNode1y = prevNode1y;
                    int finalPrevNode2x = prevNode2x;
                    int finalPrevNode2y = prevNode2y;
                    Runnable animation = () -> {
                        drawBackground();
                        drawTree(treeHandler.getRoot(), index1, index2);
                        drawMovingNode(finalPrevNode1x, finalPrevNode1y, node1);
                        drawMovingNode(finalPrevNode2x, finalPrevNode2y, node2);
                    };
                    Platform.runLater(animation);
                    try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH);}catch (Exception e){e.printStackTrace();}
                    if(prevNode1x < finalNode1x) prevNode1x++; else prevNode1x--;
                    if(prevNode1y < finalNode1y) prevNode1y++; else prevNode1y--;
                    if(prevNode2x < finalNode2x) prevNode2x++; else prevNode2x--;
                    if(prevNode2y < finalNode2y) prevNode2y++; else prevNode2y--;
                }

                /*this is the actual swap*/
                // swapping in the sophisticated tree structure
                final int node1Val = node1.getValue();
                final int node2Val = node2.getValue();
                final boolean node1Mark = node1.isMarked();
                final boolean node2Mark = node2.isMarked();
                final Color node1Color = node1.getMarkingColour();
                final Color node2Color = node2.getMarkingColour();
                node1.setValue(node2Val);
                node2.setValue(node1Val);
                node1.setMarked(node2Mark);
                node2.setMarked(node1Mark);
                node1.setMarkingColour(node2Color);
                node2.setMarkingColour(node1Color);

                // swapping in the array
                int temp = userTree[index1];
                userTree[index1] = userTree[index2];
                userTree[index2] = temp;
                Runnable animation = () -> {
                    drawBackground();
                    drawTree(treeHandler.getRoot());
                    TreeArrayCanvas.getInstance().drawArr(userTree);
                };
                Platform.runLater(animation);
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to animate the insertion of an element in to the leaf of the tree array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param element   Is the element/ value being inserted in to the tree array.
     * @param index     Is the index that the element will be inserted in to in the array.
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   Thrown if the index specified is out of the range of the tree array.
     */
    public void enqueueInsertHelper(int element, int index, String message) {
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{int e = userTree[index];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                try{
                    if(userTree[index] != 0)
                        throw new NotALeafNodeException(index);
                }catch (NotALeafNodeException e){
                    e.printStackTrace();
                }

                int[] copyArray = new int[userTree.length];
                System.arraycopy(userTree, 0, copyArray, 0, userTree.length);
                copyArray[index] = element;
                userTree[index] = element;
                TreeHandler copyTreeHandler = new TreeHandler(copyArray);
                CanvasNode copyChild = copyTreeHandler.findNodeWithIndex(index);
                int printIndex = copyChild.getIndexInPrinting();
                int depthIndex = copyChild.getDepthInPrinting();

                for(int i = 1; i<= TREE_CONSTANTS.NODE_DIAMETER; i++){
                    incrementXofIndexLargerThan(treeHandler.getRoot(), printIndex);
                    Runnable animation = () -> {
                        drawBackground();
                        drawTree(treeHandler.getRoot());
                    };
                    try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH);}catch (Exception e) {e.printStackTrace();}
                    Platform.runLater(animation);
                }
                Runnable animation;
                if(index%2 == 0){
                    // left child
                    int nDiameter = TREE_CONSTANTS.NODE_DIAMETER;
                    int x = -nDiameter*2;
                    int y = -nDiameter*2;
                    int endX = printIndex*nDiameter;
                    int endY = depthIndex*nDiameter;
                    int parentIndex = index/2;

                    while(x != endX || y != endY){
                        int finalX = x;
                        int finalY = y;
                        animation = () -> {
                            CanvasNode dummy = new CanvasNode(element);
                            dummy.setXYOrd(finalX, finalY);
                            // draw node
                            drawBackground();
                            drawTree(treeHandler.getRoot());
                            drawNode(dummy);
                        };
                        Platform.runLater(animation);
                        try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH);}catch (Exception e) {e.printStackTrace();}
                        if(x < endX){x++;}
                        if(x > endX){x--;}
                        if(y < endY){y++;}
                        if(y > endY){y--;}
                    }
                    CanvasNode parent = treeHandler.findNodeWithIndex(parentIndex);
                    CanvasNode child = new CanvasNode(element);
                    child.setIndexInArray(index);
                    child.setParent(parent);
                    child.setIndexInPrinting(printIndex);
                    parent.setLeft(child);
                    treeHandler.setNewCoordinates();
                }
                else{
                    // right child
                    int nDiameter = TREE_CONSTANTS.NODE_DIAMETER;
                    int x = -nDiameter*2;
                    int y = -nDiameter*2;
                    int endX = printIndex*nDiameter;
                    int endY = depthIndex*nDiameter;
                    int parentIndex = (index - 1) / 2;

                    while(x != endX || y != endY){
                        int finalX = x;
                        int finalY = y;
                        animation = () -> {
                            CanvasNode dummy = new CanvasNode(element);
                            dummy.setXYOrd(finalX, finalY);
                            // draw node
                            drawBackground();
                            drawTree(treeHandler.getRoot());
                            drawNode(dummy);
                        };
                        Platform.runLater(animation);
                        try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH);}catch (Exception e) {e.printStackTrace();}
                        if(x < endX){x++;}
                        if(x > endX){x--;}
                        if(y < endY){y++;}
                        if(y > endY){y--;}
                    }
                    CanvasNode parent = treeHandler.findNodeWithIndex(parentIndex);
                    CanvasNode child = new CanvasNode(element);
                    child.setIndexInArray(index);
                    child.setParent(parent);
                    child.setIndexInPrinting(printIndex);
                    parent.setRight(child);
                    treeHandler.setNewCoordinates();
                }
                animation = () -> {
                    drawBackground();
                    drawTree(treeHandler.getRoot());
                    TreeArrayCanvas.getInstance().drawArr(userTree);
                };
                Platform.runLater(animation);
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }

    /**
     * This method creates a Runnable to animate the deletion leaf element in the tree array.
     * The Runnable is then enqueued to the internal HandlerQueueThread to then be executed.
     * @param index     Is the index that the element will be deleted from the tree array.
     * @param message   The optional parameter for the overloading of methods in the GenericTreeAlgorithm class
     *                  used to reflect changes in the AACurrentTextArea. This can be null and will be
     *                  handled accordingly.
     * @throws ArrayIndexOutOfBoundsException   Thrown if the index supplied is out of the range of the array.
     */
    public void enqueueDeleteHelper(int index, String message) throws ArrayIndexOutOfBoundsException{
        Runnable r = () -> {
            if(message != null)
                enqueueMessageHelper(message);
            Runnable executor = () -> {
                try{int e = userTree[index];}catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                try{
                    if(index*2 < userTree.length && userTree[index*2] != 0)
                        throw new NotALeafNodeException(index);
                    if((index+1)*2 < userTree.length && userTree[(index+1)*2] != 0)
                        throw new NotALeafNodeException(index);
                }catch (NotALeafNodeException e){
                    e.printStackTrace();
                }
                CanvasNode deletionNode = treeHandler.findNodeWithIndex(index); // assumed that this node is a leaf i.e. has no children
                CanvasNode parent = deletionNode.getParent();
                int printIndex = deletionNode.getIndexInPrinting();
                Runnable animation;
                if(deletionNode == parent.getLeft())
                    parent.setLeft(null);
                else if(deletionNode == parent.getRight())
                    parent.setRight(null);
                double opacity = 1.0;
                while(opacity > 0){
                    double finalOpacity = opacity;
                    animation = () -> {
                        drawBackground();
                        gc.setGlobalAlpha(finalOpacity);
                        drawNode(deletionNode);
                        gc.setGlobalAlpha(1.0);
                        drawTree(treeHandler.getRoot());
                    };
                    Platform.runLater(animation);
                    try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*2);}catch(Exception e){e.printStackTrace();}
                    opacity = opacity - 0.01;
                }
                userTree[index] = 0;
                animation = () -> {
                    drawBackground();
                    drawTree(treeHandler.getRoot());
                    TreeArrayCanvas.getInstance().drawArr(userTree);
                };
                Platform.runLater(animation);
                for(int i = 1; i<= TREE_CONSTANTS.NODE_DIAMETER; i++){
                    decrementXofIndexLargerThan(treeHandler.getRoot(), printIndex);
                    animation = () -> {
                        drawBackground();
                        drawTree(treeHandler.getRoot());
                    };
                    Platform.runLater(animation);
                    try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*2);}catch(Exception e){e.printStackTrace();}
                }
                try {Thread.sleep(TREE_CONSTANTS.SWAP_FRAME_LENGTH*40);}catch (Exception e) {e.printStackTrace();}
                treeHandler.setNewCoordinates();
            };
            thread.enqueueToExecutor(executor);
        };
        thread.enqueueRunnable(r);
    }
// drawing methods -----------------------------------------------------------------------------------------------------
    /**
     * Helper method to draw the background of the correct colour
     */
    private void drawBackground(){
        gc.setFill(Color.rgb(255, 255, 255));
        gc.fillRect(0,0, TREE_CONSTANTS.WIDTH, TREE_CONSTANTS.HEIGHT);
    }

    /**
     * This is a helper method that sets the colours in the graphics context.
     * @param color Is the color that the graphics context colours will be set to draw with.
     */
    private void setGcColour(Color color){
        gc.setStroke(color);
        gc.setFill(color);
    }

    /**
     * This is the a method for actually drawing the tree on this Canvas. This executes recursively.
     * @param node The node that is to be drawn.
     */
    private void drawTree(CanvasNode node){
        if(node.getLeft() != null)
            drawTree(node.getLeft());
        if(node.getRight() != null)
            drawTree(node.getRight());
        drawNode(node);
    }

    /**
     * This is a method that facilitates the animation of the swapping of two nodes. This is done by drawing the tree
     * without the two nodes that are to be swapped. This also misses out the correct branches so that there are none
     * flailing unconnected. This executes recursively.
     * @param node The node that is to be drawn.
     * @param index1 The index of an element that is not to be drawn.
     * @param index2 The index of an element that is not to be drawn.
     */
    private void drawTree(CanvasNode node, int index1, int index2){
        if(node.getLeft() != null)
            drawTree(node.getLeft(), index1, index2);
        if(node.getRight() != null)
            drawTree(node.getRight(), index1, index2);
        if(node.getIndexInArray() != index1 && node.getIndexInArray() != index2)
            drawNode(node, index1, index2);
    }

    /**
     * This method draws the actual nodes of the tree. This figures out all of the colouring and branching of the
     * parents to the children.
     * @param node The node being drawn.
     */
    private void drawNode(CanvasNode node){
        int diameter = TREE_CONSTANTS.NODE_DIAMETER;
        int xOrd = node.getXOrd();
        int yOrd = node.getYOrd();
        int nodeVal = node.getValue();
        int offset = TREE_CONSTANTS.NODE_DIAMETER/2;
        Color color = Color.BLUE;
        setGcColour(Color.BLACK);
        drawLine(node);
        setGcColour(color);
        gc.fillOval(xOrd, yOrd, diameter, diameter);
        setGcColour(Color.YELLOW);
        gc.strokeText("" + nodeVal, xOrd + offset, yOrd + offset);
        if(node.isMarked()){
            if(node.getMarkingColour() != null)
                drawMarking(node, node.getMarkingColour());
            else
                drawMarking(node, Color.RED);
        }
    }

    /**
     * This method draws the actual nodes of the tree in the swapping animation. This figures out all of the
     * colouring and branching of the parents to the children. This also misses out the indexes passed to it in order to
     * animate the two nodes swapping elsewhere.
     * @param node      The node being drawn.
     * @param index1    The index of a node not being drawn.
     * @param index2    The index of a node not being drawn.
     */
    private void drawNode(CanvasNode node, int index1, int index2){
        int diameter = TREE_CONSTANTS.NODE_DIAMETER;
        int xOrd = node.getXOrd();
        int yOrd = node.getYOrd();
        int nodeVal = node.getValue();
        int offset = TREE_CONSTANTS.NODE_DIAMETER/2;
        Color color = Color.BLUE;
        setGcColour(Color.BLACK);
        if(node.getParent() != null){
            int nodeIndex = node.getParent().getIndexInArray();
            if(nodeIndex != index1 && nodeIndex != index2)
                drawLine(node);
        }
        setGcColour(color);
        gc.fillOval(xOrd, yOrd, diameter, diameter);
        setGcColour(Color.YELLOW);
        gc.strokeText("" + nodeVal, xOrd + offset, yOrd + offset);
        if(node.isMarked()){
            if(node.getMarkingColour() != null)
                drawMarking(node, node.getMarkingColour());
            else
                drawMarking(node, Color.RED);
        }
    }

    /**
     * Helper method to draw the marking of a node around the edge.
     * @param node      The node being marked.
     * @param colour    The colour of the marking being drawn.
     */
    private void drawMarking(CanvasNode node, Color colour){
        int diameter = TREE_CONSTANTS.NODE_DIAMETER;
        gc.setLineWidth(4.0);
        setGcColour(colour);
        gc.strokeOval(node.getXOrd(), node.getYOrd(), diameter, diameter);
        gc.setLineWidth(1.0);
    }

    /**
     * Method to draw a line between two nodes i.e. the parent and the child.
     * @param node The child node that is having its branch drawn from its parent.
     */
    private void drawLine(CanvasNode node){
        CanvasNode parent = node.getParent();
        if(parent == null)
            return;
        int offset = TREE_CONSTANTS.NODE_DIAMETER/2;
        gc.strokeLine(parent.getXOrd() + offset, parent.getYOrd() + offset , node.getXOrd() + offset,
                node.getYOrd() + offset);
    }

    /**
     * Helper method to facilitate the drawing of nodes pixel by pixel. Used during swapping.
     * @param x         The x ordinate of the node being drawn.
     * @param y         The y ordinate of the node being drawn.
     * @param node      The node being drawn.
     */
    private void drawMovingNode(int x, int y, CanvasNode node){
        int diameter = TREE_CONSTANTS.NODE_DIAMETER;
        Color color = Color.ORANGE;
        setGcColour(color);
        gc.fillOval(x, y, diameter, diameter);
        setGcColour(Color.YELLOW);
        gc.strokeText("" + node.getValue(), x + TREE_CONSTANTS.NODE_DIAMETER/2, y + TREE_CONSTANTS.NODE_DIAMETER/2);
        // if it is marked - follow the element not the index.
        if(node.getMarkingColour() != null) {
            gc.setLineWidth(4.0);
            setGcColour(node.getMarkingColour());
            gc.strokeOval(x, y, diameter, diameter);
            gc.setLineWidth(1.0);
        }
        /*if(node.isMarked()){
            if(node.getMarkingColour() != null)
                drawMarking(node, node.getMarkingColour());
            else
                drawMarking(node, Color.RED);
        }*/
    }

    /**
     * This method is used for drawing the expanse of the tree when inserting a new leaf node.
     * @param node          The node being inserted.
     * @param printIndex    The printing index of the node (index from left to right) that is used to move all nodes
     *                      larger than this printIndex to the right to make room for the new leaf node.
     */
    private void incrementXofIndexLargerThan(CanvasNode node, int printIndex){
        if(node == null)
            return;
        if(node.getIndexInPrinting() >= printIndex){
            int x = node.getXOrd();
            node.setXOrd(x + 1);
        }
        if(node.getLeft() != null)
            incrementXofIndexLargerThan(node.getLeft(), printIndex);
        if(node.getRight() != null)
            incrementXofIndexLargerThan(node.getRight(), printIndex);
    }

    /**
     * This method is used for drawing the shrinking of the tree when deleting a leaf node.
     * @param node          The node being deleted.
     * @param printIndex    The printing index of the node (index from left to right) that is used to move all nodes
     *                      larger than this printIndex to the left to shrink back to the correct tree size.
     */
    private void decrementXofIndexLargerThan(CanvasNode node, int printIndex){
        if(node == null)
            return;
        if(node.getIndexInPrinting() >= printIndex){
            int x = node.getXOrd();
            node.setXOrd(x - 1);
        }
        if(node.getLeft() != null)
            decrementXofIndexLargerThan(node.getLeft(), printIndex);
        if(node.getRight() != null)
            decrementXofIndexLargerThan(node.getRight(), printIndex);
    }

// controller methods --------------------------------------------------------------------------------------------------

    /**
     * Method to map the play button in the TreeController to the play method in the HandlerQueueThread.
     */
    public void stepAll(){
        thread.stepAll();
    }

    /**
     * Method to map the pause button in the TreeController to the pause method in the HandlerQueueThread.
     */
    public void pause(){
        thread.pause();
    }

    /**
     * Method to map the step button in the TreeController to the step method in the HandlerQueueThread.
     */
    public void step() {
        thread.step();
    }

    /**
     * Method to map the reset button in the TreeController to the reset method in the HandlerQueueThread.
     * Additionally this method repopulates the HandlerQueueThread with Runnable objects that correspond to the
     * operations stored in the TreeCanvas internal QueueActions queue.
     */
    public void reset(){
        int qPointer = 0;
        thread.resetQueue();

        while(qPointer < queueActions.size()){
            QueueNode node = queueActions.get(qPointer);
            String message = node.getMessage();

            if(node instanceof Deletion){
                Deletion deletion = (Deletion) node;
                enqueueDeleteHelper(deletion.getIndex(), message);
            }
            if(node instanceof Insertion){
                Insertion insertion = (Insertion) node;
                enqueueInsertHelper(insertion.getElement(), insertion.getIndex(), message);
            }
            if(node instanceof Mark){
                Mark mark = (Mark) node;
                int idx = mark.getIndex();
                if(mark.isColoured())
                    enqueueMarkIndexInTreeHelper(idx, mark.getColor(), message);
                else
                    enqueueMarkIndexInTreeHelper(idx, message);
            }
            if(node instanceof SetArray){
                SetArray setArray = (SetArray) node;
                setArrayHelper(setArray.getArray());
                drawBackground();
                TreeHandler dummyTree = new TreeHandler(setArray.getArray());
                TreeArrayCanvas.getInstance().drawArr(setArray.getArray());
                drawTree(dummyTree.getRoot());
            }
            if(node instanceof Swap){
                Swap swap = (Swap) node;
                enqueueSwapHelper(swap.getIndex1(), swap.getIndex2(), message);
            }
            if(node instanceof Unmark){
                Unmark unmark = (Unmark) node;
                enqueueUnmarkIndexInTreeHelper(unmark.getIndex(), message);
            }
            qPointer++;
        }
    }
}