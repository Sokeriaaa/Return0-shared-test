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
package sokeriaaa.return0.test.shared.data.api.story.event.interactive

import sokeriaaa.return0.shared.common.helpers.TimeHelper
import sokeriaaa.return0.shared.data.api.component.value.Value
import sokeriaaa.return0.shared.data.api.story.event.interactive.buildWorkbenchEntries
import sokeriaaa.return0.shared.data.models.component.conditions.CommonCondition
import sokeriaaa.return0.shared.data.models.component.values.TimeValue
import sokeriaaa.return0.shared.data.models.story.currency.CurrencyType
import sokeriaaa.return0.shared.data.models.story.event.interactive.ItemEntry
import kotlin.test.Test
import kotlin.test.assertEquals

class WorkbenchDslTest {

    @Test
    fun `WorkbenchDsl constructed correctly - normal1`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs (1 of "rare_material")
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - normal2`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material1",
                2 of "common_material2",
            ) outputs (1 of "rare_material")
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material1", amount = 3),
            actual = item?.input?.getOrNull(0)
        )
        assertEquals(
            expected = ItemEntry.Inventory("common_material2", amount = 2),
            actual = item?.input?.getOrNull(1)
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - normal3`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs listOf(
                1 of "rare_material1",
                2 of "rare_material2",
            )
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material1", amount = 1),
            actual = item?.output?.getOrNull(0)
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material2", amount = 2),
            actual = item?.output?.getOrNull(1)
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - normal4`() {
        val entries = buildWorkbenchEntries {
            inputting(
                10 of "plugin_material",
            ) outputs (1 ofPlugin "awesome_plugin")
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("plugin_material", amount = 10),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Plugin("awesome_plugin", tier = 0, amount = 1),
            actual = item?.output?.firstOrNull()
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - normal5`() {
        val entries = buildWorkbenchEntries {
            inputting(
                10 of "plugin_material",
            ) outputs (1 ofPlugin "awesome_plugin" withTier 3)
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("plugin_material", amount = 10),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Plugin("awesome_plugin", tier = 3, amount = 1),
            actual = item?.output?.firstOrNull()
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - price`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs (1 of "rare_material") costsFor 500.token
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
        assertEquals(
            expected = Value(500),
            actual = item?.price,
        )
        assertEquals(
            expected = CurrencyType.TOKEN,
            actual = item?.currency
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - limited`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs (1 of "rare_material") limitFor 100
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
        assertEquals(
            expected = Value(100),
            actual = item?.limit
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - refreshed`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs (1 of "rare_material") refreshAfter
                    TimeValue.After(TimeHelper.ONE_DAY)
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
        assertEquals(
            expected = TimeValue.After(TimeHelper.ONE_DAY),
            actual = item?.refreshAfter
        )
    }

    @Test
    fun `WorkbenchDsl constructed correctly - available`() {
        val entries = buildWorkbenchEntries {
            inputting(
                3 of "common_material",
            ) outputs (1 of "rare_material") availableWhen CommonCondition.False
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ItemEntry.Inventory("common_material", amount = 3),
            actual = item?.input?.firstOrNull()
        )
        assertEquals(
            expected = ItemEntry.Inventory("rare_material", amount = 1),
            actual = item?.output?.firstOrNull()
        )
        assertEquals(
            expected = CommonCondition.False,
            actual = item?.isAvailable
        )
    }
}