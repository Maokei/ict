package se.maokei.plugin;

import se.maokei.core.IEventBus;

import java.util.Map;

public interface IPlugin {
    String getName();
    void init();
    void registerMessageHandlers(IEventBus eventBus);
    void setupDependencies(Map<String, IPlugin> otherPlugins);
}