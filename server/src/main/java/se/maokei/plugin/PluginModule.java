package se.maokei.plugin;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

public class PluginModule extends AbstractModule {

    @Override
    protected void configure() {
        MapBinder < String, IPlugin > pluginBinder = MapBinder.newMapBinder(binder(), String.class, IPlugin.class);
        for (Plugin plugin: Plugin.values()) {
            pluginBinder.addBinding(plugin.name()).to(plugin.clazz).asEagerSingleton();
        }

        // TODO load external plugins
    }
}