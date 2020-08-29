package lesson1.bricks;

import lesson1.circles.Background;
import lesson1.common.CanvasListener;
import lesson1.common.GameCanvas;
import lesson1.common.GameObject;
import lesson1.common.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class  MainBricks extends JFrame implements CanvasListener {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    int spriteArrayLength = 10;
    GameObject[] gameObject = new GameObject[spriteArrayLength];
    private int gameObjectCount;
    Background background;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainBricks();
            }
        });
    }

    private MainBricks() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    addGameObject(new Brick(e.getX(), e.getY()));
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    removeGameObject();
                }
            }
        });

        add(canvas, BorderLayout.CENTER);
        background = new Background();
        initApplication(gameObject);
        setTitle("Шарики");
        setVisible(true);
    }

    private void addGameObject(Sprite s) {
        if (gameObjectCount == gameObject.length) {
            GameObject[] temp = new GameObject[gameObject.length * 2];
            System.arraycopy(gameObject, 0, temp, 0, gameObject.length);
            gameObject = temp;
        }
        gameObject[gameObjectCount++] = s;
    }

    private void removeGameObject() {
        if (gameObjectCount > 1) gameObjectCount--;
    }

    private void initApplication(GameObject[] gameObjects) {
        gameObjects[0] = new Background();
        gameObjectCount++;
    }

    @Override
    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime, gameObject);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime, GameObject[] gameObjects) {
        for (int i = 0; i < gameObjectCount; i++) {
            gameObjects[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < gameObjectCount; i++) {
            gameObject[i].render(canvas, g);
        }
    }
}
