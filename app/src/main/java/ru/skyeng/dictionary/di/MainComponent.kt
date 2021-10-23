package ru.skyeng.dictionary.di

import androidx.lifecycle.ViewModelStore
import com.github.terrakok.cicerone.Router
import dagger.BindsInstance
import dagger.Component
import ru.skyeng.dictionary.data.repository.DictionaryRepository
import ru.skyeng.dictionary.network.DictionaryApi
import ru.skyeng.dictionary.network.NetworkModule
import ru.skyeng.dictionary.presentation.view.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class,
        NetworkModule::class
    ]
)
interface MainComponent {

    fun inject(activity: MainActivity)

    fun getDictionaryApi(): DictionaryApi

    fun getDictionaryRepository(): DictionaryRepository

    fun getRouter(): Router

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance viewModelStore: ViewModelStore,
        ): MainComponent
    }
}
