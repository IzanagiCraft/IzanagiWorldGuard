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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:TeleportationListener
 * <p>
 * TeleportationListener is a class that extends GuardListener and handles events related to teleports.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 20.12.2023
 */
public class TeleportationListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public TeleportationListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the PlayerTeleportEvent to enforce teleport-related flags within protected regions.
     *
     * @param event The PlayerTeleportEvent
     */
    @EventHandler
    public void onPlayerPortal(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        if (event.isCancelled()) return;

        if (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.PLAYER_TELEPORT.getName())) {
            return;
        }

        Location target1 = player.getLocation();
        YamlConfiguration worldConfig1 = getPlugin().getWorldConfigs().get(target1.getWorld().getName());
        if (worldConfig1 == null) return;
        boolean allowTp1 = true;
        String allow1 = worldConfig1.getString("flags." + GuardFlag.TELEPORT.getFlagName(), "true");
        if (allow1.isEmpty() || allow1.equalsIgnoreCase("empty")
                || allow1.equalsIgnoreCase("false") || allow1.equalsIgnoreCase("deny")) {
            allowTp1 = false;
        }
        // TODO: region based checks.
        if (!allowTp1) {
            event.setCancelled(true);
            MessageUtils.sendPrefixedMessage(player, "&cYou're not allowed to teleport here. &e(TODO translation)");
            return;
        }

        Location target2 = player.getLocation();
        YamlConfiguration worldConfig2 = getPlugin().getWorldConfigs().get(target2.getWorld().getName());
        if (worldConfig2 == null) return;
        boolean allowTp2 = true;
        String allow2 = worldConfig1.getString("flags." + GuardFlag.TELEPORT.getFlagName(), "true");
        if (allow2.isEmpty() || allow2.equalsIgnoreCase("empty")
                || allow2.equalsIgnoreCase("false") || allow2.equalsIgnoreCase("deny")) {
            allowTp2 = false;
        }
        // TODO: region based checks.
        if (!allowTp2) {
            event.setCancelled(true);
            MessageUtils.sendPrefixedMessage(player, "&cYou're not allowed to teleport at the target location. &e(TODO translation)");
        }

    }

}
