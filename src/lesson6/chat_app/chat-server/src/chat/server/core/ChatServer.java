package chat.server.core;

import chat.network.ServerSocketThread;
import chat.network.ServerSocketThreadListener;
import chat.network.SocketThread;
import chat.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    public Vector<SocketThread> listOfClients = new Vector<>(10);
    private ServerSocketThread server;
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss ");

    public void start(int port) {
        if (server != null && server.isAlive()) {
            System.out.println("Server already started");
        } else {
            server = new ServerSocketThread(this, "Chat server", port, 2000);
        }
    }

    public void stop() {
        if (server == null || !server.isAlive()) {
            System.out.println("Server is not running");
        } else {
            server.interrupt();
        }
    }

    private void putLog(String msg) {
        msg = DATE_FORMAT.format(System.currentTimeMillis()) +
                Thread.currentThread().getName() + ": " + msg;
        System.out.println(msg);
    }

    /**
     * Server Socket Thread Listener methods
     */
    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server started");
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server stopped");
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Listening to port");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) {
        putLog("Waiting for connection...");
    }

    @Override
    synchronized public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        // client connected

        String name = "Client " + socket.getInetAddress() + ":" + socket.getPort();
        listOfClients.add(new SocketThread(this, name, socket));
        putLog("Client: " + (listOfClients.size()));

        if ((listOfClients.size()) > 1) {
            broadcastMessage("New user connected");
        }
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    @Override
    synchronized public void broadcastMessage(String msg) {
        for (SocketThread socketThread : listOfClients
        )
            if (socketThread != null) {
                socketThread.sendMessage(msg);
            }
    }

    /**
     * Socket Thread Listener methods
     */
    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Client thread started");
    }

    @Override
    synchronized public void onSocketStop(SocketThread thread) {
        listOfClients.remove(listOfClients.size() - 1);
        thread.close();
        putLog("Client thread stopped " + (listOfClients.size()));
        broadcastMessage("User disconnected ");
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Client is ready to chat");
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        thread.sendMessage("echo: " + msg);
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }
}

