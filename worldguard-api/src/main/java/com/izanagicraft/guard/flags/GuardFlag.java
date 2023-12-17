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

package com.izanagicraft.guard.flags;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.flags:GuardFlag
 * <p>
 * Enum representing various flags used by the IzanagiWorldGuard plugin.
 * <p>
 * This enum defines different flags that can be applied to protected regions, allowing
 * or denying certain actions or behaviors within those regions. Each flag corresponds to
 * a specific aspect of gameplay, such as building, breaking blocks, interacting with entities,
 * and more.
 *
 * @author <a href="https:  //github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public enum GuardFlag {
    BUILD("build"),                    // Allows or denies building
    BREAK("break"),                    // Allows or denies block breaking
    PLACE("place"),                    // Allows or denies block placement
    INTERACT("interact"),              // Allows or denies interaction with blocks/entities
    PVP("pvp"),                        // Allows or denies player versus player combat
    MOB_SPAWNING("mob_spawning"),      // Allows or denies mob spawning
    EXPLOSIONS("explosions"),          // Allows or denies explosions
    FIRE_SPREAD("fire_spread"),        // Allows or denies fire spread
    ENTRY("entry"),                    // Allows or denies entry into the region
    EXIT("exit"),                      // Allows or denies exit from the region
    TELEPORT("teleport"),              // Allows or denies teleportation
    DAMAGE("damage"),                  // Allows or denies damage to entities
    HEAL("heal"),                      // Allows or denies healing entities
    ITEM_DROP("item_drop"),            // Allows or denies dropping items
    ITEM_PICKUP("item_pickup"),        // Allows or denies picking up items
    BLOCK_PHYSICS("block_physics"),    // Allows or denies block physics (e.g., sand falling)
    HUNGER("hunger"),                  // Allows or denies hunger loss
    WEATHER_CHANGE("weather_change"),  // Allows or denies weather changes
    COMMAND_EXECUTE("command_execute"),// Allows or denies command execution
    ITEM_USE("item_use"),              // Allows or denies item usage (e.g., right-click actions)
    ENTITY_DAMAGE("entity_damage"),    // Allows or denies entity damage
    ENTITY_SPAWN("entity_spawn"),      // Allows or denies entity spawning
    MONSTER_DAMAGE("monster_damage"),  // Allows or denies monster damage
    MONSTER_SPAWN("monster_spawn"),    // Allows or denies monster spawning
    ANIMAL_DAMAGE("animal_damage"),    // Allows or denies animal damage
    ANIMAL_SPAWN("animal_spawn"),      // Allows or denies animal spawning
    FLY("fly"),                        // Allows or denies flying
    PORTAL_ENTER("portal_enter")       // Allows or denies entering portals

    //;
    ;

    /**
     * Gets the GuardFlag enum constant by its name.
     *
     * @param name The name of the GuardFlag.
     * @return The GuardFlag enum constant with the specified name, or null if not found.
     */
    public static GuardFlag getByName(String name) {
        for (GuardFlag guardFlag : values()) {
            if (guardFlag.getFlagName().equalsIgnoreCase(name)) {
                return guardFlag;
            }
        }
        return null; // Not found
    }

    private final String flagName;

    /**
     * Constructs a GuardFlag enum constant with the specified flag name.
     *
     * @param flagName The name of the flag.
     */
    GuardFlag(String flagName) {
        this.flagName = flagName;
    }

    /**
     * Gets the name of the flag.
     *
     * @return The name of the flag.
     */
    public String getFlagName() {
        return flagName;
    }

}
