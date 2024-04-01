package se.maokei.connection;

import se.maokei.core.*;
import se.maokei.writer.IWriterThread;

import javax.inject.Inject;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ConnectionManager implements IConnectionManager {
  //private static Logger logger = LogManager.getLogger(ConnectionManager.class);

  private final List<Client> clients = new ArrayList<>();
  private final IEventBus eventBus;
  private final ExecutorService pool;
  final int maxClients;
  private final IClientDispatcher clientDispatcher;
  private final IWriterThread writerThread;

  @Inject
  public ConnectionManager(IClientDispatcher clientDispatcher, IWriterThread writerThread,
                           IEventBus eventBus, ExecutorService pool, int maxClients) {
    this.clientDispatcher = clientDispatcher;
    this.writerThread = writerThread;
    this.eventBus = eventBus;
    this.pool = pool;
    this.maxClients = maxClients;
  }

  private synchronized void insertClientToListOrQueue(Client client) {
    if (clients.size() < maxClients) {
      clients.add(client);
      client.setConnectionClosedListener(() -> {
        clients.remove(client);
        // Create a new Disconnect Event
        eventBus.publishEvent(new ClientDisconnectedEvent(client));
        if (clientDispatcher.hasClientInQueue()) {
          this.insertClientToListOrQueue(clientDispatcher.getClientFromQueue());
        }
      });
      pool.submit(client);
      eventBus.publishEvent(new ClientConnectedEvent(client));
    } else {
      if (!clientDispatcher.addClientToQueue(client)) {
        client.close();
      }
    }
  }

  @Override
  public void addClient(Socket socket) throws IOException {
    System.out.println("ConnectionManager, new socket" + socket.getInetAddress());
    insertClientToListOrQueue(new Client(socket, writerThread, this.eventBus));
  }

  @Override
  public void onServerStart() {
    System.out.println("Starting ClientDispatcher and WriterThread");
    clientDispatcher.start();
    writerThread.start();
  }

  @Override
  public void onServerStop() {
    clientDispatcher.shutdown();
    writerThread.shutdown();
    for (IClient client : clients) {
      client.close();
    }
    pool.shutdown();
  }
}
