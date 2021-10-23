package ru.skyeng.dictionary.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.skyeng.dictionary.data.model.SearchData
import ru.skyeng.dictionary.network.DataWrapper
import ru.skyeng.dictionary.data.repository.DictionaryRepository
import ru.skyeng.dictionary.network.Status
import javax.inject.Inject

class DictionaryInteractor @Inject constructor(
    private val repository: DictionaryRepository,
) {

    private val coroutineContext = Dispatchers.IO + SupervisorJob()
    private val scope = CoroutineScope(coroutineContext)

    fun search(
        word: String,
        loadNextPage: Boolean,
        loadCallback: (DataWrapper<SearchData>) -> Unit
    ) {
        loadCallback.invoke(DataWrapper(Status.Loading))
        scope.launch {
            val response = repository.search(word, loadNextPage)
            val result: DataWrapper<SearchData> = if (response.data != null) {
                DataWrapper(Status.Success, response.data)
            } else {
                DataWrapper(Status.Error(response.throwable), null)
            }
            loadCallback.invoke(result)
        }
    }
}
