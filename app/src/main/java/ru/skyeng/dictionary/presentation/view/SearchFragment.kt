package ru.skyeng.dictionary.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_search.etSearch
import kotlinx.android.synthetic.main.fragment_search.searchRecyclerView
import ru.skyeng.dictionary.R
import ru.skyeng.dictionary.di.DaggerSearchComponent
import ru.skyeng.dictionary.di.MainComponentProvider
import ru.skyeng.dictionary.presentation.adapter.WordsRecyclerViewAdapter
import ru.skyeng.dictionary.presentation.viewmodel.SearchViewModel
import ru.skyeng.dictionary.presentation.viewmodel.SelectWordListener
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_search.progressBar
import ru.skyeng.dictionary.presentation.model.WordUiModel

class SearchFragment : Fragment(R.layout.fragment_search), SelectWordListener {

    @Inject
    lateinit var viewModel: SearchViewModel

    private val textWatcher by lazy {
        object : TextWatcher {
            private val SEARCH_DELAY = 500L
            private var timer = Timer()

            override fun afterTextChanged(s: Editable) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            viewModel.searchWords(s.toString(), false)
                        }
                    },
                    SEARCH_DELAY
                )
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDependencies()
        initAdapter()
        observeData()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        etSearch.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        etSearch.removeTextChangedListener(textWatcher)
    }

    override fun selectWord(word: WordUiModel) {
        viewModel.navigateWordScreen(word)
    }

    private fun initDependencies() {
        DaggerSearchComponent.factory().create(
            (requireActivity() as MainComponentProvider).provideMainComponent(),
            viewModelStore
        ).inject(this)
    }

    private fun observeData() {
        viewModel.searchWordsLiveData.observe(viewLifecycleOwner) { data ->
            (searchRecyclerView.adapter as WordsRecyclerViewAdapter).setWords(data)
        }
        viewModel.loadNextPageLiveData.observe(viewLifecycleOwner) { data ->
            (searchRecyclerView.adapter as WordsRecyclerViewAdapter).addWords(data)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            progressBar.isVisible = isLoading
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) { error ->
            Log.e(TAG, error)
        }
    }

    private fun initListeners() {
        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.searchWords(etSearch.text.toString(), true)
                }
            }
        })
    }

    private fun initAdapter() {
        val searchAdapter = WordsRecyclerViewAdapter(this)
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(requireContext(), R.drawable.item_divider)?.let { setDrawable(it) }
            }
        )
    }

    companion object {

        const val TAG = "SearchFragment"
    }
}
