package com.example.wordsfactory.ui.alert_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.wordsfactory.databinding.FragmentAlertBinding

private const val AlertFragmentMainText = "first"
private const val AlertFragmentSubText = "second"

class AlertFragment : DialogFragment() {
    private var _binding: FragmentAlertBinding? = null
    private val binding get() = _binding!!

    private var mainText: String? = null
    private var subText: String? = null
    private var mainTextTag = AlertFragmentMainText
    private var subTextTag = AlertFragmentSubText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainText = arguments?.get(mainTextTag).toString()
        subText = arguments?.get(subTextTag).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* alert dialog with mainText and subText*/
        binding.mainTextAlert.text = mainText
        binding.subTextAlert.text = subText

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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