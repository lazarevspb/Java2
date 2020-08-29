package lesson1.common;

import java.awt.*;

public interface CanvasListener {
   void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime);
}
