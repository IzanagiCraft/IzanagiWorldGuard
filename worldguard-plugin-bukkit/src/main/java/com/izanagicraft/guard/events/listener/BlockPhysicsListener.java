package com.izanagicraft.guard.events.listener;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import com.izanagicraft.guard.flags.GuardFlag;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:BlockPhysicsListener
 *
 * Handles the BlockPhysicsEvent to enforce block_physics-related flags within protected regions.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class BlockPhysicsListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public BlockPhysicsListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the BlockPhysicsEvent to enforce block_physics-related flags within protected regions.
     *
     * @param event The BlockPhysicsEvent.
     */
    @EventHandler
    public void onBlockPhysicsUpdate(BlockPhysicsEvent event) {
        Block block = event.getBlock();

        if (block == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowPhysics = true;

        String allow = worldConfig.getString("flags." + GuardFlag.BLOCK_PHYSICS.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowPhysics = false;
        }

        // TODO: region based checks.

        if (!allowPhysics) event.setCancelled(true);

    }
}
