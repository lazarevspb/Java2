package chat.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class SocketThread extends Thread {

    private final SocketThreadListener listener;
    private final Socket socket;
    private DataOutputStream out;
    private  Vector<Socket> listOfClients;

    public Socket getSocket() {
        return socket;
    }

    public SocketThreadListener getListener() {
        return listener;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public Vector<Socket> getListOfClients() {
        return listOfClients;
    }

    //    public SocketThread(SocketThreadListener listener, String name, Socket socket, Vector<Socket> listOfClients) {
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
                String msg = null;
                try {
                    msg = in.readUTF();
                listener.onReceiveString(this, socket, msg);
                } catch (EOFException e) {
                    in.close();
                    out.flush();
                    out.close();
                    interrupt();
                    System.out.println("in.close(); ЗОКРЫТО ");
                }

            }

        } catch (IOException e) {
            listener.onSocketException(this, e);
            System.out.println(" run() 666666666 " + getName());
        } finally {
            close();
            listener.onSocketStop(this);
            System.out.println(" run() 777777777 " + getName());
        }
    }

    public synchronized boolean sendMessage(String msg) {


            try {
                if(out != null){
                    out.writeUTF(msg);
                    out.flush();
                }
                return true;
            } catch (IOException e) {
                System.out.println("sendMessage() " + getClass().getName());

                listener.onSocketException(this, e);
                System.out.println("sendMessage 11111111111" + getClass().getName());
                try {
                    out.close();
                } catch (IOException ioException) {
                    System.out.println("sendMessage 11111111111" + getClass().getName());
                    ioException.printStackTrace();
                }
                close();
                return false;
            }

    }

    public synchronized void close() {

        interrupt();

        try {
//            this.out.flush();
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }



}
