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

package com.izanagicraft.guard.permissions;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.permissions:GuardPermission
 * <p>
 * Enum representing various permissions used by the IzanagiWorldGuard plugin.
 * <p>
 * This enum defines group-specific permissions and player-specific ignore permissions
 * for different flags in the plugin. It also includes a default permission for general use.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public enum GuardPermission {

    // group specific permissions
    /**
     * Permission for the admin group.
     */
    GROUPS_ADMIN("admin"),
    /**
     * Permission for the debug group.
     * Receives additional debug information
     */
    GROUPS_DEBUG("debug"),

    // command permissions

    /**
     * Permission to execute the world guard command
     */
    COMMAND_WORLDGUARD("guard.command.worldguard"),

    // Player-specific ignore permissions for flags
    /**
     * Ignore permission for the BUILD flag.
     */
    PLAYER_BUILD("guard.flag.ignored.build"),

    /**
     * Ignore permission for the BREAK flag.
     */
    PLAYER_BREAK("guard.flag.ignored.break"),

    /**
     * Ignore permission for the PLACE flag.
     */
    PLAYER_PLACE("guard.flag.ignored.place"),

    /**
     * Ignore permission for the INTERACT flag.
     */
    PLAYER_INTERACT("guard.flag.ignored.interact"),

    /**
     * Ignore permission for the PVP flag.
     */
    PLAYER_PVP("guard.flag.ignored.pvp"),

    /**
     * Ignore permission for the ENTRY flag.
     */
    PLAYER_ENTRY("guard.flag.ignored.entry"),

    /**
     * Ignore permission for the EXIT flag.
     */
    PLAYER_EXIT("guard.flag.ignored.exit"),

    /**
     * Ignore permission for the TELEPORT flag.
     */
    PLAYER_TELEPORT("guard.flag.ignored.teleport"),

    /**
     * Ignore permission for the DAMAGE flag.
     */
    PLAYER_DAMAGE("guard.flag.ignored.damage"),

    /**
     * Ignore permission for the ITEM_DROP flag.
     */
    PLAYER_ITEM_DROP("guard.flag.ignored.item_drop"),

    /**
     * Ignore permission for the ITEM_PICKUP flag.
     */
    PLAYER_ITEM_PICKUP("guard.flag.ignored.item_pickup"),

    /**
     * Ignore permission for the COMMAND_EXECUTE flag.
     */
    PLAYER_COMMAND_EXECUTE("guard.flag.ignored.command_execute"),

    /**
     * Ignore permission for the ITEM_USE flag.
     */
    PLAYER_ITEM_USE("guard.flag.ignored.item_use"),

    // default permission
    /**
     * Default permission for general use.
     */
    DEFAULT("default");

    /**
     * Gets the GuardPermission enum constant by its name.
     *
     * @param name The name of the GuardPermission.
     * @return The GuardPermission enum constant with the specified name, or null if not found.
     */
    public static GuardPermission getByName(String name) {
        for (GuardPermission guardPermission : values()) {
            if (guardPermission.getName().equalsIgnoreCase(name)) {
                return guardPermission;
            }
        }
        return null; // Not found
    }

    private final String name;

    /**
     * Constructs a GuardPermission enum constant with the specified permission name.
     *
     * @param name The name of the permission.
     */
    GuardPermission(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the permission.
     *
     * @return The name of the permission.
     */
    public String getName() {
        return name;
    }

}
