package se.maokei.connection;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class ConnectionManagerFactory implements IConnectionManagerFactory {
  private final IClientDispatcherFactory clientDispatcherFactory;

  @Inject
  ConnectionManagerFactory(IClientDispatcherFactory clientDispatcherFactory) {
    this.clientDispatcherFactory = clientDispatcherFactory;
  }

  @Override
  public IConnectionManager getConnectionManager(int maxClients, int waitingQueueSize) {
    final ExecutorService pool = Executors.newFixedThreadPool(maxClients);
    return new ConnectionManager(clientDispatcherFactory.getClientDispatcher( waitingQueueSize), pool, maxClients);
  }
}
