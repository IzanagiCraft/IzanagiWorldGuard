package com.izanagicraft.guard.events.listener;

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.events.GuardListener;
import com.izanagicraft.guard.flags.GuardFlag;
import com.izanagicraft.guard.utils.MessageUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.events.listener:PlayerChatListener
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class PlayerChatListener  extends GuardListener {
    /**
     * Constructs a new GuardListener with the specified IzanagiWorldGuardPlugin.
     *
     * @param plugin The IzanagiWorldGuardPlugin instance.
     */
    public PlayerChatListener(IzanagiWorldGuardPlugin plugin) {
        super(plugin);
    }

    /**
     * Handles the AsyncChatEvent to enforce chatting-related flags within protected regions.
     *
     * @param event The AsyncChatEvent.
     */
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        if(player == null) {
            event.setCancelled(true);
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAction = true;

        String allow = worldConfig.getString("flags." + GuardFlag.CHATTING.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAction = false;
        }

        // TODO: region based checks.

        if(!allowAction) {
            event.setCancelled(true);
            MessageUtils.sendPrefixedMessage(player, "&cYou're not allowed to chat here. &e(TODO translation)");
        }

    }

    /**
     * Handles the PlayerCommandPreprocessEvent to enforce command_execute-related flags within protected regions.
     *
     * @param event The PlayerCommandPreprocessEvent.
     */
    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(player == null) {
            event.setCancelled(true);
            return;
        }

        Location target = player.getLocation();

        YamlConfiguration worldConfig = getPlugin().getWorldConfigs().get(target.getWorld().getName());
        if (worldConfig == null) return;

        boolean allowAction = true;

        String allow = worldConfig.getString("flags." + GuardFlag.COMMAND_EXECUTE.getFlagName(), "false");

        if (allow.isEmpty() || allow.equalsIgnoreCase("empty")
                || allow.equalsIgnoreCase("false") || allow.equalsIgnoreCase("deny")) {
            allowAction = false;
        }

        // TODO: region based checks.

        if(!allowAction) {
            event.setCancelled(true);
            MessageUtils.sendPrefixedMessage(player, "&cYou're not allowed to execute commands here. &e(TODO translation)");
        }

    }
}
