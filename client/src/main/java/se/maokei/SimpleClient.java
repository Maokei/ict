package se.maokei;

import se.maokei.chat.IMessage;
import se.maokei.chat.TextMessage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleClient {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello from main test client");
    try (Socket socket = new Socket("localhost", 6298)) {
      Thread.sleep(1000);
      ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
      writer.writeObject(new TextMessage("Hello from client."));
      writer.flush();
      ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
      System.out.println(((IMessage) reader.readObject()).getData().toString());
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("SimpleClient done");
    }
  }
}