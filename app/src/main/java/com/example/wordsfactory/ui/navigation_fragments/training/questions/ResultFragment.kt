package com.example.wordsfactory.ui.navigation_fragments.training.questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wordsfactory.databinding.FragmentResultBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fullList: List<WordEntity> =
            requireArguments().get(CHANGED_LIST) as List<WordEntity>
        val result = requireArguments().get(RESULT) as Pair<Int, Int>

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var CHANGED_LIST: String = "words"
        var RESULT: String = "result"
    }

}