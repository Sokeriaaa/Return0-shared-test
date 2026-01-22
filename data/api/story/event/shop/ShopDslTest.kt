package sokeriaaa.return0.test.shared.data.api.story.event.shop

import sokeriaaa.return0.shared.common.helpers.TimeHelper
import sokeriaaa.return0.shared.data.api.component.value.Value
import sokeriaaa.return0.shared.data.api.story.event.shop.buildShopEntries
import sokeriaaa.return0.shared.data.models.component.conditions.CommonCondition
import sokeriaaa.return0.shared.data.models.component.values.TimeValue
import sokeriaaa.return0.shared.data.models.story.currency.CurrencyType
import sokeriaaa.return0.shared.data.models.story.event.interactive.shop.ShopEntry
import kotlin.test.Test
import kotlin.test.assertEquals

class ShopDslTest {

    @Test
    fun `ShopDsl constructed correctly - normal1`() {
        val entries = buildShopEntries {
            inventory("awesome_item") soldFor 100.token
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ShopEntry.Item.Inventory("awesome_item"),
            actual = item?.item
        )
        assertEquals(
            expected = Value(100),
            actual = item?.price
        )
        assertEquals(
            expected = CurrencyType.TOKEN,
            actual = item?.currency
        )
    }

    @Test
    fun `ShopDsl constructed correctly - normal2`() {
        val entries = buildShopEntries {
            inventory("another_item") soldFor 5.crypto
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ShopEntry.Item.Inventory("another_item"),
            actual = item?.item
        )
        assertEquals(
            expected = Value(5),
            actual = item?.price
        )
        assertEquals(
            expected = CurrencyType.CRYPTO,
            actual = item?.currency
        )
    }

    @Test
    fun `ShopDsl constructed correctly - limited`() {
        val entries = buildShopEntries {
            (inventory("rare_item") soldFor 1000.token)
                .limitFor(100)
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ShopEntry.Item.Inventory("rare_item"),
            actual = item?.item
        )
        assertEquals(
            expected = Value(1000),
            actual = item?.price
        )
        assertEquals(
            expected = CurrencyType.TOKEN,
            actual = item?.currency
        )
        assertEquals(
            expected = Value(100),
            actual = item?.limit
        )
    }

    @Test
    fun `ShopDsl constructed correctly - refreshed`() {
        val entries = buildShopEntries {
            (inventory("dont_worry_this_wil_be_restored") soldFor 42.token)
                .refreshAfter(TimeValue.After(TimeHelper.ONE_DAY))
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ShopEntry.Item.Inventory("dont_worry_this_wil_be_restored"),
            actual = item?.item
        )
        assertEquals(
            expected = Value(42),
            actual = item?.price
        )
        assertEquals(
            expected = CurrencyType.TOKEN,
            actual = item?.currency
        )
        assertEquals(
            expected = TimeValue.After(TimeHelper.ONE_DAY),
            actual = item?.refreshAfter
        )
    }

    @Test
    fun `ShopDsl constructed correctly - available`() {
        val entries = buildShopEntries {
            (inventory("plz_buy_this_later") soldFor 999.token)
                .availableWhen(CommonCondition.False)
        }
        val item = entries.getOrNull(0)
        assertEquals(
            expected = ShopEntry.Item.Inventory("plz_buy_this_later"),
            actual = item?.item
        )
        assertEquals(
            expected = Value(999),
            actual = item?.price
        )
        assertEquals(
            expected = CurrencyType.TOKEN,
            actual = item?.currency
        )
        assertEquals(
            expected = CommonCondition.False,
            actual = item?.isAvailable
        )
    }
}