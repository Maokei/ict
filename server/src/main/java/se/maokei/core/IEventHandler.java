package se.maokei.core;

@FunctionalInterface
public interface IEventHandler {
    void handleEvent(IEvent event);
}