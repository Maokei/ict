package se.maokei.core;

import se.maokei.chat.IMessage;
import se.maokei.connection.Client;

public record MessageReceivedEvent(IMessage receivedMessage, Client client) implements IEvent {

    @Override
    public String getEventType() {
        return receivedMessage.getType();
    }
}