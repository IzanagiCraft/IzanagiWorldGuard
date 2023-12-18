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

package com.izanagicraft.guard.region;

import com.izanagicraft.guard.utils.SelectionUtils;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.region:GuardedRegion
 * <p>
 * Represents a protected region.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public interface GuardedRegion {

    /**
     * Gets the unique identifier of the region.
     *
     * @return The region identifier.
     */
    String getId();

    /**
     * Gets the minimum point of the region.
     *
     * @return The minimum point.
     */
    Location getMinPoint();

    /**
     * Gets the maximum point of the region.
     *
     * @return The maximum point.
     */
    Location getMaxPoint();

    /**
     * Creates a new GuardedRegion instance with the specified parameters.
     *
     * @param id       The unique identifier of the region.
     * @param minPoint The minimum point of the region.
     * @param maxPoint The maximum point of the region.
     * @return A new GuardedRegion instance.
     */
    static GuardedRegion of(String id, Location minPoint, Location maxPoint) {
        return new BasicGuardedRegion(id, minPoint, maxPoint);
    }

    /**
     * Creates a new GuardedRegion instance based on the selected region by a player.
     *
     * @param id     The unique identifier of the region.
     * @param player The player whose selected region will be used.
     * @return A new GuardedRegion instance based on the player's selected region, or null if the worlds do not match.
     */
    static GuardedRegion of(String id, Player player) {
        Region region = SelectionUtils.getSelectedRegion(player);

        // Check if the selected region and player's world match
        if (region == null || !player.getWorld().getName().equals(region.getWorld().getName())) {
            return null;
        }

        // Create a GuardedRegion instance using the selected region's boundaries
        return of(id,
                new Location(player.getWorld(), region.getMinimumPoint().getBlockX(), region.getMinimumPoint().getBlockY(), region.getMinimumPoint().getBlockZ()),
                new Location(player.getWorld(), region.getMaximumPoint().getBlockX(), region.getMaximumPoint().getBlockY(), region.getMaximumPoint().getBlockZ()));
    }

}
