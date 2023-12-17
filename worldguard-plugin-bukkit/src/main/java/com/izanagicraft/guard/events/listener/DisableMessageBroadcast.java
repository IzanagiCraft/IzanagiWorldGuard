package com.izanagicraft.guard.events.listener;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:DisableMessageBroadcast
 * <p>
 * A listener class that disables various broadcast messages related to player events.
 * <p>
 * This class extends GuardListener and is designed to be used with the IzanagiWorldGuard plugin.
 * It suppresses join messages, quit messages, death messages, and advancement messages for players.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class DisableMessageBroadcast extends GuardListener {

    /**
     * Constructs a new DisableMessageBroadcast listener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public DisableMessageBroadcast(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Disables the join message for a player when they join the server.
     *
     * @param event The PlayerJoinEvent.
     */
    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        event.joinMessage(null);
    }

    /**
     * Disables the quit message for a player when they leave the server.
     *
     * @param event The PlayerQuitEvent.
     */
    @EventHandler
    public void onEvent(PlayerQuitEvent event) {
        event.quitMessage(null);
    }

    /**
     * Disables the death message for a player when they die.
     *
     * @param event The PlayerDeathEvent.
     */
    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        event.deathMessage(null);
    }

    /**
     * Disables the advancement message for a player when they complete an advancement.
     *
     * @param event The PlayerAdvancementDoneEvent.
     */
    @EventHandler
    public void onEvent(PlayerAdvancementDoneEvent event) {
        event.message(null);
    }

}
