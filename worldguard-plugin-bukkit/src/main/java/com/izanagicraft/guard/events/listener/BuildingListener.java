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
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:BuildingListener
 * <p>
 * This class represents a listener for building-related events in IzanagiWorldGuard.
 * It extends the base GuardListener class.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class BuildingListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public BuildingListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the BlockPlaceEvent, checking whether the player is allowed to place a block
     * based on the world configuration and region-based checks.
     *
     * @param event The BlockPlaceEvent triggered by a player placing a block.
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player == null || block == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowBuild = false;

        String build = worldConfig.getString("flags." + GuardFlag.BUILD.getFlagName(), "false");
        if (build.isEmpty() || build.equals("empty") || build.equals("false") || build.equals("deny")) {
            allowBuild = false;
        }

        String placeBlock = worldConfig.getString("flags." + GuardFlag.PLACE.getFlagName(), "false");
        if (placeBlock.isEmpty() || placeBlock.equals("empty") || placeBlock.equals("false") || placeBlock.equals("deny")) {
            allowBuild = false;
        } else if (placeBlock.equals("true") || placeBlock.equals("allow")) {
            allowBuild = true;
        }

        // TODO: region based checks.

        if (!allowBuild) {
            event.setCancelled(true);

            // Spawn deny particle at the blocked location
            spawnDenyParticle(player, target);
        }
    }

    /**
     * Handles the BlockBreakEvent, checking whether the player is allowed to break a block
     * based on the world configuration and region-based checks.
     *
     * @param event The BlockBreakEvent triggered by a player breaking a block.
     */
    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player == null || block == null) {
            event.setCancelled(true);
            return;
        }

        Location target = block.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowBuild = false;

        String build = worldConfig.getString("flags." + GuardFlag.BUILD.getFlagName(), "false");
        if (build.isEmpty() || build.equals("empty") || build.equals("false") || build.equals("deny")) {
            allowBuild = false;
        }

        String breakBlock = worldConfig.getString("flags." + GuardFlag.BREAK.getFlagName(), "false");
        if (breakBlock.isEmpty() || breakBlock.equals("empty") || breakBlock.equals("false") || breakBlock.equals("deny")) {
            allowBuild = false;
        } else if (breakBlock.equals("true") || breakBlock.equals("allow")) {
            allowBuild = true;
        }

        // TODO: region based checks.

        if (!allowBuild) {
            event.setCancelled(true);

            // Spawn deny particle at the blocked location
            spawnDenyParticle(player, target.add(new Vector(0, 1, 0)));
        }
    }

    private void spawnDenyParticle(Player player, Location location) {
        player.spawnParticle(Particle.VILLAGER_ANGRY, location, 1, 0.5, 0, 0.5, 0);
    }

}
