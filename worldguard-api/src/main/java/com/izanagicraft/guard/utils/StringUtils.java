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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IzanagiWorldGuard; com.izanagicraft.guard.utils:StringUtils
 * <p>
 * A utility class for string manipulation in the IzanagiWorldGuard plugin.
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class StringUtils {

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

    /**
     * Fast format a string with given values for placeholders.
     *
     * @param format The format string with placeholders.
     * @param values Values to replace placeholders.
     * @return Formatted string.
     */
    public String fastFormat(String format, Map<String, Object> values) {
        // Create a StringBuilder to modify the format string
        StringBuilder formatter = new StringBuilder(format);

        // Create a list to store the replacement values
        List<Object> valueList = new ArrayList<>();

        // Create a matcher to find placeholders in the format string
        Matcher matcher = Pattern.compile("\\$\\{(\\w+)}").matcher(format);

        // Iterate through the format string and find placeholders
        while (matcher.find()) {
            // Extract the placeholder key from the match
            String key = matcher.group(1);

            // Create the format key in the format "${key}"
            String formatKey = String.format("${%s}", key);

            // Find the index of the format key in the formatter
            int index = formatter.indexOf(formatKey);

            // If the format key is found
            if (index != -1) {
                // Replace the format key with "%s" for formatting
                formatter.replace(index, index + formatKey.length(), "%s");

                // Add the corresponding value to the value list
                valueList.add(values.get(key));
            }
        }

        // Use String.format to replace placeholders with values and return the formatted string
        return String.format(formatter.toString(), valueList.toArray());
    }

}
