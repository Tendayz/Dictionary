package ru.skyeng.dictionary.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_word.imageContainer
import kotlinx.android.synthetic.main.fragment_word.tvWord
import kotlinx.android.synthetic.main.fragment_word.toolbar
import kotlinx.android.synthetic.main.fragment_word.tvTranscription
import kotlinx.android.synthetic.main.fragment_word.tvTranslation
import kotlinx.android.synthetic.main.fragment_word.wordImage
import ru.skyeng.dictionary.R
import ru.skyeng.dictionary.presentation.model.WordUiModel

private const val BASE_GLIDE_URL = "https:"
private const val ARG_WORD_UI_MODEL = "word_ui_model"

class WordFragment : Fragment(R.layout.fragment_word) {

    private val wordUiModel by lazy {
        requireArguments().getParcelable<WordUiModel>(ARG_WORD_UI_MODEL)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        tvWord.text = wordUiModel?.text
        tvTranscription.text = String.format(getString(R.string.transcription), wordUiModel?.transcription)
        tvTranslation.text = wordUiModel?.translation

        wordUiModel?.imageUrl?.let { url ->
            Glide.with(this)
                .load(BASE_GLIDE_URL+url)
                .centerCrop()
                .into(wordImage)
        } ?: run {
            imageContainer.visibility = View.GONE
        }
    }

    companion object {

        fun getInstance(
            wordUiModel: WordUiModel
        ): WordFragment =
            WordFragment().apply {
                arguments = bundleOf(
                    ARG_WORD_UI_MODEL to wordUiModel
                )
            }
    }
}
