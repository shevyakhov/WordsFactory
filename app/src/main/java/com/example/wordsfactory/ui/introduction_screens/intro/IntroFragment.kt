package com.example.wordsfactory.ui.introduction_screens.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentIntroBinding
import com.example.wordsfactory.ui.introduction_screens.intro.adapter.SliderAdapter
import com.example.wordsfactory.ui.introduction_screens.intro.adapter.introList
import com.example.wordsfactory.ui.introduction_screens.sign_up.SignUpFragment


class IntroFragment : Fragment() {
    private val sliderAdapter = SliderAdapter()
    private var _binding: FragmentIntroBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return this.binding.root

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
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == sliderAdapter.itemCount - 1) {
                    binding.botButton.text = getText(R.string.LetsStart)
                } else
                    binding.botButton.text = getText(R.string.next)
            }
        })
        binding.skipBtn.setOnClickListener {
            registrationFragment()
        }
        binding.botButton.setOnClickListener {
            if (binding.viewPager.currentItem == sliderAdapter.itemCount - 1) {
                registrationFragment()
            } else
                binding.viewPager.currentItem++
        }
    }

    private fun registrationFragment() {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, SignUpFragment.newInstance()).commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance() = IntroFragment()
    }

}