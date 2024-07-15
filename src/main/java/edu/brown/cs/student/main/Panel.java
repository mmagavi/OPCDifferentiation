package edu.brown.cs.student.main;

import javax.swing.JPanel;
import java.awt.*;

public class Panel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int OriginalCellSize = 8; // 16x16 tile
    final int scale = 1; // scale of tiles

    final int cellSize = OriginalCellSize * scale; // 48x48 tile
    final int maxScreenCol = 139;
    final int maxScreenRow = 98;
    final int screenWidth = cellSize * maxScreenCol;
    final int screenHeight = cellSize * maxScreenRow;

    // How many slices in the array?
    final int depth = 200;
    // Which slice do we want to look at?
    final int currentSlice = 160;
    // What percent full do we want?
    final int percentFull = 1;

    KeyHandler keyHandler = new KeyHandler();
    Thread modelThread;
    CellArray cellArray = new CellArray(maxScreenCol, maxScreenRow, depth);

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startModelThread() {
        modelThread = new Thread(this);
        modelThread.start();
    }

    @Override
    public void run() {

        // fill brain with OPCells
        cellArray.fillBrain(percentFull);
        System.out.println("Before dividing cell count: ");
        System.out.println(cellArray.cellCount(currentSlice));
        cellArray.divide();
        System.out.println("After dividing cell count: ");
        System.out.println(cellArray.cellCount(currentSlice));

        while(modelThread != null) {
            // System.out.println("Model loop is running");
            // 1 UPDATE: Update info information (cell positions)
            update();
            // 2 DRAW: Draw with updated info
            repaint();
        }
    }

    public void update() {
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // probably not necessary
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.WHITE);

        for (int i = 0; i < cellArray.sliceList.get(currentSlice).arrayList.size(); i++) {
            for (int j = 0; j < cellArray.sliceList.get(currentSlice).arrayList.get(0).size(); j++) {
                if (cellArray.sliceList.get(currentSlice).arrayList.get(i).get(j).inBrain) {
                    if (cellArray.sliceList.get(currentSlice).arrayList.get(i).get(j).isCell) {
                        // background rect
                        g2.setColor(Color.WHITE);
                        g2.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                        // render OPC in magenta...
                        g2.setColor(Color.MAGENTA);
                        g2.fillRoundRect(j*cellSize, i*cellSize, cellSize, cellSize, cellSize, cellSize);
                    } else {
                        // else white rect
                        g2.setColor(Color.WHITE);
                        g2.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                    }
                }
            }
        }

        // save memory
        g2.dispose();
    }
}
