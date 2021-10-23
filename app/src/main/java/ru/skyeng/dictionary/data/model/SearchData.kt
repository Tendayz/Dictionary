package ru.skyeng.dictionary.data.model

import com.google.gson.annotations.SerializedName

data class SearchData(

    @SerializedName("id")
    val id: Int,

    @SerializedName("text")
    val text: String,

    @SerializedName("meanings")
    val meanings: List<Meaning>,
) {

    data class Meaning(

        @SerializedName("id")
        val id: String,

        @SerializedName("partOfSpeechCode")
        val partOfSpeechCode: String? = null,

        @SerializedName("translation")
        val translation: Translation,

        @SerializedName("previewUrl")
        val previewUrl: String? = null,

        @SerializedName("imageUrl")
        val imageUrl: String? = null,

        @SerializedName("transcription")
        val transcription: String,

        @SerializedName("soundUrl")
        val soundUrl: String? = null,
    ) {

        data class Translation(

            @SerializedName("text")
            val text: String,

            @SerializedName("note")
            val note: String? = null,
        )
    }
}
