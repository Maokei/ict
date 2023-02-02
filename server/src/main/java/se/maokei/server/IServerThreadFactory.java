package se.maokei.server;

import se.maokei.cmd.IParameterProvider;

import java.io.IOException;

public interface IServerThreadFactory {
  IServerThread getServerThread(IParameterProvider parameters) throws IOException;
}
