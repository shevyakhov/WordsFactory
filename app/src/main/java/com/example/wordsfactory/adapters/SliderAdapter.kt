package com.example.wordsfactory.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.SliderItemBinding

/* Intro fragment adapter for viewPager*/
class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderHolder>() {
    private var sliderList = listOf<SliderItem>()

    class SliderHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = SliderItemBinding.bind(v)
        val context: Context = v.context
        fun bind(it: SliderItem) = with(binding) {
            sliderMainText.text = context.getText(it.mainText)
            sliderSubText.text = context.getText(it.subText)
            sliderImage.setImageResource(it.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(sliderList[position])
    }

    override fun getItemCount(): Int {
        return sliderList.size
    }

    fun initList(list: List<SliderItem>) {
        sliderList = list
    }
}