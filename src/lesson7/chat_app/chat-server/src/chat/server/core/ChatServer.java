package chat.server.core;

import chat.common.Common;
import chat.network.ServerSocketThread;
import chat.network.ServerSocketThreadListener;
import chat.network.SocketThread;
import chat.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Класс рождает сокеты реализует SSTL, STL. Класс отвечает за события всей серверной части приложения.
 */
public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    public Vector<SocketThread> listOfClients = new Vector<>();
    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss ");
    private ChatServerListener listener;

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (server != null && server.isAlive()) {
            putLog("Server already stared");
        } else {
            server = new ServerSocketThread(this, "Chat server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            putLog("Server is not running");
        } else {
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        listener.onChatServerMessage(msg);
    }

    /**
     * Server Socket Thread Listener methods
     */
    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server started");
        SqlClient.connect();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        listOfClients.remove(thread);
        putLog("Server stopped");
        SqlClient.disconnect();
    }


    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Listening to port");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("Waiting for connection...");
    }


    /**
     * Метод создает новый соектТреад на основе сокета полученный ивентом от ССТЛ, как бы оборачивает сокеты в сокетТреады
     *
     * @param thread
     * @param server
     * @param socket
     */
    @Override
    synchronized public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        String name = "Client " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    synchronized public void broadcastMessage(String msg) {
    }

    /**
     * Socket Thread Listener methods
     */
    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Client thread started");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Client thread stopped");
        listOfClients.remove(thread);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {

        putLog("Client is ready to chat");
        listOfClients.add(thread);
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthMessage(client, msg);
        } else {
            handleNonAuthMessage(client, msg);
        }

    }


    private void handleNonAuthMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Common.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Common.AUTH_REQUEST)) {
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid login attempt: " + login);
            client.authFail();
            return;

        }
        client.authAccept(nickname);
        sendToAllAuthorizedClients(Common.getTypeBroadcast("Server", nickname + " connected"));
    }

    private void handleAuthMessage(ClientThread client, String msg) {

        sendToAllAuthorizedClients(msg);
    }

    private void sendToAllAuthorizedClients(String msg) {
        for (int i = 0; i < listOfClients.size(); i++) {
            ClientThread client = (ClientThread) listOfClients.get(i);
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }
}

