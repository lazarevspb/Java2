package lesson4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

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

public class ClientGUI extends JFrame implements ActionListener, Thread.UncaughtExceptionHandler {
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
    private boolean shownIoErrors = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    private ClientGUI() {

        if (logFile.exists()) {
            try {
                logFile.addTextToFile(tfMessage.getText());
                log.setText(logFile.readFile("log"));
                log.setCaretPosition(log.getDocument().getLength());
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File log not found");
            }
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
//        tfMessage.addKeyListener(this);
        tfMessage.addActionListener(this);

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
        else if (src == btnSend || src == tfMessage)
            sendMessage();
        else
            throw new RuntimeException("Unknown source: " + src);
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        String username = tfLogin.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        putLog(String.format("%s: %s", username, msg));
        wrtMsgToLogFile(msg, username);
    }

    private void wrtMsgToLogFile(String msg, String username) {
        try (FileWriter out = new FileWriter("log", true)) {
            out.write(username + ": " + msg + "\n");
            out.flush();
        } catch (IOException e) {
            if (!shownIoErrors) {
                shownIoErrors = true;
                showException(Thread.currentThread(), e);
            }
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(() -> {
            log.append(msg + "\n");
            log.setCaretPosition(log.getDocument().getLength());//Устанавливает каретку на конец документа
        });
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, msg, "Exception", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }
}
