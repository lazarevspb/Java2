package lesson1;

import java.awt.*;

public interface GameObject {
   void update(GameCanvas canvas, float deltaTime) ;
   void render(GameCanvas canvas, Graphics g);
}
