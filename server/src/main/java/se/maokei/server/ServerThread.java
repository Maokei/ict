package se.maokei.server;

import se.maokei.connection.IConnectionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerThread extends Thread implements IServerThread {
  private static final int SOCKET_TIMEOUT = 5000;
  private final int port;
  private final IConnectionManager connectionManager;
  private boolean running = false;


  public ServerThread(IConnectionManager connectionManager, int port) throws IOException {
    super("ServerThread");
    this.connectionManager = connectionManager;
    this.port = port;
    //TODO running never set to true client unable to connect
  }

  @Override
  public void shutdown() {
    running = false;
    try {
      join();
    } catch (InterruptedException ignored) {}
  }

  /**
   * run
   * <p>Method creates a new ServerSocket class
   * instance and sets its timeout to the value of
   * SOCKET_TIMEOUT, which is 5 seconds.</p>
   * */
  @Override
  public void run() {
    connectionManager.onServerStart();
    this.running = true;
    System.out.println("ServerThread started listening to port: " + port);
    System.out.println("ServerThread state: " + running);
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      serverSocket.setSoTimeout(SOCKET_TIMEOUT);
      while (running) {
        try {
          final Socket socket = serverSocket.accept();
          System.out.println("sever soccket accept");
          connectionManager.addClient(socket);
        } catch (SocketTimeoutException ignored) {}
      }
    } catch (IOException e) {
      System.out.println("Unable to start server on port: " + port);
      e.printStackTrace();
    }
    connectionManager.onServerStop();
  }
}
