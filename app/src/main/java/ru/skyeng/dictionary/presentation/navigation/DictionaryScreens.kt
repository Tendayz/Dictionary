package ru.skyeng.dictionary.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.skyeng.dictionary.presentation.model.WordUiModel
import ru.skyeng.dictionary.presentation.view.SearchFragment
import ru.skyeng.dictionary.presentation.view.WordFragment

object DictionaryScreens {

    fun searchFragment() = FragmentScreen {
        SearchFragment()
    }

    fun wordFragment(wordUiModel: WordUiModel) = FragmentScreen {
        WordFragment.getInstance(wordUiModel)
    }
}
