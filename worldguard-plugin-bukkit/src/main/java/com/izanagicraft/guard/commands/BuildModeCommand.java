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

import com.izanagicraft.guard.IzanagiWorldGuardPlugin;
import com.izanagicraft.guard.permissions.GuardPermission;
import com.izanagicraft.guard.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.commands:BuildModeCommand
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 19.12.2023
 */
public class BuildModeCommand extends GuardCommand {

    /**
     * List holding all buildMode player uuid's
     */
    public static List<UUID> buildModePlayers = new ArrayList<>();

    private final IzanagiWorldGuardPlugin plugin;

    /**
     * Constructs a new GuardCommand with the given name.
     *
     * @param plugin The instance of {@link IzanagiWorldGuardPlugin}
     */
    public BuildModeCommand(IzanagiWorldGuardPlugin plugin) {
        super("guardbuildmode");
        this.plugin = plugin;
        setAliases(List.of("gbm", "wgbuild"));
        setDescription("Toggle the build mode to get ignored by the Guard.");
        setUsage("&e/guardbuildmode");
        setPermission(GuardPermission.COMMAND_BUILDMODE.getName());
    }

    @Override
    public void error(CommandSource source, String commandLabel, Command command, String[] args, Throwable throwable) {
        if (source.hasPermission(GuardPermission.GROUPS_DEBUG.getName())) {
            MessageUtils.sendErrorMessage(source, throwable);
        }
    }

    @Override
    public void run(CommandSource source, String commandLabel, Command command, String[] args) {
        if (!source.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                && !source.hasPermission(GuardPermission.COMMAND_BUILDMODE.getName())) {
            return;
        }
        if (!source.isPlayer()) {
            MessageUtils.sendMessage(source, plugin.getTranslationHandler().translate("command.player.only"));
            return;
        }

        if (isBuildMode(source.getPlayer())) {
            buildModePlayers.remove(source.getPlayer().getUniqueId());
            MessageUtils.sendMessage(source, plugin.getTranslationHandler().translate(source.getPlayer().locale(), "command.buildmode.left"));
        } else {
            buildModePlayers.add(source.getPlayer().getUniqueId());
            MessageUtils.sendMessage(source, plugin.getTranslationHandler().translate(source.getPlayer().locale(), "command.buildmode.joined"));
        }
    }

    @Override
    public List<String> tab(CommandSource source, String commandLabel, Command cmd, String[] args) {
        return Collections.emptyList();
    }

    /**
     * Checks if a player is in buildMode automatically removes players from {@link BuildModeCommand#buildModePlayers} if no permission.
     *
     * @param player The player to check
     * @return true if enabled ( false if no permission or not joined )
     */
    public static boolean isBuildMode(Player player) {
        if (buildModePlayers.contains(player.getUniqueId())
                && (player.hasPermission(GuardPermission.GROUPS_ADMIN.getName())
                || player.hasPermission(GuardPermission.COMMAND_BUILDMODE.getName()))) return true;
        buildModePlayers.remove(player.getUniqueId());
        return false;
    }
}
