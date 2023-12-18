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
import com.izanagicraft.guard.flags.GuardFlag;
import com.izanagicraft.guard.permissions.GuardPermission;
import com.izanagicraft.guard.utils.MessageUtils;
import com.izanagicraft.guard.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.commands:WorldGuardCommand
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class WorldGuardCommand extends GuardCommand {
    private final IzanagiWorldGuardPlugin plugin;

    /**
     * Constructs a new GuardCommand with the given name.
     *
     * @param plugin the instance of the IzanagiWorldGuardPlugin
     */
    public WorldGuardCommand(IzanagiWorldGuardPlugin plugin) {
        super("worldguard");
        this.plugin = plugin;
        setAliases(List.of("wg", "guard"));
        setDescription("Guard your worlds and set region specific flags.");
        setUsage("&e/worldguard <reload/flag> [flag: <flagname> <value>]");
        setPermission(GuardPermission.COMMAND_WORLDGUARD.getName());
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
                && !source.hasPermission(GuardPermission.COMMAND_WORLDGUARD.getName())) {
            return;
        }

        int argCount = args.length;

        if (argCount == 0) {
            MessageUtils.sendPrefixedMessage(source, this.getUsage());
            return;
        }

        String subCommand = args[0];

        switch (subCommand.toLowerCase()) {
            case "reload":
                try {
                    plugin.reloadConfigurations();
                    MessageUtils.sendPrefixedMessage(source, "&aConfigs successfully reloaded.");
                } catch (Exception e) {
                    error(source, commandLabel, command, args, e);
                    MessageUtils.sendPrefixedMessage(source, "&cConfigs could not be reloaded. &eAdditional info in console.");
                }
                return;
            case "flag":
                if (!source.isPlayer()) {
                    MessageUtils.sendPrefixedMessage(source, "&cYou can only run this command as a player.");
                    return;
                }

                if (argCount == 3) {
                    GuardFlag choice = GuardFlag.getByName(args[1]);
                    if (choice == null) {
                        break;
                    }

                    YamlConfiguration config = plugin.getWorldConfigs().get(source.getPlayer().getWorld().getName());

                    if (config == null) {
                        MessageUtils.sendPrefixedMessage(source, "&cYour current world is not under protection. &aYou can remove it in the WorldGuard config.yml in section 'ignored' to protect it properly.");
                        return;
                    }

                    String newValue = args[2].toLowerCase();
                    if (!choice.getValidValues().contains(newValue)) {
                        MessageUtils.sendPrefixedMessage(source, "&cUnsupported Value: &e" + newValue);
                        MessageUtils.sendPrefixedMessage(source, "&aChoose one of: &e" + String.join(", ", choice.getValidValues()));
                        return;
                    }

                    config.set("flags." + choice.getFlagName(), newValue);

                    MessageUtils.sendPrefixedMessage(source, StringUtils.fastFormat(
                            "&aSuccessfully &7set flag [&e${flag}&7] for region [&7${region}&7] to [&9${newValue}&7].",
                            Map.of(
                                    "flag", choice.getFlagName(),
                                    "newValue", newValue,
                                    "region", "__global__"
                            )
                    ));
                    return;
                }
                break;
            default:
                break;
        }

        MessageUtils.sendPrefixedMessage(source, this.getUsage());
    }

    @Override
    public List<String> tab(CommandSource source, String commandLabel, Command cmd, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> available = new ArrayList<>();
        // Check the number of arguments provided
        int argCount = args.length;

        // Suggestions for the first argument
        if (argCount == 1) {
            available.add("reload");
            available.add("flag");

            StringUtils.copyPartialMatches(args[0], available, completions);
        }

        if (argCount == 2 && args[0].equalsIgnoreCase("flag")) {
            // TODO: filter permission based flags
            for (GuardFlag flag : GuardFlag.values()) {
                available.add(flag.getFlagName());
            }

            StringUtils.copyPartialMatches(args[1], available, completions);
        }

        if (argCount == 3 && args[0].equalsIgnoreCase("flag")) {
            // TODO: filter permission based flag values
            GuardFlag choice = GuardFlag.getByName(args[1]);
            // System.out.println("Choice: " + choice);
            if (choice == null) return completions;
            available.addAll(choice.getValidValues());
            // System.out.println(String.join(", ", available));
            StringUtils.copyPartialMatches(args[2], available, completions);
        }


        return completions;
    }

}
