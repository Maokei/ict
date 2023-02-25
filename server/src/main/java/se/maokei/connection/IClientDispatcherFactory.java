package se.maokei.connection;

import se.maokei.core.IClientDispatcher;

public interface IClientDispatcherFactory {
  IClientDispatcher getClientDispatcher(int waitingQueueSize);
}
