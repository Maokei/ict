package se.maokei.core;

import se.maokei.connection.Client;

public interface IClientDispatcher extends IThreadControl {
  boolean hasClientInQueue();
  Client getClientFromQueue();
  boolean addClientToQueue(Client client);
}
