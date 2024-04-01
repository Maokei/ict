package se.maokei;

import com.google.inject.Guice;
import com.google.inject.Injector;
import se.maokei.plugin.PluginModule;

public class Main {
  public static void main(String[] args) throws Exception {
    final Injector injector = Guice.createInjector(new ServerModule(), new PluginModule());
    Server server = injector.getInstance(Server.class);
    System.out.println("Starting server!");
    server.run(args);
  }
}