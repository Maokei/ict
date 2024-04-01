package se.maokei;

import com.google.inject.Inject;
import se.maokei.cmd.IParameterFactory;
import se.maokei.cmd.IParameterProvider;
import se.maokei.core.IEventBus;
import se.maokei.plugin.IPlugin;
import se.maokei.server.IServerThread;
import se.maokei.server.IServerThreadFactory;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
  private final IParameterFactory parameterFactory;
  private final IServerThreadFactory serverThreadFactory;
  private final IEventBus eventBus;
  private final Map<String, IPlugin> plugins;


  @Inject
  public Server(IParameterFactory parameterFactory, IServerThreadFactory serverThreadFactory,
                IEventBus eventBus, Map<String, IPlugin> plugins) {
    this.parameterFactory = parameterFactory;
    this.serverThreadFactory = serverThreadFactory;
    this.eventBus = eventBus;
    this.plugins = plugins;
  }

  private void initPlugins() {
    for (IPlugin plugin: plugins.values()) {
      plugin.init();
    }
    for (IPlugin plugin: plugins.values()) {
      plugin.registerMessageHandlers(this.eventBus);
    }
    for (IPlugin plugin: plugins.values()) {
      plugin.setupDependencies(plugins);
    }
  }

  /**
   * <P>Type exit to quit</P>
   * */
  public void run(String[]args) throws IOException {
    final Scanner scanner = new Scanner(System.in);
    final IParameterProvider parameters = parameterFactory.getParameters(args);
    final IServerThread serverThread = serverThreadFactory.getServerThread(parameters);
    this.initPlugins();
    serverThread.start();

    while(true) {
      String input = "";
      try {
        input = scanner.nextLine();
      } catch(NoSuchElementException e) {

      }
      if ("exit".equals(input)) {
        break;
      }
    }
    serverThread.shutdown();
  }
}
