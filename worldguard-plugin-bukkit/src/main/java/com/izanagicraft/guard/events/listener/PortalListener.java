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
import com.izanagicraft.guard.permissions.GuardPermission;
import com.izanagicraft.guard.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:PortalListener
 * <p>
 * PortalListener is a class that extends GuardListener and handles events related to portals.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 20.12.2023
 */
public class PortalListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public PortalListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the PlayerPortalEvent to enforce portal_enter-related flags within protected regions.
     *
     * @param event The PlayerPortalEvent
     */
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) return;

        if (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.PLAYER_PORTAL_ENTER.getName())) {
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowEnter = true;

        String allow = worldConfig.getString("flags." + GuardFlag.PORTAL_ENTER.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowEnter = false;
        }

        // TODO: region based checks.

        if (!allowEnter) {
            event.setCancelled(true);
            knockBack(player);
            MessageUtils.sendPrefixedMessage(player, "&cYou're not allowed to enter portals here. &e(TODO translation)");
        }
    }

    /**
     * Handles the EntityPortalEvent to enforce portal_enter-related flags within protected regions.
     *
     * @param event The EntityPortalEvent
     */
    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) return;

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowEnter = true;

        String allow = worldConfig.getString("flags." + GuardFlag.PORTAL_ENTER.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowEnter = false;
        }

        // TODO: region based checks.

        if (!allowEnter) {
            event.setCancelled(true);
            knockBack(entity);
        }
    }

    /**
     * Applies knockback to the specified entity in the direction it's coming from.
     *
     * @param entity The entity to apply knockback to.
     */
    private void knockBack(Entity entity) {
        // You can adjust this value based on how much knockback you want.
        double strength = 1.5; // Knockback strength

        // Get the entity's current velocity
        Vector currentVelocity = entity.getVelocity();

        // Reverse the velocity to make it go in the opposite direction
        Vector knockbackVelocity = currentVelocity.clone().multiply(-strength);

        // Apply the knockback
        entity.setVelocity(knockbackVelocity);
    }

}
