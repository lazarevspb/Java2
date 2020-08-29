package lesson1.common;

import lesson1.circles.MainCircles;

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
    private long lastFrameTime;
    private CanvasListener controller;

    public GameCanvas(CanvasListener controller) {
        this.controller = controller;
        lastFrameTime = System.nanoTime();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000_000_001f;
        controller.onDrawFrame(this, g, deltaTime);
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
