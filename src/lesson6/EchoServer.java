package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        System.out.println("server started");
        try (ServerSocket serverSocket = new ServerSocket(8181);
             Socket client = serverSocket.accept()){ //Ждем клиента
            System.out.println("Client connected");
            DataInputStream in =new DataInputStream    (client.getInputStream()); //подключаемся к потокам ввода вывода клиента
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while (true) {
                String b = in.readUTF(); //ждем байи данных от клиента
                out.writeUTF("Echo: " + b); //когда дождались дописываем 1 и отправляем обратно в поток ввода вывода
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
