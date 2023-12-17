package com.izanagicraft.guard.events.event;

import com.izanagicraft.guard.region.GuardedRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.event:PlayerRegionEnterEvent
 * <p>
 * Represents an event that is triggered when a player enters a guarded region.
 * <p>
 * This event is called when a player moves into a region that is being monitored for entry.
 * Plugins can listen to this event to perform actions when a player enters a specific region.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class PlayerRegionEnterEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final GuardedRegion region;

    private boolean cancelled = false;

    /**
     * Constructs a new PlayerRegionEnterEvent.
     *
     * @param player The player who entered the region.
     * @param region The guarded region that the player entered.
     */
    public PlayerRegionEnterEvent(Player player, GuardedRegion region) {
        this.player = player;
        this.region = region;
    }

    /**
     * Gets the player who entered the region.
     *
     * @return The player who entered the region.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the guarded region that the player entered.
     *
     * @return The guarded region that the player entered.
     */
    public GuardedRegion getRegion() {
        return region;
    }

    /**
     * Gets the handler list for this event.
     *
     * @return The handler list for this event.
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the handler list for this event.
     *
     * @return The handler list for this event.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Checks if the event is cancelled.
     *
     * @return True if the event is cancelled, otherwise false.
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of the event.
     *
     * @param cancelled True to cancel the event, false to allow it.
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


}
