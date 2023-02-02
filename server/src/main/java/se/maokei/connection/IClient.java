package se.maokei.connection;

import java.io.IOException;

public interface IClient {
  void sendMessageAsync(Object message);
  void sendMessage(Object message) throws IOException;
  void close();
}
