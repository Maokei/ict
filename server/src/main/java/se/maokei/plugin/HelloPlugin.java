package se.maokei.plugin;

import se.maokei.core.IEventBus;

import java.util.Map;

public class HelloPlugin implements IPlugin {
    @Override
    public String getName() {
        return "HelloPlugin";
    }

    @Override
    public void init() {
        System.out.println("Plugin initialization: " + getName());
    }

    @Override
    public void registerMessageHandlers(IEventBus eventBus) {

    }

    @Override
    public void setupDependencies(Map<String, IPlugin> otherPlugins) {

    }
}
