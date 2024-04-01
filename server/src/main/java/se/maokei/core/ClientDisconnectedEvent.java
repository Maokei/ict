package se.maokei.core;

import se.maokei.connection.Client;

public class ClientDisconnectedEvent implements IEvent {
    public static final String EVENT_TYPE = "client-disonnected";
    private final Client client;

    public ClientDisconnectedEvent(Client client) {
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