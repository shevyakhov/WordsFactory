package com.example.wordsfactory.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.WordItemBinding

/*adapter for retrieving word*/
class WordAdapter : RecyclerView.Adapter<WordAdapter.SliderHolder>() {
    private var sliderList = ArrayList<WordItem>()

    class SliderHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val wordItemBinding = WordItemBinding.bind(view)
        val context: Context = view.context
        fun bind(item: WordItem) = with(wordItemBinding) {
            explaining.text = item.definition
            if (item.example != null && item.example != "null") {
                exampleText.text = item.example
            } else
                exampleText.text = context.getString(R.string.noExample)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(sliderList[position])
    }

    override fun getItemCount(): Int {
        return sliderList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun initList(list: List<WordItem>) {

        sliderList = list as ArrayList<WordItem>
        notifyDataSetChanged()
    }
}