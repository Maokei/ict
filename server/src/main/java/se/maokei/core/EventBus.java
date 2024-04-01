package se.maokei.core;

import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class EventBus implements IEventBus {
    // Event listeners for individual events will be stored.
    private final Map<String, List<IEventHandler>> listenerMap = new HashMap<>();
    /*
    * The map has
String keys, which we'll get using the getEventType()
method from the IEvent interface. The map value is a collection of
listeners so that multiple listeners can be registered for a single event.
    * */

    @Override
    public void registerEventHandler(String messageType, IEventHandler listener) {
        List<IEventHandler> listeners = listenerMap
                .computeIfAbsent(messageType, k -> new ArrayList<>());
        listeners.add(listener);
    }

    @Override
    public void unregisterEventHandler(String messageType,
                                       IEventHandler listener) {
        final List<IEventHandler> listeners = listenerMap
                .getOrDefault(messageType, Collections.emptyList());
        listeners.remove(listener);
    }

    @Override
    public void publishEvent(IEvent event) {
        final List<IEventHandler> handlers = listenerMap
                .getOrDefault(event.getEventType(), Collections.emptyList());
        handlers.forEach(handler -> handler.handleEvent(event));
    }
}
