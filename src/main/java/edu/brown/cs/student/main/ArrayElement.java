package edu.brown.cs.student.main;

/**
 * Basic info about each array element
 */
public class ArrayElement {

    public boolean isCell;
    public boolean inBrain;

    public ArrayElement(boolean isCell, boolean inBrain) {
        this.isCell = isCell;
        this.inBrain = inBrain;
    }

    public void setCell() {
        isCell = true;
    }
    public void setEmpty() {
        isCell = false;
    }
}
