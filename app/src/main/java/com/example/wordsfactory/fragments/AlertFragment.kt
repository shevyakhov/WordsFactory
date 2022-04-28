package com.example.wordsfactory.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentAlertBinding


class AlertFragment : DialogFragment() {
    private lateinit var fragmentAlertBinding: FragmentAlertBinding
    private var mainText: String? = null
    private var subText: String? = null
    private var mainTextTag = getString(R.string.AlertFragmentMainText)
    private var subTextTag = getString(R.string.AlertFragmentSubText)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainText = arguments?.get(mainTextTag).toString()
        subText = arguments?.get(subTextTag).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAlertBinding = FragmentAlertBinding.inflate(inflater)
        return fragmentAlertBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* alert dialog with mainText and subText*/
        fragmentAlertBinding.mainTextAlert.text = mainText
        fragmentAlertBinding.subTextAlert.text = subText

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString(mainTextTag, param1)
                    putString(subTextTag, param2)
                }
            }
    }
}