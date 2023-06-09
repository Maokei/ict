package se.maokei.connection;

import java.io.IOException;
import java.net.Socket;

public interface IConnectionManager {
  void addClient(Socket socket) throws IOException;
  void onServerStart();
  void onServerStop();
}
