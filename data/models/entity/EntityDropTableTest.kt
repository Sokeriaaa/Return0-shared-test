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
package sokeriaaa.return0.test.shared.data.models.entity

import sokeriaaa.return0.shared.data.models.entity.EntityDropTable
import sokeriaaa.return0.test.shared.common.helpers.FakeRandom
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class EntityDropTableTest {

    @Test
    fun `base and levelBonus without floating randomness`() {
        val table = EntityDropTable(
            listOf(
                EntityDropTable.DropEntry(
                    itemKey = "loot_1",
                    base = 10f,
                    levelBonus = 2f,
                    floating = 0f, // no randomness
                )
            )
        )

        val rewards = table.generateRewards(
            enemyLevel = 5,
            random = FakeRandom(0f)
        )

        // (2 * 5 + 10) * 1 = 20
        assertEquals(20, rewards["loot_1"])
    }

    @Test
    fun `floating minimum factor`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_2",
            base = 0f,
            levelBonus = 10f,
            floating = 0.25f
        )

        val table = EntityDropTable(listOf(entry))

        val rewards = table.generateRewards(
            enemyLevel = 1,
            random = FakeRandom(0f) // min factor: 1 - floating
        )

        // (10 * 1) * 0.75 = 7.5 -> 7
        assertEquals(7, rewards["loot_2"])
    }

    @Test
    fun `floating maximum factor`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_2",
            base = 0f,
            levelBonus = 10f,
            floating = 0.25f
        )

        val table = EntityDropTable(listOf(entry))

        val rewards = table.generateRewards(
            enemyLevel = 1,
            random = FakeRandom(1f) // max factor: 1 + floating
        )

        // (10 * 1) * 1.25 = 12.5 -> 12
        assertEquals(12, rewards["loot_2"])
    }

    @Test
    fun `floating mid factor`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_3",
            base = 5f,
            levelBonus = 5f,
            floating = 0.2f
        )

        val table = EntityDropTable(listOf(entry))

        val rewards = table.generateRewards(
            enemyLevel = 2,
            random = FakeRandom(0.5f) // neutral factor
        )

        // (5*2 + 5) * 1 = 15
        assertEquals(15, rewards["loot_3"])
    }

    @Test
    fun `max cap is enforced`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_1",
            base = 0f,
            levelBonus = 100f,
            floating = 0f,
            max = 50
        )

        val table = EntityDropTable(listOf(entry))

        val rewards = table.generateRewards(
            enemyLevel = 10,
            random = FakeRandom(0f)
        )

        // Raw = 1000 → capped to 50
        assertEquals(50, rewards["loot_1"])
    }

    @Test
    fun `negative result coerces to zero`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_bug",
            base = -100f,
            levelBonus = 0f,
            floating = 0f
        )

        val table = EntityDropTable(listOf(entry))

        val rewards = table.generateRewards(
            enemyLevel = 1,
            random = FakeRandom(0f)
        )

        assertEquals(0, rewards["loot_bug"])
    }

    @Test
    fun `multiple entries are generated independently`() {
        val table = EntityDropTable(
            listOf(
                EntityDropTable.DropEntry(
                    itemKey = "loot_1",
                    base = 10f,
                    levelBonus = 1f,
                    floating = 0f
                ),
                EntityDropTable.DropEntry(
                    itemKey = "loot_2",
                    base = 0f,
                    levelBonus = 2f,
                    floating = 0f
                )
            )
        )

        val rewards = table.generateRewards(
            enemyLevel = 5,
            random = FakeRandom(0f, 0f)
        )

        assertEquals(15, rewards["loot_1"])
        assertEquals(10, rewards["loot_2"])
    }

    @Test
    fun `fake random exhaustion throws error`() {
        val entry = EntityDropTable.DropEntry(
            itemKey = "loot_1",
            base = 1f,
            levelBonus = 1f,
            floating = 0.5f
        )

        val table = EntityDropTable(listOf(entry))

        assertFailsWith<IllegalStateException> {
            table.generateRewards(
                enemyLevel = 1,
                random = FakeRandom() // no values
            )
        }
    }
}
