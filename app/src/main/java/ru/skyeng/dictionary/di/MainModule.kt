package ru.skyeng.dictionary.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.skyeng.dictionary.data.repository.DictionaryRepository
import ru.skyeng.dictionary.data.repository.DictionaryRepositoryImpl
import ru.skyeng.dictionary.domain.DictionaryInteractor
import ru.skyeng.dictionary.network.DictionaryApi
import javax.inject.Singleton

@Module
object MainModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun provideRouter(): Router =
        cicerone.router

    @Provides
    fun provideNavigatorHolder(): NavigatorHolder =
        cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        api: DictionaryApi,
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDictionaryInteractor(
        repository: DictionaryRepository,
    ): DictionaryInteractor {
        return DictionaryInteractor(repository)
    }
}
