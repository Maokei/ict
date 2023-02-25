package se.maokei.connection;

import se.maokei.core.ClientDispatcher;
import se.maokei.core.IClientDispatcher;

public class ClientDispatcherFactory implements IClientDispatcherFactory {
  @Override
  public IClientDispatcher getClientDispatcher(int waitingQueueSize) {
    return new ClientDispatcher(waitingQueueSize);
  }
}
