package com.izanagicraft.guard.region;

import org.bukkit.Location;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.region:BasicGuardedRegion
 * <p>
 * Basic implementation of the {@link GuardedRegion} interface.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class BasicGuardedRegion implements GuardedRegion {

    private String id;
    private Location minPoint;
    private Location maxPoint;

    public BasicGuardedRegion(String id, Location minPoint, Location maxPoint) {
        this.id = id;
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Location getMinPoint() {
        return minPoint;
    }

    @Override
    public Location getMaxPoint() {
        return maxPoint;
    }

}
