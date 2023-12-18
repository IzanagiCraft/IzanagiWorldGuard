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

package tests;

import com.izanagicraft.guard.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * IzanagiWorldGuard; tests:StringUtilsTest
 *
 * @author <a href="https://github.com/sanguine6660">@sanguine6660</a>
 * @since 18.12.2023
 */
public class StringUtilsTest {

    @Test
    public void testStartsWithIgnoreCase() {
        assertTrue(StringUtils.startsWithIgnoreCase("Hello, World!", "hello"));
        assertTrue(StringUtils.startsWithIgnoreCase("Hello, World!", "HELLO"));
        assertFalse(StringUtils.startsWithIgnoreCase("Hello, World!", "world"));
        assertFalse(StringUtils.startsWithIgnoreCase("Hello, World!", "WORLD"));
        assertFalse(StringUtils.startsWithIgnoreCase("Hello, World!", "Hi"));
    }

    @Test
    public void testCopyPartialMatches() {
        Collection<String> available = Arrays.asList("apple", "banana", "cherry", "date");
        Collection<String> toAppend = new ArrayList<>();
        StringUtils.copyPartialMatches("a", available, toAppend);
        assertEquals(Arrays.asList("apple"), toAppend);

        toAppend.clear();
        StringUtils.copyPartialMatches("b", available, toAppend);
        assertEquals(Arrays.asList("banana"), toAppend);

        toAppend.clear();
        StringUtils.copyPartialMatches("c", available, toAppend);
        assertEquals(Arrays.asList("cherry"), toAppend);

        toAppend.clear();
        StringUtils.copyPartialMatches("d", available, toAppend);
        assertEquals(Arrays.asList("date"), toAppend);

        toAppend.clear();
        StringUtils.copyPartialMatches("e", available, toAppend);
        assertEquals(Arrays.asList(), toAppend);
    }

    @Test
    public void testFastFormat() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "John");
        values.put("age", 25);
        values.put("city", "New York");

        String result = new StringUtils().fastFormat("Hello, my name is ${name}. I am ${age} years old. I live in ${city}.", values);

        assertEquals("Hello, my name is John. I am 25 years old. I live in New York.", result);
    }

    @Test
    public void testFastFormatNullPlaceholder() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Jane");

        String result = new StringUtils().fastFormat("Hello, my name is ${name}. I am ${age} years old.", values);

        assertEquals("Hello, my name is Jane. I am null years old.", result);
    }
}
