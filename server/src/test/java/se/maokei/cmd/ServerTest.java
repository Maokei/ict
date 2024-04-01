package se.maokei.cmd;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;
import se.maokei.Server;
import se.maokei.ServerModule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServerTest {

    @Test
    public void startServer() throws IOException {
        /*final Injector injector = Guice.createInjector(new ServerModule());
        Server server = injector.getInstance(Server.class);

        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
        System.setIn(in);
        //https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input
        //https://www.logicbig.com/how-to/junit/java-test-user-command-line-input.html
        server.run(CmdParserTest.PARAMETERS);*/

    }

    @Test
    public void ConnectAndSendMessageTest() {
        //TODO
    }

}
