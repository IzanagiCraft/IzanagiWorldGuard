package com.izanagicraft.guard.events.listener;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import com.izanagicraft.guard.flags.GuardFlag;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:EntityStatsChangeListener
 * <p>
 * Handles events related to entity stats changes for IzanagiWorldGuard.
 * This listener checks and enforces flags related to hunger, healing, and damage within protected regions.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class EntityStatsChangeListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public EntityStatsChangeListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the FoodLevelChangeEvent to enforce hunger-related flags within protected regions.
     *
     * @param event The FoodLevelChangeEvent.
     */
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowChange = true;

        String allow = worldConfig.getString("flags." + GuardFlag.HUNGER.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowChange = false;
        }

        // TODO: region based checks.

        if (!allowChange) event.setCancelled(true);

    }

    /**
     * Handles the EntityRegainHealthEvent to enforce healing-related flags within protected regions.
     *
     * @param event The EntityRegainHealthEvent.
     */
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();

        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowChange = true;

        String allow = worldConfig.getString("flags." + GuardFlag.HEAL.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowChange = false;
        }

        // TODO: region based checks.

        if (!allowChange) event.setCancelled(true);

    }

    /**
     * Handles the EntityDamageEvent to enforce damage-related flags within protected regions.
     *
     * @param event The EntityDamageEvent.
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowChange = true;

        String allow = worldConfig.getString("flags." + GuardFlag.DAMAGE.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowChange = false;
        }

        // TODO: region based checks.

        if (!allowChange) event.setCancelled(true);

    }


}
