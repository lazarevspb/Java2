package lesson1;

import javax.swing.*;
import java.awt.*;

/**
 * Homework for lesson #1
 *
 * 1 Полностью разобраться с кодом
 * 2 Прочитать методичку к следующему уроку
 * 3 Написать класс Бэкграунд, изменяющий цвет канвы в зависимости от времени
 * 4 * Реализовать добавление новых кружков по клику используя ТОЛЬКО массивы
 * 5 ** Реализовать по клику другой кнопки удаление кружков (никаких эррейЛист)
 *
 * @author Valeriy Lazarev
 * @since 26.08.2020
 */
public class MainCircles extends JFrame {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    int spriteArrayLength = 10;
    Background background;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });

    }

    Sprite[] sprites = new Sprite[spriteArrayLength];

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas, BorderLayout.CENTER);
        background = new Background();
        initApplication(sprites);
        setTitle("Шарики");
        setVisible(true);
    }

    private void initApplication(Sprite[] arraySprites) {
        for (int i = 0; i < arraySprites.length; i++) {
            arraySprites[i] = new Ball();
        }
    }

    private void updInitApplication2(Sprite[] arraySprites) {
        for (int i = arraySprites.length - 10; i < arraySprites.length; i++) {
            arraySprites[i] = new Ball();
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime, int addDeleteBall) {
        update(canvas, deltaTime, sprites);
        render(canvas, g, addDeleteBall);
    }

    private void update(GameCanvas canvas, float deltaTime, Sprite[] arraySprites) {
        for (int i = 0; i < arraySprites.length; i++) {
            arraySprites[i].update(canvas, deltaTime);
        }
        background.changeColor(canvas, deltaTime);
    }

    private void render(GameCanvas canvas, Graphics g, int addDeleteBall) {
        if (sprites.length + addDeleteBall - sprites.length > sprites.length - 1) {
            increaseNewArraySprite();
            updInitApplication2(sprites);
        }
        if (addDeleteBall > 0 && (sprites.length + addDeleteBall - sprites.length < sprites.length)) {
            for (int i = 0; i < sprites.length + addDeleteBall - sprites.length; i++) {
                sprites[i].render(canvas, g);
            }
        }
        if (addDeleteBall == 0) {
            for (int i = 0; i < sprites.length - sprites.length; i++) {
                sprites[i].render(canvas, g);
            }
        }
        if (addDeleteBall < 0) {
            for (int i = 0; i < sprites.length + addDeleteBall - sprites.length; i++) {
                sprites[i].render(canvas, g);
            }
            if (sprites.length + addDeleteBall - sprites.length < sprites.length) {
            }
        } else {
        }
    }

    private void increaseNewArraySprite() {
        Sprite[] sprites2;
        spriteArrayLength += 10;
        sprites2 = new Sprite[sprites.length];
        for (int i = 0; i < sprites.length; i++) {
            sprites2[i] = sprites[i];
        }
        sprites = new Sprite[spriteArrayLength];
        for (int i = 0; i < sprites2.length; i++) {
            sprites[i] = sprites2[i];
        }
    }
}
