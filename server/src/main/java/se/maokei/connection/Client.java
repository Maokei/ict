package se.maokei.connection;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client implements IClient, Runnable {
  private final Socket socket;
  private final ObjectOutputStream writer;
  private ConnectionClosedListener connectionClosedListener;

  public Client(Socket socket) throws IOException {
    this.socket = socket;
    writer = new ObjectOutputStream(socket.getOutputStream());
  }

  @Override
  public void run() {
    try (ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
      Object received;
      while ((received = reader.readObject()) != null) {
        // TODO process the received message
      }
    } catch (EOFException | SocketException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // Should never happen
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connectionClosedListener != null) {
        connectionClosedListener.onConnectionClosed();
      }
      close();
    }
  }

  @Override
  public void sendMessageAsync(Object message) {
    //TODO
  }

  @Override
  public void sendMessage(Object message) throws IOException {
    writer.writeObject(message);
  }

  @Override
  public void close() {
    try {
      socket.close();
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  @FunctionalInterface
  public interface ConnectionClosedListener {
    void onConnectionClosed();
  }

  void setConnectionClosedListener(ConnectionClosedListener connectionClosedListener) {
    this.connectionClosedListener = connectionClosedListener;
  }

  public ObjectOutputStream getWriter() {
    return writer;
  }
}

