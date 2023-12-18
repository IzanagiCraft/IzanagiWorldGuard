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

import org.bukkit.Location;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.region:RegionManager
 * <p>
 * Manages regions and provides methods for region-related operations.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public interface RegionManager {

    /**
     * Adds a region to the manager.
     *
     * @param region The region to add.
     */
    void addRegion(GuardedRegion region);

    /**
     * Adds a region to the manager asynchronously.
     *
     * @param region The region to add.
     * @return A CompletableFuture representing the completion of the operation.
     */
    default CompletableFuture<Void> addRegionAsync(GuardedRegion region) {
        return CompletableFuture.runAsync(() -> addRegion(region));
    }


    /**
     * Gets a collection of all registered regions.
     *
     * @return A collection of registered regions.
     */
    Collection<GuardedRegion> getRegions();


    /**
     * Checks whether a given location is within the cuboid defined by the minimum and maximum points.
     *
     * @param location The location to check for containment.
     * @param minPoint The minimum point of the cuboid.
     * @param maxPoint The maximum point of the cuboid.
     * @return {@code true} if the location is within the cuboid, {@code false} otherwise.
     */
    boolean isLocationInCuboid(Location location, Location minPoint, Location maxPoint);

    /**
     * Checks if two regions intersect.
     *
     * @param region1Min The minimum point of the first region.
     * @param region1Max The maximum point of the first region.
     * @param region2Min The minimum point of the second region.
     * @param region2Max The maximum point of the second region.
     * @return {@code true} if the regions intersect, {@code false} otherwise.
     */
    boolean doRegionsIntersect(Location region1Min, Location region1Max, Location region2Min, Location region2Max);

    /**
     * Checks whether a given location is within the specified region.
     *
     * @param location The location to check for containment.
     * @param region   The region to check against.
     * @return {@code true} if the location is within the region, {@code false} otherwise.
     */
    default boolean isLocationInRegion(Location location, GuardedRegion region) {
        return isLocationInCuboid(location, region.getMinPoint(), region.getMaxPoint());
    }

    /**
     * Checks if two regions intersect.
     *
     * @param region1 The first region to check for intersection.
     * @param region2 The second region to check for intersection.
     * @return {@code true} if the regions intersect, {@code false} otherwise.
     */
    default boolean doRegionsIntersect(GuardedRegion region1, GuardedRegion region2) {
        return doRegionsIntersect(region1.getMinPoint(), region1.getMaxPoint(), region2.getMinPoint(), region2.getMaxPoint());
    }

    /**
     * Asynchronously checks whether a given location is within the cuboid defined by the minimum and maximum points.
     *
     * @param location The location to check for containment.
     * @param minPoint The minimum point of the cuboid.
     * @param maxPoint The maximum point of the cuboid.
     * @return A CompletableFuture&lt;Boolean&gt; representing {@code true} if the location is within the cuboid, {@code false} otherwise.
     */
    default CompletableFuture<Boolean> isLocationInCuboidAsync(Location location, Location minPoint, Location maxPoint) {
        return CompletableFuture.supplyAsync(() -> isLocationInCuboid(location, minPoint, maxPoint));
    }

    /**
     * Asynchronously checks if two regions intersect.
     *
     * @param region1Min The minimum point of the first region.
     * @param region1Max The maximum point of the first region.
     * @param region2Min The minimum point of the second region.
     * @param region2Max The maximum point of the second region.
     * @return A CompletableFuture&lt;Boolean&gt; representing {@code true} if the regions intersect, {@code false} otherwise.
     */
    default public CompletableFuture<Boolean> doRegionsIntersectAsync(Location region1Min, Location region1Max,
                                                                      Location region2Min, Location region2Max) {
        return CompletableFuture.supplyAsync(() -> doRegionsIntersect(region1Min, region1Max, region2Min, region2Max));
    }

    /**
     * Asynchronously checks whether a given location is within the specified region.
     *
     * @param location The location to check for containment.
     * @param region   The region to check against.
     * @return A CompletableFuture&lt;Boolean&gt; representing {@code true} if the location is within the region, {@code false} otherwise.
     */
    default CompletableFuture<Boolean> isLocationInRegionAsync(Location location, GuardedRegion region) {
        return isLocationInCuboidAsync(location, region.getMinPoint(), region.getMaxPoint());
    }

    /**
     * Asynchronously checks if two regions intersect.
     *
     * @param region1 The first region to check for intersection.
     * @param region2 The second region to check for intersection.
     * @return A CompletableFuture&lt;Boolean&gt; representing {@code true} if the regions intersect, {@code false} otherwise.
     */
    default CompletableFuture<Boolean> doRegionsIntersectAsync(GuardedRegion region1, GuardedRegion region2) {
        return doRegionsIntersectAsync(region1.getMinPoint(), region1.getMaxPoint(), region2.getMinPoint(), region2.getMaxPoint());
    }

}
