package ru.skyeng.dictionary.presentation.mapper

import ru.skyeng.dictionary.data.model.SearchData
import ru.skyeng.dictionary.presentation.model.WordUiModel

private const val ID = 173
private const val TEXT = "apple"
private const val MEANING_ID = "226138"
private const val TRANSLATION = "яблоко"
private const val IMAGE_URL = "//d2zkmv5t5kao9.cloudfront.net/images/5e1a9ba9b430f74bbfde59e65f77cbff.jpeg?w=96&h=72"
private const val TRANSCRIPTION ="\\u044f\\u0431\\u043b\\u043e\\u043a\\u043e"

object SearchTestData {

    val searchData = SearchData(
        id = ID,
        text = TEXT,
        meanings = listOf(
            SearchData.Meaning(
                id = MEANING_ID,
                translation = SearchData.Meaning.Translation(TRANSLATION),
                imageUrl = IMAGE_URL,
                transcription = TRANSCRIPTION
            )
        )
    )

    val wordUiModel = WordUiModel(
        text = TEXT,
        translation = TRANSLATION,
        transcription = TRANSCRIPTION,
        imageUrl = IMAGE_URL
    )
}
