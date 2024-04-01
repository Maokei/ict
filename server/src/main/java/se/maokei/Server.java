package se.maokei;

import com.google.inject.Inject;
import se.maokei.cmd.IParameterFactory;
import se.maokei.cmd.IParameterProvider;
import se.maokei.server.IServerThread;
import se.maokei.server.IServerThreadFactory;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {
  private final IParameterFactory parameterFactory;
  private final IServerThreadFactory serverThreadFactory;


  @Inject
  public Server(IParameterFactory parameterFactory, IServerThreadFactory serverThreadFactory) {
    this.parameterFactory = parameterFactory;
    this.serverThreadFactory = serverThreadFactory;
  }

  /**
   * <P>Type exit to quit</P>
   * */
  public void run(String[]args) throws IOException {
    final Scanner scanner = new Scanner(System.in);
    final IParameterProvider parameters = parameterFactory.getParameters(args);
    final IServerThread serverThread = serverThreadFactory.getServerThread(parameters);
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
