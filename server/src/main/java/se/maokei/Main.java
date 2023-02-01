package se.maokei;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
  public static void main(String[] args) throws Exception {
    final Injector injector = Guice.createInjector(new ServerModule());
    Server server = injector.getInstance(Server.class);
    server.run(args);
  }
}