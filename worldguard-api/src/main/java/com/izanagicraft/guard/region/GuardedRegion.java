package com.izanagicraft.guard.region;

import com.izanagicraft.guard.flags.GuardFlag;
import com.izanagicraft.guard.utils.SelectionUtils;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

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
     * Gets the flag container containing all enabled in this region.
     *
     * @return The FlagContainer containing enabled flags
     */
    GuardFlag.FlagContainer getFlagContainer();

    /**
     * Gets the flags enabled in this region.
     *
     * @return The enabled flags
     */
    Collection<GuardFlag> readFlags();

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
