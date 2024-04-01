package se.maokei;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

public class EchoClient {

    //private static final Logger LOG = LoggerFactory.getLogger(EchoClient.class);

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            //LOG.debug("Error when initializing connection", e);
            System.out.println("EchClient could not establish connection!");
        }

    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch (Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            //LOG.debug("error when closing", e);
        }

    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        client.startConnection("127.0.0.1", 6298); //5555
        System.out.println("sngMsg: " + client.sendMessage("echo hello"));
        client.stopConnection();
    }
}