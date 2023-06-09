package se.maokei.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import se.maokei.cmd.CmdParser;
import se.maokei.cmd.IParameterProvider;
import se.maokei.connection.IConnectionManagerFactory;

import java.io.IOException;

@Singleton
public class ServerThreadFactory implements IServerThreadFactory {
  // Default port
  private static final int DEFAULT_SERVER_PORT = 15378;
  // Default maximum number of clients
  private static final int DEFAULT_MAX_CLIENTS = 3;
  // Default waiting queue size
  private static final int DEFAULT_WAITING_QUEUE_SIZE = 1;
  private final IConnectionManagerFactory connectionManagerFactory;

  @Inject
  public ServerThreadFactory(IConnectionManagerFactory connectionManagerFactory) {
    this.connectionManagerFactory = connectionManagerFactory;
  }

  @Override
  public IServerThread getServerThread(IParameterProvider parameters) throws IOException {
    final int port = parameters.getInteger(CmdParser.PORT, DEFAULT_SERVER_PORT);
    final int maxClients = parameters.getInteger(CmdParser.CLIENTS, DEFAULT_MAX_CLIENTS);
    final int waitingQueueSize = parameters.getInteger(CmdParser.MAX_WAITING_QUEUE, DEFAULT_WAITING_QUEUE_SIZE);

    return new ServerThread(connectionManagerFactory.getConnectionManager(maxClients, waitingQueueSize), port);
  }
}
