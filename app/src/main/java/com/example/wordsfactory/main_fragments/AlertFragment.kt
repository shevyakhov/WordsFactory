package com.example.wordsfactory.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wordsfactory.databinding.FragmentAlertBinding


class AlertFragment : DialogFragment() {
    private lateinit var binding: FragmentAlertBinding
    private var mainText: String? = null
    private var subText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainText = arguments?.get("first").toString()
        subText = arguments?.get("second").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* alert dialog with mainText and subText*/
        binding.mainTextAlert.text = mainText
        binding.subTextAlert.text = subText

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString("first", param1)
                    putString("second", param2)
                }
            }
    }
}