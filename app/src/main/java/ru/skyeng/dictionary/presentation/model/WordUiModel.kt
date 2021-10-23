package ru.skyeng.dictionary.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.skyeng.dictionary.data.model.SearchData
import ru.skyeng.dictionary.utils.Mapper
import javax.inject.Inject

@Parcelize
data class WordUiModel(
    val text: String,
    val translation: String,
    val transcription: String,
    val imageUrl: String?,
) : Parcelable

class WordUiModelMapper @Inject constructor() :
    Mapper<SearchData, WordUiModel> {

    override fun map(entity: SearchData): WordUiModel = with(entity) {
        return WordUiModel(
            text = text,
            translation = meanings[0].translation.text,
            transcription = meanings[0].transcription,
            imageUrl = meanings[0].imageUrl,
        )
    }
}
