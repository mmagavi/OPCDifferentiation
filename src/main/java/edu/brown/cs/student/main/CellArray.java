package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Will create & manipulate the array
 * Array will be rendered in Panel
 */
public class CellArray {
    public ArrayList<Slice> sliceList;
    int numCol;
    int numRow;
    // Filepath to a CSV file containing a 138 x 98 array of x and os
    // An exception will be thrown if the csv size does not match specified size
    String filepath = "data/brainLayout.csv";

    /**
     * Constructor
     * @param numCol number of columns
     * @param numRow number of rows
     */
    public CellArray(int numCol, int numRow, int depth) {
        this.numCol = numCol;
        this.numRow = numRow;

        // populate arrayList using brain layout csv
        ArrayList<ArrayList<ArrayElement>> arrayList = new ArrayList<>();
        this.sliceList = new ArrayList<>();
        // create an instance of BufferedReader
        try (BufferedReader br = Files.newBufferedReader(Path.of(filepath), StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {
                // init a new inner arraylist
                ArrayList<ArrayElement> innerList = new ArrayList<>();

                // use string.split to load a string array with the values from each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                if (attributes.length != numCol) {
                    throw new IllegalArgumentException("Invalid brain csv width");
                }

                for (String attribute : attributes) {
                    if (Objects.equals(attribute, "x")) {
                        innerList.add(new ArrayElement(false, false));
                    } else if (Objects.equals(attribute, "o")) {
                        innerList.add(new ArrayElement(false, true));
                    } else {
                        throw new IllegalArgumentException("Invalid brain layout csv - non x/o values");
                    }
                }
                arrayList.add(innerList);

                line = br.readLine();
            }

            // add slices to sliceList
            for (int i = 0; i < depth; i++) {
                sliceList.add(new Slice(arrayList));
            }

            if (arrayList.size() != numRow) {
                throw new IllegalArgumentException("Invalid brain csv height");
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Will eventually populate the brain region with cells
     * @param percent
     */
    public void fillBrain(int percent) {

        Random rand = new Random();

        // for each slice in the sliceList
        for (Slice slice : sliceList) {
            // for each row in the arrayList
            for (ArrayList<ArrayElement> arrayElements : slice.arrayList) {
                // for each element in the row
                for (int j = 0; j < slice.arrayList.get(0).size(); j++) {
                    // if cell is in brain
                    if (arrayElements.get(j).inBrain) {
                        // pick random number between 0 and 100
                        int randomNum = rand.nextInt(100);
                        // if it is less than the percent, set the element to be a cell
                        if (randomNum < percent) {
                            arrayElements.get(j).setCell();
                        } else {
                            arrayElements.get(j).setEmpty();
                        }
                    }
                }
            }
            // if we are at the last slice
            if (sliceList.get(sliceList.size() - 1) == slice) {
                System.out.println("last slice filled");
            }
        }
    }

    /**
     * Counts the number of cells in the brain in that slice
     */
    public int cellCount(int sliceNum) {

        int count = 0;

        for (int i = 0; i < sliceList.get(sliceNum).arrayList.size(); i++) {
            for (int j = 0; j < sliceList.get(sliceNum).arrayList.get(0).size(); j++) {
                if (sliceList.get(sliceNum).arrayList.get(i).get(j).isCell && sliceList.get(sliceNum).arrayList.get(i).get(j).inBrain) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * One division in OPC tree
     */
    public void divide() {
        // Random rand = new Random();

        // for each slice in the sliceList
        for (int h = 0; h < sliceList.size(); h++) {
            // for each row in the arrayList
            for (int i = 0; i < sliceList.get(h).arrayList.size(); i++) {
                // for each element in the row
                for (int j = 0; j < sliceList.get(h).arrayList.get(0).size(); j++) {
                    // if in brain & is cell...

                    //System.out.println("i: " + i + " j: " + j + " h: " + h);
                    //System.out.println("checking if is in brain & is cell...");
                    if (sliceList.get(h).arrayList.get(i).get(j).inBrain && sliceList.get(h).arrayList.get(i).get(j).isCell) {

                        //System.out.println("is in brain & is cell");

                        // Tries all adjacent cells
                        // TODO: fix goes in a set order, so it will always try the same cells first
                        boolean found = false;
                        while(!found) {
                            for (int k = -1; k < 2; k++) {
                                for (int l = -1; l < 2; l++) {
                                    for (int m = -1; m < 2; m++) {

                                        // dont try oob cells
                                        if (h == 0 && m == -1) {
                                            continue;
                                        } else if (h == sliceList.size() - 1 && m == 1) {
                                            continue;
                                        } else if (i + k < 0 || i + k >= sliceList.get(h + m).arrayList.size()) {
                                            continue;
                                        } else if (j + l < 0 || j + l >= sliceList.get(h + m).arrayList.get(0).size()) {
                                            continue;
                                        }

                                        // if space is empty...
                                        if (!sliceList.get(h + m).arrayList.get(i + k).get(j + l).isCell) {
                                            sliceList.get(h + m).arrayList.get(i + k).get(j + l).setCell();
                                            found = true;
                                            //System.out.println("divided");
                                        }
                                    }
                                }
                            }
                        }

                        // left while loop
                        //System.out.println("Left while loop, continuing to next cell");

                    } else {
                        //System.out.println("not in brain or not cell");
                    }

                }
            }
        }
    }
}
