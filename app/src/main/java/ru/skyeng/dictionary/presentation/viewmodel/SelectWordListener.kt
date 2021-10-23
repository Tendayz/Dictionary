package ru.skyeng.dictionary.presentation.viewmodel

import ru.skyeng.dictionary.presentation.model.WordUiModel

interface SelectWordListener {

    fun selectWord(word: WordUiModel)
}
