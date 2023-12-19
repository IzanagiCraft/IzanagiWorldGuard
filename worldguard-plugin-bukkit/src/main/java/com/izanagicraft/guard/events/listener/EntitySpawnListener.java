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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:EntitySpawnListener
 * <p>
 * EntitySpawnListener is a listener class that handles events related to entity spawning within protected regions.
 * It extends GuardListener and is associated with the IzanagiWorldGuardPlugin.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 19.12.2023
 */
public class EntitySpawnListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public EntitySpawnListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the EntitySpawnEvent to enforce entity_spawn-related flags within protected regions.
     *
     * @param event The EntitySpawnEvent.
     */
    @EventHandler
    public void onDamage(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        // return if player spawn
        if ((entity instanceof Player)) return;

        if (event.isCancelled()) return;

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowSpawn = true;

        String allow = worldConfig.getString("flags." + GuardFlag.ENTITY_DAMAGE.getFlagName(), "false");
        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowSpawn = false;
        }

        if (entity instanceof Animals) {
            String allowAnimal = worldConfig.getString("flags." + GuardFlag.ANIMAL_DAMAGE.getFlagName(), "false");
            if (allowAnimal.isEmpty() || allowAnimal.equals("empty") || allowAnimal.equals("false") || allowAnimal.equals("deny")) {
                allowSpawn = false;
            } else if (allowAnimal.equals("true") || allowAnimal.equals("allow")) {
                allowSpawn = true;
            } else allowSpawn = false;
        }

        if (entity instanceof Monster) {
            String allowMonster = worldConfig.getString("flags." + GuardFlag.MONSTER_DAMAGE.getFlagName(), "false");
            if (allowMonster.isEmpty() || allowMonster.equals("empty") || allowMonster.equals("false") || allowMonster.equals("deny")) {
                allowSpawn = false;
            } else if (allowMonster.equals("true") || allowMonster.equals("allow")) {
                allowSpawn = true;
            } else allowSpawn = false;
        }

        // TODO: region based checks.

        if (!allowSpawn) {
            event.setCancelled(true);
        }
    }
}
