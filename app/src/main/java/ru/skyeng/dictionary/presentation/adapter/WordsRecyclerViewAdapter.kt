package ru.skyeng.dictionary.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_word.view.tvTitle
import ru.skyeng.dictionary.R
import ru.skyeng.dictionary.presentation.model.WordUiModel
import ru.skyeng.dictionary.presentation.viewmodel.SelectWordListener

class WordsRecyclerViewAdapter(
    private val listener: SelectWordListener,
) : RecyclerView.Adapter<WordsRecyclerViewAdapter.WordViewHolder>() {

    private val data = mutableListOf<WordUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent, listener)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setWords(newDataList: List<WordUiModel>) {
        data.clear()
        data.addAll(newDataList)
        notifyDataSetChanged()
    }

    fun addWords(newDataList: List<WordUiModel>) {
        data.addAll(newDataList)
        notifyItemRangeInserted(data.size - newDataList.size, newDataList.size)
    }

    class WordViewHolder(
        rootView: View,
        private val listener: SelectWordListener
    ) : RecyclerView.ViewHolder(rootView) {

        companion object {

            fun create(
                viewGroup: ViewGroup,
                listener: SelectWordListener,
            ): WordViewHolder {
                return WordViewHolder(
                    LayoutInflater.from(viewGroup.context).inflate(R.layout.item_word, viewGroup, false),
                    listener
                )
            }
        }

        fun bind(item: WordUiModel) {
            itemView.tvTitle.text = item.text
            itemView.setOnClickListener {
                listener.selectWord(item)
            }
        }
    }
}
