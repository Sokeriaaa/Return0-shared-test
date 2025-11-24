/**
 * Copyright (C) 2025 Sokeriaaa
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

import sokeriaaa.return0.shared.common.helpers.chance
import sokeriaaa.return0.shared.common.helpers.withChance
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RandomHelperTest {
    @Test
    fun `chance(int) true`() {
        val rng = FakeRandom(0)
        assertTrue(chance(success = 1, base = 2, random = rng))
    }

    @Test
    fun `chance(int) false`() {
        val rng = FakeRandom(1)
        assertFalse(chance(success = 1, base = 2, random = rng))
    }

    @Test
    fun `chance(long) true`() {
        val rng = FakeRandom(0L)
        assertTrue(chance(success = 1L, base = 2L, random = rng))
    }

    @Test
    fun `chance(long) false`() {
        val rng = FakeRandom(1L)
        assertFalse(chance(success = 1L, base = 2L, random = rng))
    }

    @Test
    fun `chance(double) true`() {
        val rng = FakeRandom(0.2)
        assertTrue(chance(success = 0.5, base = 1.0, random = rng))
    }

    @Test
    fun `chance(double) false`() {
        val rng = FakeRandom(0.8)
        assertFalse(chance(success = 0.5, base = 1.0, random = rng))
    }

    @Test
    fun `chance(float) true`() {
        val rng = FakeRandom(0.1f)
        assertTrue(chance(success = 0.5f, base = 1f, random = rng))
    }

    @Test
    fun `chance(float) false`() {
        val rng = FakeRandom(0.8f)
        assertFalse(chance(success = 0.5f, base = 1f, random = rng))
    }

    @Test
    fun `withChance runs callback`() {
        var called = false
        val rng = FakeRandom(0)
        withChance(success = 1, base = 2, random = rng) { called = true }
        assertTrue(called)
    }

    @Test
    fun `withChance does not run callback`() {
        var called = false
        val rng = FakeRandom(1)
        withChance(success = 1, base = 2, random = rng) { called = true }
        assertFalse(called)
    }
}