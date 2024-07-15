package edu.brown.cs.student.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // SPACE: one division
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        // A: five divisions
        if (code == KeyEvent.VK_A) {
            spacePressed = true;
        }
        // S: ten divisions
        if (code == KeyEvent.VK_S) {
            spacePressed = true;
        }
        // D: fifty divisions
        if (code == KeyEvent.VK_D) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // SPACE: one division
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        // A: five divisions
        if (code == KeyEvent.VK_A) {
            spacePressed = false;
        }
        // S: ten divisions
        if (code == KeyEvent.VK_S) {
            spacePressed = false;
        }
        // D: fifty divisions
        if (code == KeyEvent.VK_D) {
            spacePressed = false;
        }
    }
}
