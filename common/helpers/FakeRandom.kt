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

import kotlin.random.Random

/**
 * A fake random class extends [Random] for testing.
 *
 * [values] will return one by one in all "next" functions instead of a random value.
 */
class FakeRandom(private val values: List<Number>) : Random() {

    constructor(value: Number) : this(listOf(value))

    private var index = 0

    private fun next(): Number {
        if (index >= values.size)
            error("FakeRandom exhausted")
        return values[index++]
    }

    override fun nextBits(bitCount: Int): Int {
        // Use nextInt() then mask out only the requested bits.
        val value = next().toInt()
        return value and ((1 shl bitCount) - 1)
    }

    override fun nextInt(from: Int, until: Int): Int = next().toInt()
    override fun nextLong(from: Long, until: Long): Long = next().toLong()
    override fun nextDouble(): Double = next().toDouble()
    override fun nextFloat(): Float = next().toFloat()
}
