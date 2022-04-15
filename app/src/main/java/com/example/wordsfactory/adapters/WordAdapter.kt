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

    class SliderHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = WordItemBinding.bind(v)
        val context: Context = v.context
        fun bind(it: WordItem) = with(binding) {
            explaining.text = it.definition
            if (it.example != null && it.example != "null") {
                exampleText.text = it.example
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