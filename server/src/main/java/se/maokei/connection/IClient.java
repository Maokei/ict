package se.maokei.connection;

import se.maokei.chat.IMessage;

import java.io.IOException;

public interface IClient {
  void sendMessageAsync(IMessage message);
  void sendMessage(IMessage message) throws IOException;
  void close();
}
