package se.maokei;

import com.google.inject.AbstractModule;
import se.maokei.cmd.IParameterFactory;
import se.maokei.cmd.ParameterFactory;

public class ServerModule extends AbstractModule {

  @Override
  public void configure() {
    bind(IParameterFactory.class).to(ParameterFactory.class);
  }
}
