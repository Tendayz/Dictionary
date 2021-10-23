package ru.skyeng.dictionary.presentation.mapper

import junit.framework.Assert.assertEquals
import org.junit.Test
import ru.skyeng.dictionary.presentation.model.WordUiModelMapper

class WordUiModelMapperTest {

    @Test
    fun `map search response`() {
        assertEquals(
            WordUiModelMapper().map(SearchTestData.searchData),
            SearchTestData.wordUiModel
        )
    }
}
