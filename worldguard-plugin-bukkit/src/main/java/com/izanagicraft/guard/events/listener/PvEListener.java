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
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:PvEListener
 * <p>
 * PvEListener is a listener class that handles events related to PvE interactions within protected regions.
 * It extends GuardListener and is associated with the IzanagiWorldGuardPlugin.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 19.12.2023
 */
public class PvEListener extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public PvEListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the EntityDamageByEntityEvent to enforce pve-related flags within protected regions.
     *
     * @param event The EntityDamageByEntityEvent.
     */
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity == null) {
            event.setCancelled(true);
            return;
        }

        // return if not pve
        if ((entity instanceof Player) || !(event.getDamager() instanceof Player)) return;

        if (event.isCancelled()) return;

        if (event.getDamager().hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || event.getDamager().hasPermission(GuardPermission.PLAYER_DAMAGE.getName())) {
            return;
        }

        Location target = entity.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAttack = true;
        String attacked = "Entities";

        String allow = worldConfig.getString("flags." + GuardFlag.ENTITY_DAMAGE.getFlagName(), "false");
        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAttack = false;
        }

        if (entity instanceof Animals) {
            attacked = "Animals";
            String allowAnimal = worldConfig.getString("flags." + GuardFlag.ANIMAL_DAMAGE.getFlagName(), "false");
            if (allowAnimal.isEmpty() || allowAnimal.equals("empty") || allowAnimal.equals("false") || allowAnimal.equals("deny")) {
                allowAttack = false;
            } else if (allowAnimal.equals("true") || allowAnimal.equals("allow")) {
                allowAttack = true;
            } else allowAttack = false;
        }

        if (entity instanceof Monster) {
            attacked = "Monster";
            String allowMonster = worldConfig.getString("flags." + GuardFlag.MONSTER_DAMAGE.getFlagName(), "false");
            if (allowMonster.isEmpty() || allowMonster.equals("empty") || allowMonster.equals("false") || allowMonster.equals("deny")) {
                allowAttack = false;
            } else if (allowMonster.equals("true") || allowMonster.equals("allow")) {
                allowAttack = true;
            } else allowAttack = false;
        }

        // TODO: region based checks.

        if (!allowAttack) {
            event.setCancelled(true);

            Player attackerPlayer = (Player) event.getDamager();
            // TODO: translations by player locale || fire event cancelled item drop instead
            attackerPlayer.sendActionBar(MessageUtils.getComponentSerializer().deserialize(
                    GuardConstants.CHAT_PREFIX + "&cYou're not allowed to attack" + attacked + " here. &e(TODO translation)"
            ));
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> attackerPlayer.sendActionBar(Component.empty()), 20*2);
        }
    }
}
