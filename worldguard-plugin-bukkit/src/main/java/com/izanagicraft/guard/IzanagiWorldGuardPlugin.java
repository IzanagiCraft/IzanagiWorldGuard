package com.izanagicraft.guard;

import com.izanagicraft.guard.commands.GuardCommand;
import com.izanagicraft.guard.events.listener.DisableMessageBroadcast;
import com.izanagicraft.guard.events.listener.WorldLoadEventListener;
import com.izanagicraft.guard.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard:IzanagiWorldGuardPlugin
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class IzanagiWorldGuardPlugin extends JavaPlugin {

    private static IzanagiWorldGuardPlugin instance;

    public static IzanagiWorldGuardPlugin getInstance() {
        return instance;
    }

    private ExecutorService executor;
    private ScheduledExecutorService scheduler;

    private Map<String, YamlConfiguration> worldConfigs;
    private Map<String, File> worldConfigFiles;

    // TODO: region specific config loading
    private Map<String, YamlConfiguration> regionConfigs;
    private Map<String, File> regionConfigFiles;

    @Override
    public void onLoad() {
        // throw runtime exception to stop plugin
        if (instance != null) {
            throw new RuntimeException("This plugin can only be initialized once... Please restart the server.");
        }
        instance = this;

        MessageUtils.sendPrefixedMessage(Bukkit.getConsoleSender(), "Guard loaded...");
    }

    @Override
    public void onEnable() {
        this.loadConfigurations();
        this.prepare();
        this.loadProviders();
        this.loadCommands();
        this.loadEvents();
        this.startSchedulers();

        MessageUtils.sendPrefixedMessage(Bukkit.getConsoleSender(), "Guard enabled...");
    }

    @Override
    public void onDisable() {
        this.saveConfigurations();

        MessageUtils.sendPrefixedMessage(Bukkit.getConsoleSender(), "Guard disabled...");
    }

    private void saveConfigurations() {
        getWorldConfigs().keySet().forEach(worldName -> {
            if (getWorldConfigFiles().containsKey(worldName)) {
                try {
                    getWorldConfigs().get(worldName).save(getWorldConfigFiles().get(worldName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadConfigurations() {
        this.saveDefaultConfig();
        this.reloadConfigurations();
    }

    private void reloadConfigurations() {
        this.reloadConfig();
    }

    private void prepare() {
        this.executor = Executors.newFixedThreadPool(this.getConfig().getInt("threading.poolSize", 64));
        this.scheduler = Executors.newScheduledThreadPool(this.getConfig().getInt("threading.corePoolSize", 8));
        this.worldConfigs = new ConcurrentHashMap<>();
        this.worldConfigFiles = new ConcurrentHashMap<>();

        Bukkit.getScheduler().cancelTasks(this);

    }

    private void loadProviders() {
    }

    private void loadCommands() {
        CommandMap commandMap = Bukkit.getCommandMap();
        List.of(
                // TODO: add all commands
        ).forEach(command -> commandMap.register("guard", (GuardCommand) command));
    }

    private void loadEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List.of(
                new DisableMessageBroadcast(this),
                new WorldLoadEventListener(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    private void startSchedulers() {
        // save configs each 5 minutes to keep it up to date
        this.scheduler.scheduleAtFixedRate(this::saveConfigurations, 0, 5, TimeUnit.MINUTES);
    }

    public Map<String, YamlConfiguration> getWorldConfigs() {
        return worldConfigs;
    }

    public Map<String, File> getWorldConfigFiles() {
        return worldConfigFiles;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
