package com.example.wordsfactory.ui.introduction_screens.intro.adapter

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

    class SliderHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sliderItemBinding = SliderItemBinding.bind(view)
        val context: Context = view.context
        fun bind(item: SliderItem) = with(sliderItemBinding) {
            sliderMainText.text = context.getText(item.mainText)
            sliderSubText.text = context.getText(item.subText)
            sliderImage.setImageResource(item.image)
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