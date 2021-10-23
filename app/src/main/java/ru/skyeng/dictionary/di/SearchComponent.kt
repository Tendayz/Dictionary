package ru.skyeng.dictionary.di

import androidx.lifecycle.ViewModelStore
import dagger.BindsInstance
import dagger.Component
import ru.skyeng.dictionary.presentation.view.SearchFragment

@FragmentScope
@Component(
    dependencies = [
        MainComponent::class
    ],
    modules = [SearchModule::class]
)
interface SearchComponent {

    fun inject(fragment: SearchFragment)

    @Component.Factory
    interface Factory {
        fun create(
            mainComponent: MainComponent,
            @BindsInstance viewModelStore: ViewModelStore,
        ): SearchComponent
    }
}
