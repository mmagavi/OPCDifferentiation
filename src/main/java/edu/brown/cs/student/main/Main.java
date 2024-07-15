package edu.brown.cs.student.main;

import javax.swing.JFrame;

/** Main class runs JFrame */
public class Main {
  public static void main(String[] args) {

    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("OPC Differentiation Model");

    Panel myPanel = new Panel();
    window.add(myPanel);

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    myPanel.startModelThread();
  }
}
