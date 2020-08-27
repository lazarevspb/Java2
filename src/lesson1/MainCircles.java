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
    Background background;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });

    }

    Sprite[] sprites = new  Sprite[10];

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas, BorderLayout.CENTER);
        background = new Background();
        initApplication();
        setTitle("Шарики");
        setVisible(true);

    }

    private void initApplication(){
        for (int i = 0; i < sprites.length ; i++) {
            sprites[i] = new Ball();
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime){

        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime){
        for(int i = 0; i < sprites.length; i++){
            sprites[i].update(canvas, deltaTime);

        }
        background.changeColor(canvas, deltaTime);

    }

     private void render(GameCanvas canvas, Graphics g){
         for (int i = 0; i < sprites.length; i++) {
             sprites[i].render(canvas, g);
         }

     }

}
