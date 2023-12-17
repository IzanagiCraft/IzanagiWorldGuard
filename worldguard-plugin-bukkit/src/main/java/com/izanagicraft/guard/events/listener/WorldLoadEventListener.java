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

    private void populateWorldConfig(World world, YamlConfiguration config) {
        ConfigurationSection defaultFlagSection = getPlugin().getConfig().getConfigurationSection("defaultFlags");
        defaultFlagSection.getKeys(false).forEach(defaultFlag -> {
            config.set("flags." + defaultFlag, defaultFlagSection.getString("defaultFlags." + defaultFlag, "empty"));
        });
    }

}
