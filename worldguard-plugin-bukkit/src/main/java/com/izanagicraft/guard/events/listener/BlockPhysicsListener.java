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
import com.izanagicraft.guard.flags.GuardFlag;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:BlockPhysicsListener
 * <p>
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

    /**
     * Handles the BlockFromToEvent to enforce block_physics-related flags within protected regions.
     *
     * @param event The BlockFromToEvent.
     */
    @EventHandler
    public void onBlockPhysicsUpdate(BlockFromToEvent event) {
        Block block = event.getBlock();
        Block block2 = event.getToBlock();

        if (block == null || block2 == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();
        Location target2 = block2.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowPhysics = true;

        String allow = worldConfig.getString("flags." + GuardFlag.BLOCK_PHYSICS.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowPhysics = false;
        }

        // TODO: region based checks both blocks.

        if (!allowPhysics) event.setCancelled(true);

    }

    /**
     * Handles the BlockIgniteEvent to enforce fire_spread-related flags within protected regions.
     *
     * @param event The BlockIgniteEvent.
     */
    @EventHandler
    public void onBlockPhysicsUpdate(BlockIgniteEvent event) {
        Block block = event.getBlock();

        if (block == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowPhysics = true;

        String allow = worldConfig.getString("flags." + GuardFlag.FIRE_SPREAD.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowPhysics = false;
        }

        // TODO: region based checks both blocks.

        if (!allowPhysics) event.setCancelled(true);

    }

    /**
     * Handles the EntityExplodeEvent to enforce explosion-related flags within protected regions.
     *
     * @param event The EntityExplodeEvent.
     */
    @EventHandler
    public void onBlockPhysicsUpdate(EntityExplodeEvent event) {
        Entity entity = event.getEntity();

        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowPhysics = true;

        String allow = worldConfig.getString("flags." + GuardFlag.EXPLOSIONS.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowPhysics = false;
        }

        // TODO: region based checks both blocks.

        if (!allowPhysics) event.setCancelled(true);
    }

    /**
     * Handles the BlockExplodeEvent to enforce explosion-related flags within protected regions.
     *
     * @param event The BlockExplodeEvent.
     */
    @EventHandler
    public void onBlockPhysicsUpdate(BlockExplodeEvent event) {
        Block block = event.getBlock();

        if (block == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowPhysics = true;

        String allow = worldConfig.getString("flags." + GuardFlag.EXPLOSIONS.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowPhysics = false;
        }

        // TODO: region based checks both blocks.

        if (!allowPhysics) event.setCancelled(true);
    }

}
