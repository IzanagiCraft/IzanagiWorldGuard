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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:PlayerFlyListener
 * <p>
 * PlayerFlyListener is a listener class that handles events related to player flying within protected regions.
 * It extends GuardListener and is associated with the IzanagiWorldGuardPlugin.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 19.12.2023
 */
public class PlayerFlyListener extends GuardListener {

    private Map<UUID, Boolean> spawnedFallTask = new ConcurrentHashMap<>();

    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public PlayerFlyListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);

        // don't actually set's a listener. Just enables if player is allowed to fly checks

        getPlugin().getScheduler().scheduleAtFixedRate(() -> {
            getPlugin().getServer().getOnlinePlayers().forEach(online -> {

                Location target = online.getLocation();

                YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
                if (worldConfig == null) return;

                boolean allowFly;

                String allowFlight = worldConfig.getString("flags." + GuardFlag.FLY.getFlagName(), "false");
                if (allowFlight.isEmpty() || allowFlight.equals("empty") || allowFlight.equals("false") || allowFlight.equals("deny")) {
                    allowFly = false;
                } else if (allowFlight.equals("true") || allowFlight.equals("allow")) {
                    allowFly = true;
                } else allowFly = false;

                // TODO: region based checks.

                boolean flyMode = (online.getGameMode().equals(GameMode.SPECTATOR) || online.getGameMode().equals(GameMode.CREATIVE));

                if (!flyMode && online.getAllowFlight() != allowFly && !allowFly) {
                    Bukkit.getScheduler().runTask(getPlugin(), () -> {
                        if (spawnedFallTask.getOrDefault(online.getUniqueId(), false)) return;
                        spawnedFallTask.put(online.getUniqueId(), true);
                        MessageUtils.sendPrefixedMessage(online, "&cYou're not allowed to fly anymore. In (10 seconds) you'll fall to the ground. &e(TODO translation)");
                        online.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                                GuardConstants.CHAT_PREFIX + "&cYou're not allowed to fly anymore. In (10 seconds) you'll fall to the ground. &e(TODO translation)"
                        ));
                        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                            if (spawnedFallTask.getOrDefault(online.getUniqueId(), false)) return;
                            online.setAllowFlight(false);
                            online.setFlying(false);
                            spawnedFallTask.remove(online.getUniqueId());
                        }, 20 * 10);
                    });
                }

                if (!flyMode && online.getAllowFlight() != allowFly && allowFly) {
                    Bukkit.getScheduler().runTask(getPlugin(), () -> {
                        online.setAllowFlight(true);
                        spawnedFallTask.remove(online.getUniqueId());
                        online.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                                GuardConstants.CHAT_PREFIX + "&aYou're allowed to fly now. &e(TODO translation)"
                        ));
                    });
                }

            });
        }, 1, 2, TimeUnit.SECONDS);
    }

}
