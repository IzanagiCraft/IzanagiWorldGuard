package com.izanagicraft.guard.events;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import org.bukkit.event.Listener;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events:GuardListener
 * <p>
 * An abstract listener class that serves as a base for event listeners in the IzanagiWorldGuard plugin.
 * <p>
 * This class implements the Bukkit Listener interface and is designed to be extended by specific
 * event listener classes. It provides a reference to the IzanagiWorldGuardPlugin for convenience.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public abstract class GuardListener implements Listener {

    private final IzanagiWorldGuardPlugin plugin;

    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public GuardListener(IzanagiWorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the IzanagiWorldGuardPlugin associated with this GuardListener.
     *
     * @return The IzanagiWorldGuardPlugin instance.
     */
    public IzanagiWorldGuardPlugin getPlugin() {
        return plugin;
    }

}
