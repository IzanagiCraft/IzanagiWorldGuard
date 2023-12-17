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

package com.izanagicraft.guard.utils;

import com.izanagicraft.guard.GuardConstants;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

import java.util.Collection;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.utils:MessageUtils
 * <p>
 * A utility class for handling and sending formatted messages in the IzanagiWorldGuard plugin.
 * <p>
 * This class provides methods for sending messages to CommandSenders with consistent styling,
 * as well as utility methods for text manipulation and matching. It uses the LegacyComponentSerializer
 * for converting legacy color codes to modern Adventure components.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 17.12.2023
 */
public class MessageUtils {

    private static LegacyComponentSerializer componentSerializer = LegacyComponentSerializer.builder()
            .character('&').hexCharacter('#').build();

    /**
     * Gets the instance of LegacyComponentSerializer used for message formatting.
     *
     * @return The LegacyComponentSerializer instance.
     */
    public static LegacyComponentSerializer getComponentSerializer() {
        return componentSerializer;
    }

    private MessageUtils() {
    }

    /**
     * Sends a formatted message to a CommandSender.
     *
     * @param sender The CommandSender to receive the message.
     * @param text   The text of the message.
     */
    public static void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(componentSerializer.deserialize(GuardConstants.CHAT_COLOR + text));
    }

    /**
     * Sends a prefixed and formatted message to a CommandSender.
     *
     * @param sender The CommandSender to receive the message.
     * @param text   The text of the message.
     */
    public static void sendPrefixedMessage(CommandSender sender, String text) {
        sendMessage(sender, GuardConstants.CHAT_PREFIX + GuardConstants.CHAT_COLOR + text);
    }

    /**
     * Sends an array of messages to a CommandSender with consistent formatting.
     *
     * @param sender The CommandSender to receive the messages.
     * @param lines  The array of message lines.
     */
    public static void sendMessages(CommandSender sender, String... lines) {
        for (String line : lines) sendMessage(sender, line);
    }

    /**
     * Sends an array of prefixed and formatted messages to a CommandSender.
     *
     * @param sender The CommandSender to receive the messages.
     * @param lines  The array of message lines.
     */
    public static void sendPrefixedMessages(CommandSender sender, String... lines) {
        for (String line : lines) sendPrefixedMessage(sender, line);
    }

    /**
     * Sends a wrapped message to a CommandSender, where each line is prefixed and formatted.
     *
     * @param sender The CommandSender to receive the wrapped message.
     * @param lines  The array of message lines to wrap and send.
     */
    public static void sendWrappedMessage(CommandSender sender, String... lines) {
        sendMessage(sender, "\n" + GuardConstants.CHAT_COLOR +
                String.join("\n" + GuardConstants.CHAT_COLOR, lines));
    }

    /**
     * Checks if a string starts with another string, ignoring case.
     *
     * @param string The input string.
     * @param prefix The prefix to check for.
     * @return True if the input string starts with the specified prefix, ignoring case; otherwise, false.
     */
    public static boolean startsWithIgnoreCase(String string, String prefix) {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    /**
     * Copies partial matches of a prefix from available strings to a collection.
     *
     * @param input     The input prefix.
     * @param available The collection of available strings.
     * @param toAppend  The collection to append matching strings.
     */
    public static void copyPartialMatches(String input, Collection<String> available, Collection<String> toAppend) {
        for (String string : available) {
            if (startsWithIgnoreCase(string, input)) {
                toAppend.add(string);
            }
        }
    }

}
