package edu.brown.cs.student.main;

import java.util.ArrayList;

/**
 * CellArray is composed of 'Slice' components, which are themselves composed of 'ArrayElement' components.
 */
public class Slice {
    public ArrayList<ArrayList<ArrayElement>> arrayList;

    public Slice(ArrayList<ArrayList<ArrayElement>> arrayList) {
        this.arrayList = arrayList;
    }
}


