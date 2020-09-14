package chat.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class SocketThread extends Thread {

    private final SocketThreadListener listener;
    private final Socket socket;
    private DataOutputStream out;

    public SocketThread(SocketThreadListener listener, String name, Socket socket) {
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onSocketStart(this, socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()) {
                String msg = "";
                try {
                    msg = in.readUTF();
                    listener.onReceiveString(this, socket, msg);
                } catch (EOFException e) {
                    in.close();
                    out.flush();
                    out.close();
                    interrupt();
                }
            }

        } catch (IOException e) {
            listener.onSocketException(this, e);
        } finally {
            close();
            listener.onSocketStop(this);
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            if (out != null) {
                out.writeUTF(msg);
                out.flush();
            }
            return true;
        } catch (IOException e) {
            System.out.println("sendMessage() " + getClass().getName());
            listener.onSocketException(this, e);
            try {
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            close();
            return false;
        }
    }

    public synchronized void close() {
        interrupt();
        try {
            out.flush();
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }
}
