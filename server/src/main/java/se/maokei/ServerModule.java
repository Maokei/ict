package se.maokei;

import com.google.inject.AbstractModule;
import se.maokei.cmd.IParameterFactory;
import se.maokei.cmd.ParameterFactory;
import se.maokei.connection.ClientDispatcherFactory;
import se.maokei.connection.ConnectionManagerFactory;
import se.maokei.connection.IClientDispatcherFactory;
import se.maokei.connection.IConnectionManagerFactory;
import se.maokei.server.IServerThreadFactory;
import se.maokei.server.ServerThreadFactory;
import se.maokei.writer.IWriterThread;
import se.maokei.writer.WriterThread;

public class ServerModule extends AbstractModule {

  @Override
  public void configure() {
    bind(IParameterFactory.class).to(ParameterFactory.class);
    bind(IServerThreadFactory.class).to(ServerThreadFactory.class);
    bind(IConnectionManagerFactory.class).to(ConnectionManagerFactory.class);
    bind(IClientDispatcherFactory.class).to(ClientDispatcherFactory.class);
    bind(IWriterThread.class).to(WriterThread.class);
  }
}
