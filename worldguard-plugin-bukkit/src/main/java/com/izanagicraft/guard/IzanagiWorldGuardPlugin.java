/*
 * ▪  ·▄▄▄▄• ▄▄▄·  ▐ ▄  ▄▄▄·  ▄▄ • ▪   ▄▄· ▄▄▄   ▄▄▄· ·▄▄▄▄▄▄▄▄
 * ██ ▪▀·.█▌▐█ ▀█ •█▌▐█▐█ ▀█ ▐█ ▀ ▪██ ▐█ ▌▪▀▄ █·▐█ ▀█ ▐▄▄·•██
 * ▐█·▄█▀▀▀•▄█▀▀█ ▐█▐▐▌▄█▀▀█ ▄█ ▀█▄▐█·██ ▄▄▐▀▀▄ ▄█▀▀█ ██▪  ▐█.▪
 * ▐█▌█▌▪▄█▀▐█ ▪▐▌██▐█▌▐█ ▪▐▌▐█▄▪▐█▐█▌▐███▌▐█•█▌▐█ ▪▐▌██▌. ▐█▌·
 * ▀▀▀·▀▀▀ • ▀  ▀ ▀▀ █▪ ▀  ▀ ·▀▀▀▀ ▀▀▀·▀▀▀ .▀  ▀ ▀  ▀ ▀▀▀  ▀▀▀
 *
 *
 *    @@@@@
 *    @@* *@@
 *      @@@  @@@
 *         @@@  @@ @@@       @@@@@@@@@@@
 *           @@@@@@@@   @@@@@@@@@@@@@@@@@@@@@
 *            @@@    @@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *               #@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *               #@@@   @@                 @@  @@@@  @@@@
 *                @@@@      @@@      @@@@      @@@@   @@@
 *                @@@@@@                     @@@@@@    @@
 *                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                   @@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                     @@@@@@@@@@@@@@@@@@@@@@@
 *                       @@@@@@@@@@@@@@@@@@@
 *                           @@@@@@@@@@@
 *
 * Copyright (c) 2023 - present | sanguine6660 <sanguine6660@gmail.com>
 * Copyright (c) 2023 - present | izanagicraft.com <contact@izanagicraft.com>
 * Copyright (c) 2023 - present | izanagicraft.com team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.izanagicraft.guard;

import com.izanagicraft.guard.commands.GuardCommand;
import com.izanagicraft.guard.commands.WorldGuardCommand;
import com.izanagicraft.guard.events.listener.*;
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
 * <p>
 * Main class for the IzanagiWorldGuard plugin.
 * <p>
 * This class extends the Bukkit {@link JavaPlugin} class and serves as the entry point for the plugin.
 * It manages the initialization, configuration loading, event registration, command registration,
 * and scheduling of tasks for the IzanagiWorldGuard plugin.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class IzanagiWorldGuardPlugin extends JavaPlugin {

    private static IzanagiWorldGuardPlugin instance;

    /**
     * Gets the singleton instance of the IzanagiWorldGuardPlugin.
     *
     * @return The instance of the plugin.
     */
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
        try {
            this.loadConfigurations();
        } catch (IOException e) {
            MessageUtils.sendPrefixedMessage(Bukkit.getConsoleSender(), "&cGuard cannot be enabled... Cannot load configs....");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
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

    /**
     * Saves configurations for all loaded worlds.
     */
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

    /**
     * Loads plugin configurations, including default configurations and reloads existing ones.
     */
    private void loadConfigurations() throws IOException {
        this.saveDefaultConfig();
        this.reloadConfigurations();
    }

    /**
     * Reloads plugin configurations, including the main configuration file.
     *
     * @throws IOException if the configs could not been reloaded.
     */
    public void reloadConfigurations() throws IOException {
        this.reloadConfig();
    }

    /**
     * Prepares the plugin by initializing necessary components, such as executors and configuration maps.
     */
    private void prepare() {
        this.executor = Executors.newFixedThreadPool(this.getConfig().getInt("threading.poolSize", 64));
        this.scheduler = Executors.newScheduledThreadPool(this.getConfig().getInt("threading.corePoolSize", 8));
        this.worldConfigs = new ConcurrentHashMap<>();
        this.worldConfigFiles = new ConcurrentHashMap<>();

        Bukkit.getScheduler().cancelTasks(this);

    }

    /**
     * Loads plugin providers.
     * TODO: Implement provider loading
     */
    private void loadProviders() {
        // TODO: Implement provider loading
    }

    /**
     * Registers plugin commands with the Bukkit command map.
     */
    private void loadCommands() {
        CommandMap commandMap = Bukkit.getCommandMap();
        List.of(
                new WorldGuardCommand(this)
        ).forEach(command -> commandMap.register("guard", (GuardCommand) command));
    }

    /**
     * Registers plugin events with the Bukkit plugin manager.
     */
    private void loadEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        List.of(
                new DisableMessageBroadcast(this),
                new WorldLoadEventListener(this),
                new BuildingListener(this),
                new InventoryItemChangeListener(this),
                new EntityStatsChangeListener(this),
                new BlockPhysicsListener(this)
        ).forEach(listener -> pluginManager.registerEvents(listener, this));
    }

    /**
     * Starts scheduled tasks, such as periodic configuration saving.
     */
    private void startSchedulers() {
        // save configs each 5 minutes to keep it up to date in the file system
        this.scheduler.scheduleAtFixedRate(this::saveConfigurations, 0, 5, TimeUnit.MINUTES);
    }

    /**
     * Gets the map of world configurations.
     *
     * @return The map of world configurations.
     */
    public Map<String, YamlConfiguration> getWorldConfigs() {
        return worldConfigs;
    }

    /**
     * Gets the map of world configuration files.
     *
     * @return The map of world configuration files.
     */
    public Map<String, File> getWorldConfigFiles() {
        return worldConfigFiles;
    }

    /**
     * Gets the executor service used by the plugin.
     *
     * @return The executor service.
     */
    public ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Gets the scheduled executor service used by the plugin.
     *
     * @return The scheduled executor service.
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
