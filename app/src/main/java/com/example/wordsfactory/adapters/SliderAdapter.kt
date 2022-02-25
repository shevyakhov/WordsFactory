package com.example.wordsfactory.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.SliderItemBinding

class SliderAdapter : RecyclerView.Adapter<SliderAdapter.SliderHolder>() {
    private var imageList = listOf<String>()

    class SliderHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val binding = SliderItemBinding.bind(v)
        val context = v.context
        fun bind(it: String) = with(binding) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderHolder(view)
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }



}