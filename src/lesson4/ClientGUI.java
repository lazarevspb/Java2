package lesson4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

/**
 * Homework for lesson #4
 * <p>
 * 1. Отправлять сообщения в лог по нажатию кнопки или по нажатию клавиши Enter.
 * 2. Создать лог в файле (показать комментарием, где и как Вы планируете писать
 * сообщение в файловый журнал).
 *
 * @author Valeriy Lazarev
 * @since 06.09.2020
 */

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler, KeyListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int BORDER_SIZE = 5;
    private final JPanel mainPanel = new JPanel();
    private final JTextArea log = new JTextArea();
    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));

    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8181");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("LV");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");
    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("Disconnect");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");
    private final JList<String> userList = new JList();
    private LogFile logFile = new LogFile("log");

    public static void main(String[] args) throws FileNotFoundException {
        new ClientGUI();
    }

    private ClientGUI() throws FileNotFoundException {

        if (logFile.exists()) {
            logFile.readTextFile(tfMessage.getText());
            log.setText(logFile.readFile("log"));
        }

        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        log.setEditable(false);
        JScrollPane scrollUsers = new JScrollPane(userList);
        JScrollPane scrollLog = new JScrollPane(log);
        String[] users = {"user",
                "user",
                "user",
                "user",
                "user",
                "user _with_at_looooooooooooooooooong_name",};
        userList.setListData(users);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addKeyListener(this);

        tfMessage.setBorder(getBorder());
        log.setBorder(getBorder());
        userList.setBorder(getBorder());
        scrollLog.setBorder(getBorder());
        scrollUsers.setBorder(getBorder());
        panelTop.setBorder(getBorder());
        panelBottom.setBorder(getBorder());
        mainPanel.setBorder(getBorder());

        panelTop.add(tfIPAddress);
        panelTop.add(tfPort);
        panelTop.add(cbAlwaysOnTop);
        panelTop.add(tfLogin);
        panelTop.add(tfPassword);
        panelTop.add(btnLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);

        mainPanel.setLayout(new BorderLayout(0, 0));

        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.add(scrollLog, BorderLayout.CENTER);
        mainPanel.add(scrollUsers, BorderLayout.EAST);
        mainPanel.add(panelTop, BorderLayout.NORTH);
        mainPanel.add(panelBottom, BorderLayout.SOUTH);
        add(mainPanel);

        setVisible(true);
    }

    private EmptyBorder getBorder() {
        return new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); //Возвращает обект который создал ActionEvent
        if (src == cbAlwaysOnTop)
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        else if (src == btnSend)
            keyReleased(new KeyEvent(this, KeyEvent.VK_ENTER, System.currentTimeMillis(),
                    KeyEvent.VK_ENTER, '\n', '\n'));
        else
            throw new RuntimeException("Unknown source: " + src);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER
        ) {
            try {
                if (logFile.exists()) {
                    logFile.readTextFile(tfMessage.getText());
                    log.setText(logFile.readFile("log"));
                } else {
                    logFile.createTextFile(tfMessage.getText());
                }
            } catch (FileNotFoundException fileNotFoundException) {
                throw new RuntimeException("File not found");
            }
            tfMessage.setText("");
        }
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
