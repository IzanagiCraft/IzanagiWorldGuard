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

package com.izanagicraft.guard.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.commands:CommandSource
 * <p>
 * Represents a wrapper around a Bukkit CommandSender, providing additional functionality
 * for easier handling and abstraction in plugin code.
 * <p>
 * This class allows treating both players and the console as a unified command source.
 * It provides methods to check the type of the underlying sender, access the original sender,
 * and perform common CommandSender actions.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class CommandSource implements CommandSender {

    private CommandSender sender;
    private Player player;
    private ConsoleCommandSender consoleSender;

    /**
     * Constructs a new CommandSource wrapping the given Bukkit CommandSender.
     *
     * @param sender The original Bukkit CommandSender to be wrapped.
     */
    public CommandSource(CommandSender sender) {
        this.sender = sender;
        if (this.isPlayer()) this.player = (Player) this.sender;
        if (this.isConsole()) this.consoleSender = (ConsoleCommandSender) this.sender;
    }

    /**
     * Creates a CommandSource instance based on the provided Bukkit CommandSender.
     *
     * @param sender The original Bukkit CommandSender to be wrapped.
     * @return A CommandSource instance wrapping the provided CommandSender.
     */
    public static CommandSource of(CommandSender sender) {
        return new CommandSource(sender);
    }

    /**
     * Checks if the underlying sender is a player.
     *
     * @return True if the sender is a player, otherwise false.
     */
    public boolean isPlayer() {
        return this.sender instanceof Player;
    }

    /**
     * Checks if the underlying sender is the console.
     *
     * @return True if the sender is the console, otherwise false.
     */
    public boolean isConsole() {
        return this.sender instanceof ConsoleCommandSender;
    }

    /**
     * Gets the original Bukkit CommandSender wrapped by this CommandSource.
     *
     * @return The original Bukkit CommandSender.
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Gets the player associated with the command source, if it is a player.
     *
     * @return The player, or null if the command source is not a player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the console sender associated with the command source, if it is the console.
     *
     * @return The ConsoleCommandSender, or null if the command source is not the console.
     */
    public ConsoleCommandSender getConsoleSender() {
        return consoleSender;
    }

    // ========================= SENDER IMPL =========================

    @Override
    public void sendMessage(@NotNull String message) {
        this.sender.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull String... messages) {
        this.sender.sendMessage(messages);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String message) {
        this.sender.sendMessage(sender, message);
    }

    @Override
    public void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        this.sender.sendMessage(sender, messages);
    }

    @Override
    public @NotNull Server getServer() {
        return this.sender.getServer();
    }

    @Override
    public @NotNull String getName() {
        return this.sender.getName();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return this.sender.spigot();
    }

    @Override
    public @NotNull Component name() {
        return this.sender.name();
    }

    @Override
    public boolean isPermissionSet(@NotNull String name) {
        return this.sender.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission perm) {
        return this.sender.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull String name) {
        return this.sender.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull Permission perm) {
        return this.sender.hasPermission(perm);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        return this.sender.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        return this.sender.addAttachment(plugin);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value, int ticks) {
        return this.sender.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        return this.sender.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment attachment) {
        this.sender.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        this.sender.recalculatePermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.sender.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return this.sender.isOp();
    }

    @Override
    public void setOp(boolean value) {
        this.sender.setOp(value);
    }
}
