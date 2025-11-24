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

import sokeriaaa.return0.shared.common.helpers.JsonHelper
import sokeriaaa.return0.shared.common.helpers.JsonHelper.toJsonString
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonHelperTest {
    @Test
    fun `encodeToJsonString() encodes correctly`() {
        val data = JsonTestData("hello", 123)

        val json = JsonHelper.encodeToJsonString(data)

        assertEquals("""{"name":"hello","value":123}""", json)
    }

    @Test
    fun `decodeFromString() decodes correctly`() {
        val json = """{"name":"world","value":456}"""

        val data = JsonHelper.decodeFromString<JsonTestData>(json)

        assertEquals("world", data.name)
        assertEquals(456, data.value)
    }

    @Test
    fun `extension toJsonString() encodes correctly`() {
        val data = JsonTestData("foo", 999)

        val json = data.toJsonString()

        assertEquals("""{"name":"foo","value":999}""", json)
    }

    @Test
    fun `decodeFromString() ignores unknown keys`() {
        val json = """
        {
            "name": "test",
            "value": 42,
            "extra": "SHOULD_BE_IGNORED"
        }
        """.trimIndent()

        val data = JsonHelper.decodeFromString<JsonTestData>(json)

        assertEquals("test", data.name)
        assertEquals(42, data.value)
    }
}