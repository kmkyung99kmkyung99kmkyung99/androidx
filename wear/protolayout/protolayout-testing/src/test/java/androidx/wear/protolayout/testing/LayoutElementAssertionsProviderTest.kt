/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.wear.protolayout.testing

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.wear.protolayout.LayoutElementBuilders.Box
import androidx.wear.protolayout.LayoutElementBuilders.Column
import androidx.wear.protolayout.LayoutElementBuilders.Image
import androidx.wear.protolayout.LayoutElementBuilders.Layout
import androidx.wear.protolayout.LayoutElementBuilders.Row
import androidx.wear.protolayout.LayoutElementBuilders.Text
import com.google.common.truth.ExpectFailure
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertThrows
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.internal.DoNotInstrument

@RunWith(AndroidJUnit4::class)
@DoNotInstrument
class LayoutElementAssertionsProviderTest {

    @Test
    fun primaryConstructor_onRoot() {
        assertTrue(LayoutElementAssertionsProvider(TEST_LAYOUT.root!!).onRoot().element is Box)
    }

    @Test
    fun secondaryConstructor_onRoot() {
        assertTrue(LayoutElementAssertionsProvider(TEST_LAYOUT).onRoot().element is Box)
    }

    @Test
    fun onRoot_description() {
        val assertionError =
            assertThrows(AssertionError::class.java) {
                LayoutElementAssertionsProvider(TEST_LAYOUT).onRoot().assertDoesNotExist()
            }

        val rootDescription = "root"

        ExpectFailure.assertThat(assertionError)
            .hasMessageThat()
            .isEqualTo("Expected $rootDescription to not exist, but it does.")
    }

    @Test
    fun onElement_isImage() {
        val firstImageElement =
            LayoutElementAssertionsProvider(TEST_LAYOUT).onElement(isImage).element as Image
        assertThat(firstImageElement.resourceId!!.value).isEqualTo("image1")
    }

    @Test
    fun onElement_isText() {
        val firstTextElement =
            LayoutElementAssertionsProvider(TEST_LAYOUT).onElement(isText).element as Text
        assertThat(firstTextElement.text!!.value).isEqualTo("text1")
    }

    @Test
    fun onElement_description() {
        val assertionError =
            assertThrows(AssertionError::class.java) {
                LayoutElementAssertionsProvider(TEST_LAYOUT).onElement(isText).assertDoesNotExist()
            }

        val elementDescription = "element matching '${isText.description}'"

        ExpectFailure.assertThat(assertionError)
            .hasMessageThat()
            .isEqualTo("Expected $elementDescription to not exist, but it does.")
    }

    companion object {
        val isBox = LayoutElementMatcher("Element type is Box") { it is Box }
        val isImage = LayoutElementMatcher("Element type is Image") { it is Image }
        val isText = LayoutElementMatcher("Element type is Text") { it is Text }
        val TEST_LAYOUT =
            Layout.Builder()
                .setRoot(
                    Box.Builder()
                        .addContent(
                            Row.Builder()
                                .addContent(Image.Builder().setResourceId("image1").build())
                                .addContent(Image.Builder().setResourceId("image2").build())
                                .build()
                        )
                        .addContent(
                            Column.Builder()
                                .addContent(Text.Builder().setText("text1").build())
                                .addContent(Text.Builder().setText("text2").build())
                                .build()
                        )
                        .build()
                )
                .build()
    }
}
