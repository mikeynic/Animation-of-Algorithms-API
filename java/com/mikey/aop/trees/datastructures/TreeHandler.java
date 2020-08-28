package com.mikey.aop.trees.datastructures;

import com.mikey.aop.application.TREE_CONSTANTS;
import javafx.scene.paint.Color;

/**
 * This class handles the internal workings of the tree data structure. This class sets the parent, left child, right
 * child and the coordinates for each node that is reachable from the root passed in to this class. This then allows
 * access to the sophisticated data structure through the return of the root.
 * @author Michael Nicholson
 */
public class TreeHandler {
    CanvasNode root;

    /**
     * This is the sole constructor for this class. The node passed to this class where each child in the tree is
     * recursively processed to set the pointers of the children and parent.
     * @param arr The array that the user passes in. The data needs to be processed in order to extract
     *            data in a usable form.
     */
    public TreeHandler(int[] arr) {
        setParents(arr);
        SetOrdinates so = new SetOrdinates();
        so.set_ords(root, 1);
    }

    /**
     * Getter for the root of this sophisticated tree structure.
     * @return The root of this tree.
     */
    public CanvasNode getRoot() {
        return root;
    }

    /**
     * Returns the node with the index specified as the parameter. Useful when we want to extract or change
     * data of specific nodes.
     * @param index         The index of the node that needs to be returned.
     * @return              The CanvasNode that corresponds to the specified index.
     */
    public CanvasNode findNodeWithIndex(int index){
        return findNodeWithIndexHelper(root, index);
    }

    /**
     * Helper method for the findNodeWithIndex class. This is used to recursively search for the node.
     * @param node  The node that is currently being queried for the index.
     * @param index The index that is being searched for.
     * @return      The CanvasNode that corresponds to the index specified.
     */
    private CanvasNode findNodeWithIndexHelper(CanvasNode node, int index){
        if(node == null)
            return null;
        if(node.getIndexInArray() == index)
            return node;
        CanvasNode res1 = findNodeWithIndexHelper(node.getLeft(), index);
        if(res1 != null)
            return res1;
        return findNodeWithIndexHelper(node.getRight(), index);
    }

    /**
     * A wrapper method to set the parents of each node.
     * @param arr The input array that corresponds to the internal sophisticated data structure.
     */
    private void setParents(int[] arr){
        if(arr[1] == 0)
        return;
        root = new CanvasNode(arr[1]);
        root.setIndexInArray(1);
        setParentsUtil(arr, 1, root, null);
    }

    /**
     * Helper function for the setParents method. This recursively moves through the tree whilst setting a pointer to
     * the parent of each of the nodes children.
     * @param arr       The array corresponding to the users input.
     * @param currIndex The index of the node in the array.
     * @param cNode     The CanvasNode that corresponds to the currIndex specified.
     * @param cParent   The parent of the current node
     */
    private void setParentsUtil (int[] arr, int currIndex , CanvasNode cNode, CanvasNode cParent) {
        int value = arr[currIndex];
        cNode.setValue(value);
        cNode.setParent(cParent);
        int leftIndex = 2*currIndex;
        int rightIndex = leftIndex+1;
        if(leftIndex < arr.length){
            int left = arr[leftIndex];
            if (left != 0) {
                CanvasNode leftNode = new CanvasNode(left);
                leftNode.setIndexInArray(leftIndex);
                cNode.setLeft(leftNode);
                setParentsUtil(arr, leftIndex, cNode.getLeft(), cNode);
            }
        }
        if(rightIndex < arr.length) {
            int right = arr[rightIndex];
            if (right != 0) {
                CanvasNode rightNode = new CanvasNode(right);
                rightNode.setIndexInArray(rightIndex);
                cNode.setRight(rightNode);
                setParentsUtil(arr, rightIndex, cNode.getRight(), cNode);
            }
        }
    }

    /**
     * Internal class used to set the coordinates of the nodes in 2D space.
     * @author Michael Nicholson
     */
    private class SetOrdinates{
        private int i = 1;
        private int maxDepth = 0;
        private final int nodeSize = TREE_CONSTANTS.NODE_DIAMETER;

        /**
         * Method to set the coordinates of each of the nodes that are specified as a parameter to this method.
         * The method recursively moves through the tree to calculate the coordinates of the nodes in the tree.
         * @param node      The node currently being operated on to set the coordinates.
         * @param depth     The current depth of the tree.
         */
        private void set_ords(CanvasNode node, int depth){
            if(node.getLeft() != null)
                set_ords(node.getLeft(), depth + 1);

            node.setXYOrd(i *nodeSize, depth*nodeSize);
            node.setIndexInPrinting(i);
            node.setDepthInPrinting(depth);
            i++;

            if(node.getRight() != null)
                set_ords(node.getRight(), depth + 1);

            if(depth+1 > maxDepth) {
                maxDepth = depth + 1;
            }
        }
    }

    /**
     * Method to utilise the SetOrdinates class. This is essentially a wrapper method to set the coordinates of the
     * nodes using the set_ords method.
     */
    public void setNewCoordinates(){
        SetOrdinates so = new SetOrdinates();
        so.set_ords(root, 1);
    }

    /**
     * Method to mark an index within the tree.
     * @param index         The index being marked.
     * @param color         The colour that the node will be marked with.
     */
    public void markIndexInTree(int index, Color color){
        CanvasNode node = findNodeWithIndex(index);
        if(node != null){
            node.setMarked(true);
            node.setMarkingColour(color);
        }
    }

    /**
     * Method to unmark an index within the tree.
     * @param index         The index being un-marked.
     */
    public void unmarkIndexInTree(int index){
        CanvasNode node = findNodeWithIndex(index);
        if(node != null){
            node.setMarked(false);
            node.setMarkingColour(null);
        }
    }
}
