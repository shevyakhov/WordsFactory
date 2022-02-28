package com.example.wordsfactory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordsfactory.adapters.SliderAdapter
import com.example.wordsfactory.adapters.introList
import com.example.wordsfactory.databinding.FragmentIntroBinding


class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding
    private val sliderAdapter = SliderAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroBinding.inflate(inflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding.viewPager.adapter = sliderAdapter
        sliderAdapter.initList(introList)
        val indicator = binding.indicator
        indicator.setViewPager(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                /*TODO indicator*/
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = IntroFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}