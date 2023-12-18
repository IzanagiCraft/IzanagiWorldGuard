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

package com.izanagicraft.guard.events.listener;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import java.io.File;
import java.io.IOException;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:WorldLoadEventListener
 * <p>
 * Event listener for world-related events in the IzanagiWorldGuard plugin.
 * <p>
 * This class extends the {@link GuardListener} class and handles events related to world loading and unloading.
 * It manages the loading of world configurations, including the creation of global configurations for new worlds,
 * and the saving of world configurations upon world unloading.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class WorldLoadEventListener extends GuardListener {

    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public WorldLoadEventListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the WorldLoadEvent by asynchronously loading the configuration for the world.
     *
     * @param event The WorldLoadEvent.
     */
    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        getPlugin().getExecutor().submit(() -> {
            World world = event.getWorld();

            if (getPlugin().getWorldConfigs().containsKey(world.getName())) return;

            // TODO: implement region specific config loading
            File worldConfigFile = new File(getPlugin().getDataFolder(), "worlds/" + world.getName() + "/__global__.yml");
            YamlConfiguration worldConfig;
            if (!worldConfigFile.exists()) {
                worldConfigFile.getParentFile().mkdirs();
                worldConfig = new YamlConfiguration();
                populateWorldConfig(world, worldConfig);
                try {
                    worldConfig.save(worldConfigFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                worldConfig = YamlConfiguration.loadConfiguration(worldConfigFile);
            }
            getPlugin().getWorldConfigs().put(world.getName(), worldConfig);
        });
    }

    /**
     * Handles the WorldUnloadEvent by asynchronously saving the configuration for the world.
     *
     * @param event The WorldUnloadEvent.
     */
    @EventHandler
    public void onWorldUnLoad(WorldUnloadEvent event) {
        getPlugin().getExecutor().submit(() -> {
            World world = event.getWorld();
            if (!getPlugin().getWorldConfigs().containsKey(world.getName())
                    || !getPlugin().getWorldConfigs().containsKey(world.getName())) return;

            // TODO: implement region specific config loading
            File worldConfigFile = getPlugin().getWorldConfigFiles().get(world.getName());
            YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(world.getName());
            try {
                worldConfig.save(worldConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Populates the world configuration with default flags from the plugin's main configuration.
     *
     * @param world  The world for which the configuration is being populated.
     * @param config The YamlConfiguration to be populated.
     */
    private void populateWorldConfig(World world, YamlConfiguration config) {
        ConfigurationSection defaultFlagSection = getPlugin().getConfig().getConfigurationSection("defaultFlags");
        defaultFlagSection.getKeys(false).forEach(defaultFlag -> {
            config.set("flags." + defaultFlag, defaultFlagSection.getString("defaultFlags." + defaultFlag, "empty"));
        });
    }

}
