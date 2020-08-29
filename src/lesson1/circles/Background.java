package lesson1.circles;

import lesson1.common.GameCanvas;
import lesson1.common.GameObject;

import java.awt.*;

public class Background implements GameObject {
    private float time;
    private static final float AMPLITUDE = 255f / 2;
    private Color color;

    @Override
    public void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }

    @Override
    public void update(GameCanvas canvas, float deltaTime) {
        time += deltaTime;
        int red = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time * 2f));
        int green = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time * 3f));
        int blue = Math.round(AMPLITUDE + AMPLITUDE * (float) Math.sin(time * 0.5f));
        color = new Color(red, green, blue);
    }
}
