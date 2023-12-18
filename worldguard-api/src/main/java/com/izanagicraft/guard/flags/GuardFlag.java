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

import java.util.ArrayList;
import java.util.List;

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
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public enum GuardFlag {
    /**
     * Allows or denies building.
     */
    BUILD("build", List.of()),

    /**
     * Allows or denies block breaking.
     */
    BREAK("break", List.of()),

    /**
     * Allows or denies block placement.
     */
    PLACE("place", List.of()),

    /**
     * Allows or denies interaction with blocks/entities.
     */
    INTERACT("interact", List.of()),

    /**
     * Allows or denies player versus player combat.
     */
    PVP("pvp", List.of()),

    /**
     * Allows or denies mob spawning.
     */
    MOB_SPAWNING("mob_spawning", List.of()),

    /**
     * Allows or denies explosions.
     */
    EXPLOSIONS("explosions", List.of()),

    /**
     * Allows or denies fire spread.
     */
    FIRE_SPREAD("fire_spread", List.of()),

    /**
     * Allows or denies entry into the region.
     */
    ENTRY("entry", List.of()),

    /**
     * Allows or denies exit from the region.
     */
    EXIT("exit", List.of()),

    /**
     * Allows or denies teleportation.
     */
    TELEPORT("teleport", List.of()),

    /**
     * Allows or denies damage to entities.
     */
    DAMAGE("damage", List.of()),

    /**
     * Allows or denies healing entities.
     */
    HEAL("heal", List.of()),

    /**
     * Allows or denies dropping items.
     */
    ITEM_DROP("item_drop", List.of()),

    /**
     * Allows or denies picking up items.
     */
    ITEM_PICKUP("item_pickup", List.of()),

    /**
     * Allows or denies block physics (e.g., sand falling).
     */
    BLOCK_PHYSICS("block_physics", List.of()),

    /**
     * Allows or denies hunger loss.
     */
    HUNGER("hunger", List.of()),

    /**
     * Allows or denies weather changes.
     */
    WEATHER_CHANGE("weather_change", List.of()),

    /**
     * Allows or denies command execution.
     */
    COMMAND_EXECUTE("command_execute", List.of()),

    /**
     * Allows or denies chatting.
     */
    CHATTING("chatting", List.of()),

    /**
     * Allows or denies item usage (e.g., right-click actions).
     */
    ITEM_USE("item_use", List.of()),

    /**
     * Allows or denies entity damage.
     */
    ENTITY_DAMAGE("entity_damage", List.of()),

    /**
     * Allows or denies entity spawning.
     */
    ENTITY_SPAWN("entity_spawn", List.of()),

    /**
     * Allows or denies monster damage.
     */
    MONSTER_DAMAGE("monster_damage", List.of()),

    /**
     * Allows or denies monster spawning.
     */
    MONSTER_SPAWN("monster_spawn", List.of()),

    /**
     * Allows or denies animal damage.
     */
    ANIMAL_DAMAGE("animal_damage", List.of()),

    /**
     * Allows or denies animal spawning.
     */
    ANIMAL_SPAWN("animal_spawn", List.of()),

    /**
     * Allows or denies flying.
     */
    FLY("fly", List.of()),

    /**
     * Allows or denies entering portals.
     */
    PORTAL_ENTER("portal_enter", List.of());

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

    /**
     * Gets the list of globally valid values for the flag.
     *
     * @return The list of globally valid values for the flag.
     */
    public static List<String> getDefaultValidValues() {
        return List.of("empty", "true", "false", "allow", "deny");
    }

    private final String flagName;
    private final List<String> validValues;

    /**
     * Constructs a GuardFlag enum constant with the specified flag name.
     *
     * @param flagName    The name of the flag.
     * @param validValues
     */
    GuardFlag(String flagName, List<String> validValues) {
        this.flagName = flagName;
        List<String> valid = new ArrayList();
        valid.addAll(validValues);
        valid.addAll(getDefaultValidValues());
        this.validValues = valid;
    }

    /**
     * Gets the name of the flag.
     *
     * @return The name of the flag.
     */
    public String getFlagName() {
        return flagName;
    }

    /**
     * Gets the list of valid values for the flag.
     *
     * @return The list of valid values for the flag.
     */
    public List<String> getValidValues() {
        return validValues;
    }

}
