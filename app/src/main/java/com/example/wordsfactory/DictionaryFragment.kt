package com.example.wordsfactory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordsfactory.databinding.FragmentDictionaryBinding


class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DictionaryFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}