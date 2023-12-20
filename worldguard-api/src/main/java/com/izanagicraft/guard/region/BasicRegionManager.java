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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.region:BasicRegionManager
 * <p>
 * Basic implementation of the {@link RegionManager} interface.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class BasicRegionManager implements RegionManager {

    /**
     * A map to store GuardedRegion instances, where the key is the region ID.
     */
    private Map<String, GuardedRegion> regions;

    /**
     * Constructs a new BasicRegionManager with an empty ConcurrentHashMap for storing regions.
     */
    public BasicRegionManager() {
        this.regions = new ConcurrentHashMap<>();
    }

    @Override
    public void addRegion(GuardedRegion region) {
        regions.put(region.getId(), region);
    }

    @Override
    public Collection<GuardedRegion> getRegions() {
        return regions.values();
    }

    @Override
    public boolean isLocationInCuboid(Location location, Location minPoint, Location maxPoint) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double minX = Math.min(minPoint.getX(), maxPoint.getX());
        double minY = Math.min(minPoint.getY(), maxPoint.getY());
        double minZ = Math.min(minPoint.getZ(), maxPoint.getZ());

        double maxX = Math.max(minPoint.getX(), maxPoint.getX());
        double maxY = Math.max(minPoint.getY(), maxPoint.getY());
        double maxZ = Math.max(minPoint.getZ(), maxPoint.getZ());

        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    @Override
    public boolean doRegionsIntersect(Location region1Min, Location region1Max, Location region2Min, Location region2Max) {
        double minX1 = Math.min(region1Min.getX(), region1Max.getX());
        double minY1 = Math.min(region1Min.getY(), region1Max.getY());
        double minZ1 = Math.min(region1Min.getZ(), region1Max.getZ());

        double maxX1 = Math.max(region1Min.getX(), region1Max.getX());
        double maxY1 = Math.max(region1Min.getY(), region1Max.getY());
        double maxZ1 = Math.max(region1Min.getZ(), region1Max.getZ());

        double minX2 = Math.min(region2Min.getX(), region2Max.getX());
        double minY2 = Math.min(region2Min.getY(), region2Max.getY());
        double minZ2 = Math.min(region2Min.getZ(), region2Max.getZ());

        double maxX2 = Math.max(region2Min.getX(), region2Max.getX());
        double maxY2 = Math.max(region2Min.getY(), region2Max.getY());
        double maxZ2 = Math.max(region2Min.getZ(), region2Max.getZ());

        // Check if the regions do not intersect along any axis
        if (maxX1 < minX2 || minX1 > maxX2) return false;
        if (maxY1 < minY2 || minY1 > maxY2) return false;
        if (maxZ1 < minZ2 || minZ1 > maxZ2) return false;

        // If none of the above conditions are met, the regions intersect
        return true;
    }
}
