package lesson1;

import java.awt.*;

public abstract class Sprite implements GameObject{
    protected float x;
    protected float y;
    protected float halfWidth;
    protected float halfHeight;

    // границы спарйта относительно его координат и его размеров
    protected float getLeft() {
        return x - halfWidth;
    }

    //устанавливаем границы спрайтов
    protected void setLeft(float left) {
        x = left + halfWidth;
    }
    protected float getRight() {
        return x + halfWidth;
    }
    protected void setRight(float right) {
        x = right - halfWidth;
    } // TODO: 29.08.2020 не вызываемый метод 
    protected float getTop() {
        return y - halfHeight;
    }
    protected void setTop(float top) {
        y = top + halfHeight;
    }
    protected float getBottom() {
        return y + halfHeight;
    }
    protected void setBottom(float bottom) {
        y = bottom - halfHeight;
    }
    protected float getWidth() {
        return 2f * halfWidth;
    }
    protected float getHeight() {
        return 2f * halfHeight;
    }


}
