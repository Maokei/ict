package se.maokei.connection;

public interface IConnectionManagerFactory {
  IConnectionManager getConnectionManager(int maxClients, int waitingQueueSize);
}
