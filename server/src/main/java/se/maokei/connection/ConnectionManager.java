package se.maokei.connection;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ConnectionManager implements IConnectionManager {
  private final List<Client> clients = new ArrayList<>();
  private final ExecutorService pool;
  final int maxClients;

  @Inject
  public ConnectionManager(ExecutorService pool, int maxClients) {
    this.pool = pool;
    this.maxClients = maxClients;
  }

  private synchronized void insertClientToListOrQueue(Client client) {
    if (clients.size() < maxClients) {
      clients.add(client);
      client.setConnectionClosedListener(() -> {
        clients.remove(client);
      });
      pool.submit(client);
    } else {
      // TODO add the client to the waiting queue
    }
  }

  @Override
  public void addClient(Socket socket) throws IOException {
    insertClientToListOrQueue(new Client(socket));
  }

  @Override
  public void onServerStart() {
    //TODO
  }

  @Override
  public void onServerStop() {
    for (IClient client : clients) {
      client.close();
    }
    pool.shutdown();
  }
}
