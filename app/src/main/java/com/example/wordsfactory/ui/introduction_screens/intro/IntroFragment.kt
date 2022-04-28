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
    private lateinit var fragmentIntroBinding: FragmentIntroBinding
    private val sliderAdapter = SliderAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentIntroBinding = FragmentIntroBinding.inflate(inflater)
        return fragmentIntroBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
    }

    private fun initBinding() {
        fragmentIntroBinding.viewPager.adapter = sliderAdapter
        sliderAdapter.initList(introList)

        val indicator = fragmentIntroBinding.indicator
        indicator.setViewPager(fragmentIntroBinding.viewPager)
        fragmentIntroBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == sliderAdapter.itemCount - 1) {
                    fragmentIntroBinding.botButton.text = getText(R.string.LetsStart)
                } else
                    fragmentIntroBinding.botButton.text = getText(R.string.next)
            }
        })
        fragmentIntroBinding.skipBtn.setOnClickListener {
            registrationFragment()
        }
        fragmentIntroBinding.botButton.setOnClickListener {
            if (fragmentIntroBinding.viewPager.currentItem == sliderAdapter.itemCount - 1) {
                registrationFragment()
            } else
                fragmentIntroBinding.viewPager.currentItem++
        }
    }

    private fun registrationFragment() {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, SignUpFragment.newInstance()).commit()
    }


    companion object {
        @JvmStatic
        fun newInstance() = IntroFragment()
    }

}