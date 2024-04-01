package se.maokei.core;

import se.maokei.connection.Client;

public class ClientConnectedEvent implements IEvent {
    public static final String EVENT_TYPE = "client-connected";
    private final Client client;

    public ClientConnectedEvent(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String getEventType() {
        return EVENT_TYPE;
    }
}