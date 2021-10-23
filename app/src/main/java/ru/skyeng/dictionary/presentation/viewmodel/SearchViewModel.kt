package ru.skyeng.dictionary.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import ru.skyeng.dictionary.domain.DictionaryInteractor
import ru.skyeng.dictionary.network.Status
import ru.skyeng.dictionary.presentation.model.WordUiModel
import ru.skyeng.dictionary.presentation.model.WordUiModelMapper
import ru.skyeng.dictionary.presentation.navigation.DictionaryScreens

class SearchViewModel(
    private val interactor: DictionaryInteractor,
    private val router: Router,
    private val mapper: WordUiModelMapper
) : ViewModel() {

    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    val searchWordsLiveData = MutableLiveData<List<WordUiModel>>()
    val loadNextPageLiveData = MutableLiveData<List<WordUiModel>>()

    fun searchWords(word: String, loadNextPage: Boolean) {
        interactor.search(word, loadNextPage) { dataWrapper ->
            when (dataWrapper.status) {
                is Status.Loading -> loadingLiveData.postValue(true)
                is Status.Error -> {
                    loadingLiveData.postValue(false)
                    errorLiveData.postValue(dataWrapper.status.throwable?.localizedMessage)
                }
                is Status.Success -> {
                    loadingLiveData.postValue(false)
                    if (!loadNextPage) {
                        searchWordsLiveData.postValue(mapper.map(dataWrapper.data!!))
                    } else {
                        loadNextPageLiveData.postValue(mapper.map(dataWrapper.data!!))
                    }
                }
            }
        }
    }

    fun navigateWordScreen(word: WordUiModel) {
        router.navigateTo(DictionaryScreens.wordFragment(word))
    }
}
