package lesson4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGui extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {

    public static final int POS_X = 1000;
    public static final int POS_Y = 550;
    public static final int WIDTH = 200;
    public static final int HEIGHT = 100;

    private final ChatServer chatServer = new ChatServer();
    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");

    public static void main(String[] args) {
        /*Создается окно в потоке event dispatching thread (EDT) */
        SwingUtilities/*Класс*/.invokeLater/*Статический метод*/(/*аргумент*/ new Runnable() {
            /*Вызов конструктора, создание нового объекта, реализующего интерфейс(анонимный класс),
            передается в качестве рагумента*/
            @Override
            public void run()/*переопределяемый метод взятый из интерфейса Runnable()*/ {
                new ServerGui();/*Создаем объект, вызывая конструктор-*/
            }
        });


    }

    private ServerGui() {
        Thread.setDefaultUncaughtExceptionHandler(this); //Обработчик непойманных исключений по умолчанию
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        setLayout(new GridLayout(1, 2));

        btnStop.addActionListener(this);
        btnStart.addActionListener(this);

        add(btnStart);
        add(btnStop);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); //Возвращает обект который создал ActionEvent
        if (src == btnStop)
            chatServer.stop();
        else if (src == btnStart)
//            chatServer.start(8181);
            throw new RuntimeException("Hello from EDT");
        else
            throw new RuntimeException("Unknown source: " + src);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        msg = "Exception in " + t.getName() + " " +
                e.getClass().getCanonicalName() + ": " +
                e.getMessage() + "\n\t at" + ste[0];
        JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
