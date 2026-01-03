/**
 * Copyright (C) 2026 Sokeriaaa
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package sokeriaaa.return0.test.shared.common.helpers

import sokeriaaa.return0.shared.common.helpers.TimeHelper
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeHelperTest {
    @Test
    fun `nextDay should return next midnight UTC`() {
        // 1970-01-01 10:00 UTC
        val now = 10 * TimeHelper.ONE_HOUR
        val expected = TimeHelper.ONE_DAY
        val result = TimeHelper.nextDay(now)

        assertEquals(expected, result)
    }

    @Test
    fun `nextDay at midnight should return following day`() {
        // 1970-01-01 00:00 UTC
        val now = 0L
        val expected = TimeHelper.ONE_DAY
        val result = TimeHelper.nextDay(now)

        assertEquals(expected, result)
    }

    @Test
    fun `nextSunday from Thursday should return same week Sunday`() {
        // 1970-01-01 Thursday 00:00 UTC
        val now = 0L
        // Sunday = Jan 4, 1970
        val expected = 3 * TimeHelper.ONE_DAY
        val result = TimeHelper.nextSunday(now)

        assertEquals(expected, result)
    }

    @Test
    fun `nextSunday from Saturday should return next day`() {
        // Saturday Jan 3, 1970 12:00 UTC
        val now = 2 * TimeHelper.ONE_DAY + 12 * TimeHelper.ONE_HOUR
        val expected = 3 * TimeHelper.ONE_DAY
        val result = TimeHelper.nextSunday(now)

        assertEquals(expected, result)
    }

    @Test
    fun `nextSunday from Sunday midnight should return same Sunday`() {
        // Sunday Jan 4, 1970 00:00 UTC
        val now = 3 * TimeHelper.ONE_DAY
        val expected = 3 * TimeHelper.ONE_DAY
        val result = TimeHelper.nextSunday(now)

        assertEquals(expected, result)
    }
}