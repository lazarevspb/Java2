package lesson1;

import java.awt.*;

public class Background {
    float timeHolder;

    public Background() {
    }

    public void changeColor(GameCanvas canvas, float deltaTime) {
        timeHolder += deltaTime;
        if (timeHolder >= 1) {
            canvas.setBackground(new Color(
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255),
                    (int) (Math.random() * 255)));
            timeHolder = 0;
        }
    }
}
