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
import com.izanagicraft.guard.utils.MessageUtils;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:InventoryItemChangeListener
 * <p>
 * Handles inventory-related events for IzanagiWorldGuard.
 * This listener checks and enforces item drop and pickup permissions within protected regions.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class InventoryItemChangeListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public InventoryItemChangeListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the PlayerDropItemEvent to enforce item drop permissions within protected regions.
     *
     * @param event The PlayerDropItemEvent.
     */
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        if (player == null || item == null) {
            event.setCancelled(true);
            return;
        }

        Location target = item.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowItem = true;

        String allow = worldConfig.getString("flags." + GuardFlag.ITEM_DROP.getFlagName(), "false");

        // System.out.println("Drop Allow: '" + allow + "'");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowItem = false;
        }

        // TODO: region based checks.

        // System.out.println("Final Drop Allow: '" + allowItem + "'");

        if (!allowItem) {
            event.setCancelled(true);

            // TODO: translations by player locale || fire event cancelled item drop instead
            player.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&cYou're not allowed to drop items here. &e(TODO translation)"
            ));
        }

    }


    /**
     * Handles the PlayerAttemptPickupItemEvent to enforce item pickup permissions within protected regions.
     *
     * @param event The PlayerAttemptPickupItemEvent.
     */
    @EventHandler
    public void onAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (player == null || item == null) {
            event.setCancelled(true);
            return;
        }

        Location target = item.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowItem = true;

        String allow = worldConfig.getString("flags." + GuardFlag.ITEM_PICKUP.getFlagName(), "false");

        // System.out.println("Drop Allow: '" + allow + "'");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowItem = false;
        }

        // TODO: region based checks.

        // System.out.println("Final Drop Allow: '" + allowItem + "'");

        if (!allowItem) {
            event.setCancelled(true);

            // TODO: translations by player locale || fire event cancelled item drop instead
            player.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&cYou're not allowed to pickup items here. &e(TODO translation)"
            ));
        }

    }

    /**
     * Handles the InventoryPickupItemEvent to enforce item pickup permissions within protected regions.
     *
     * @param event The InventoryPickupItemEvent.
     */
    @EventHandler
    public void onItemPickUp(InventoryPickupItemEvent event) {
        Item picked = event.getItem();
        if (picked == null) {
            event.setCancelled(true);
            return;
        }

        Location target = picked.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowItem = true;

        String allow = worldConfig.getString("flags." + GuardFlag.ITEM_PICKUP.getFlagName(), "false");

        // System.out.println("Pick Allow: '" + allow + "'");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowItem = false;
        }

        // TODO: region based checks.

        // System.out.println("Final Pick Allow: '" + allowItem + "'");

        if (!allowItem) {
            event.setCancelled(true);
        }

    }

    /**
     * Handles the PlayerPickItemEvent
     *
     * @param event The PlayerPickItemEvent.
     */
    @EventHandler
    public void onItemPick(PlayerPickItemEvent event) {
        // TODO: add checks if item_pick enabled
    }

}
