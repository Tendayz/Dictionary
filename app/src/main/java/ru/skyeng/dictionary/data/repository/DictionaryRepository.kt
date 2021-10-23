package ru.skyeng.dictionary.data.repository

import okhttp3.ResponseBody
import org.json.JSONObject
import ru.skyeng.dictionary.data.model.SearchData
import ru.skyeng.dictionary.network.DictionaryApi
import ru.skyeng.dictionary.network.BaseResponse
import javax.inject.Inject

private const val ERROR = "error"
private const val UNKNOWN_ERROR = "UnknownError"

interface DictionaryRepository {
    suspend fun search(word: String, loadNextPage: Boolean): BaseResponse<SearchData>
}

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: DictionaryApi,
) : DictionaryRepository {

    private var currentPage = 1

    override suspend fun search(word: String, loadNextPage: Boolean): BaseResponse<SearchData> {
        currentPage = if (loadNextPage) {
            currentPage + 1
        } else {
            1
        }

        return try {
            val result = dictionaryApi.search(word, currentPage)
            if (result.isSuccessful) {
                BaseResponse(result.body())
            } else {
                BaseResponse(null, getErrorMessage(result.errorBody()))
            }
        } catch (e: Exception) {
            BaseResponse(null, Throwable(e.localizedMessage))
        }
    }

    private fun getErrorMessage(errorBody: ResponseBody?): Throwable {
        if (errorBody == null) {
            return Throwable(UNKNOWN_ERROR)
        }

        val error = JSONObject(errorBody.string())
        return if (error.has(ERROR)) {
            Throwable(error.getString(ERROR))
        } else {
            Throwable(UNKNOWN_ERROR)
        }
    }
}
