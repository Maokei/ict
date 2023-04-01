package se.maokei.connection;

import se.maokei.chat.IMessage;
import se.maokei.writer.IWriterThread;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Client implements IClient, Runnable {
  private final Socket socket;
  private final ObjectOutputStream writer;
  private final IWriterThread writerThread;
  private ConnectionClosedListener connectionClosedListener;

  Client(Socket socket, IWriterThread writerThread) throws IOException {
    this.socket = socket;
    writer = new ObjectOutputStream(socket.getOutputStream());
    this.writerThread = writerThread;
  }

  @Override
  public void run() {
    try (ObjectInputStream reader = new ObjectInputStream(socket.getInputStream())) {
      IMessage received;
      while ((received = (IMessage) reader.readObject()) != null) {
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
  public void sendMessageAsync(IMessage message) {
    //TODO
  }

  @Override
  public void sendMessage(IMessage message) throws IOException {
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

