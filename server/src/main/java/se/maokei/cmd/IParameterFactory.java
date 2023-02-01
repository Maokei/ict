package se.maokei.cmd;

public interface IParameterFactory {
  IParameterProvider getParameters(String[] args);
}
