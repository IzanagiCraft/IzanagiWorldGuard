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

import com.izanagicraft.guard.GuardConstants;
import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import com.izanagicraft.guard.flags.GuardFlag;
import com.izanagicraft.guard.permissions.GuardPermission;
import com.izanagicraft.guard.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:InteractionsListener
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 20.12.2023
 */
public class InteractionsListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public InteractionsListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the PlayerItemConsumeEvent to enforce item_use-related flags within protected regions.
     *
     * @param event The PlayerItemConsumeEvent
     */
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) return;

        if (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.PLAYER_ITEM_USE.getName())) {
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAction = true;

        String allow = worldConfig.getString("flags." + GuardFlag.ITEM_USE.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAction = false;
        }

        if (!allowAction) {
            event.setCancelled(true);
            player.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&aYou're allowed to use this here. &e(TODO translation)"
            ));
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.sendActionBar(Component.empty()), 20 * 2);
        }
    }

    /**
     * Handles the PlayerInteractEvent to enforce interact-related flags within protected regions.
     *
     * @param event The PlayerInteractEvent
     */
    @EventHandler
    public void onItemConsume(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (event.useInteractedBlock().equals(Event.Result.DENY)
                && event.useItemInHand().equals(Event.Result.DENY)) return;

        if (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.PLAYER_INTERACT.getName())) {
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAction = true;

        String allow = worldConfig.getString("flags." + GuardFlag.INTERACT.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAction = false;
        }

        if (!allowAction) {
            event.setCancelled(true);
            player.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&aYou're allowed to use this here. &e(TODO translation)"
            ));
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.sendActionBar(Component.empty()), 20 * 2);
        }
    }

    /**
     * Handles the PlayerInteractEntityEvent to enforce inteeract-related flags within protected regions.
     *
     * @param event The PlayerInteractEntityEvent
     */
    @EventHandler
    public void onItemConsume(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) return;

        if (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.PLAYER_INTERACT.getName())) {
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAction = true;

        String allow = worldConfig.getString("flags." + GuardFlag.INTERACT.getFlagName(), "true");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAction = false;
        }

        if (!allowAction) {
            event.setCancelled(true);
            player.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&aYou're allowed to use this here. &e(TODO translation)"
            ));
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> player.sendActionBar(Component.empty()), 20 * 2);
        }
    }

}
