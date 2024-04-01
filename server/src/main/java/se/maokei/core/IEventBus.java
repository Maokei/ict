package se.maokei.core;

public interface IEventBus {
    void registerEventHandler(String messageType, IEventHandler listener);
    void unregisterEventHandler(String messageType, IEventHandler listener);
    void publishEvent(IEvent event);
}