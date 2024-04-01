package se.maokei.cmd;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CmdParserTest {
  public static final String[] PARAMETERS = {
      "-port=6298", "-clients=5", "-max_waiting_queue=5", "name=test"
  };

  private static IParameterProvider parameterProvider;

  @BeforeAll
  public static void setUp() {
    parameterProvider = new CmdParser(PARAMETERS);
  }

  @Test
  public void getStringTest() {
    final String key = "name";
    final String name = "test";
    assertEquals(name, parameterProvider.getString(key), "Error, server name value mismatch.");
  }

  @Test
  public void getStringNegativeTest() {
    final String key = "unknown";
    final String value = CmdParser.DEFAULT_STRING;
    assertEquals(value, parameterProvider.getString(key), "Error, wrongly defined default value.");
  }

  @Test
  public void getIntegerTest() {
    final String key = "port";
    final int value = 6298;
    assertEquals(value, parameterProvider.getInteger(key),"Error, port value mismatch.");
  }

  @Test
  public void getIntegerNegativeTest() {
    final String key = "unknown";
    final int value = CmdParser.DEFAULT_INTEGER;
    assertEquals(value, parameterProvider.getInteger(key), "Error, wrongly defined default value.");
  }
}
