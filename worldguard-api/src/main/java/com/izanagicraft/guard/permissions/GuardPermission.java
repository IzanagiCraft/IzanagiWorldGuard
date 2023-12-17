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
    GROUPS_ADMIN("admin"),
    // ========================================= //

    // Player-specific ignore permissions for flags
    PLAYER_BUILD("guard.flag.ignored.build"),
    PLAYER_BREAK("guard.flag.ignored.break"),
    PLAYER_PLACE("guard.flag.ignored.place"),
    PLAYER_INTERACT("guard.flag.ignored.interact"),
    PLAYER_PVP("guard.flag.ignored.pvp"),
    PLAYER_ENTRY("guard.flag.ignored.entry"),
    PLAYER_EXIT("guard.flag.ignored.exit"),
    PLAYER_TELEPORT("guard.flag.ignored.teleport"),
    PLAYER_DAMAGE("guard.flag.ignored.damage"),
    PLAYER_ITEM_DROP("guard.flag.ignored.item_drop"),
    PLAYER_ITEM_PICKUP("guard.flag.ignored.item_pickup"),
    PLAYER_COMMAND_EXECUTE("guard.flag.ignored.command_execute"),
    PLAYER_ITEM_USE("guard.flag.ignored.item_use"),

    // ========================================= //
    // default permission
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
