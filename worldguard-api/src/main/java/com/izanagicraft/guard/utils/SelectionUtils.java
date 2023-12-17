package com.izanagicraft.guard.utils;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.utils:SelectionUtils
 * <p>
 * A utility class for retrieving WorldEdit regions associated with players.
 * <p>
 * This class provides synchronous and asynchronous methods for getting the WorldEdit region
 * of a player. It utilizes the WorldEditPlugin to retrieve the selection for a given player.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class SelectionUtils {

    private SelectionUtils() {
    }

    /**
     * Synchronous method to get the WorldEdit region of a player.
     *
     * @param player The player whose region is to be retrieved.
     * @return The WorldEdit region of the player.
     */
    public static Region getSelectedRegion(Player player) {
        if (player != null) {
            WorldEditPlugin worldEditPlugin = WorldEditPlugin.getInstance();
            if (worldEditPlugin != null) {
                return worldEditPlugin.getSession(player).getSelection(worldEditPlugin.getSession(player).getSelectionWorld());
            }
        }
        return null;
    }

    /**
     * Asynchronous method to get the WorldEdit region of a player.
     *
     * @param player The player whose region is to be retrieved.
     * @return A CompletableFuture that completes with the WorldEdit region of the player.
     */
    public static CompletableFuture<Region> getSelectedRegionAsync(Player player) {
        return CompletableFuture.supplyAsync(() -> getSelectedRegion(player));
    }

}
