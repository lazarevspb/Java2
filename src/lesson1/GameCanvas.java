package lesson1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameCanvas extends JPanel {
    private long lastFrameTime;
    private MainCircles controller;
    private int addDeleteBall;

    GameCanvas(MainCircles controller) {
        this.controller = controller;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addDeleteBall++;
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    addDeleteBall--;
                }
            }
        });
        lastFrameTime = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000_000_001f;
        controller.onDrawFrame(this, g, deltaTime, addDeleteBall);
        lastFrameTime = currentTime;
        try {
            Thread.sleep(17);// обновление 60 раз в сек.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    /**
     * Левая граница канвы;
     *
     *
     */
    public int getLeft() {
        return 0;
    }

    public int getRight() {
        return getWidth() - 1;
    }

    public int getTop() {
        return 0;
    }

    public int getBottom() {
        return getHeight() - 1;
    }


}
