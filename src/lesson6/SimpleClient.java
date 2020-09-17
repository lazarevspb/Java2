package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8181);
        Scanner scanner =new Scanner(System.in)) {
            System.out.println("Connected to server");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                out.writeUTF(scanner.nextLine());
                String b = in.readUTF();
                System.out.println(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
