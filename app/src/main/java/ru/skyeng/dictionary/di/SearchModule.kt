package ru.skyeng.dictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.skyeng.dictionary.domain.DictionaryInteractor
import ru.skyeng.dictionary.presentation.model.WordUiModelMapper
import ru.skyeng.dictionary.presentation.viewmodel.SearchViewModel

@Module
object SearchModule {

    @Provides
    fun searchViewModel(
        viewModelStore: ViewModelStore,
        interactor: DictionaryInteractor,
        router: Router,
        mapper: WordUiModelMapper
    ): SearchViewModel {
        val factory: ViewModelFactory<*> = ViewModelFactory<ViewModel> {
            SearchViewModel(interactor, router, mapper)
        }
        return ViewModelProvider(viewModelStore, factory)
            .get(SearchViewModel::class.java)
    }
}
