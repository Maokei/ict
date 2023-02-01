package se.maokei;

import com.google.inject.Inject;
import se.maokei.cmd.CmdParser;
import se.maokei.cmd.IParameterFactory;
import se.maokei.cmd.IParameterProvider;

public class Server {
  private final IParameterFactory parameterFactory;

  @Inject
  public Server(IParameterFactory parameterFactory) {
    this.parameterFactory = parameterFactory;
  }

  public void run(String[] args) {
    final IParameterProvider parameters = parameterFactory.getParameters(args);
    System.out.println("Maximum number of clients: " + parameters.getInteger(CmdParser.CLIENTS));
  }
}
