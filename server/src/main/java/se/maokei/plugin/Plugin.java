package se.maokei.plugin;

public enum Plugin {
    HELLO(HelloPlugin.class);
    public final Class<? extends IPlugin> clazz;
    Plugin(Class<? extends IPlugin> clazz) {
        this.clazz = clazz;
    }
}