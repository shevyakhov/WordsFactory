package com.example.wordsfactory.ui.navigation_fragments.training.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentResultBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.ui.navigation_fragments.training.TrainingFragment

@Suppress("UNCHECKED_CAST")
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
        val changedList: List<WordEntity> =
            requireArguments().get(CHANGED_LIST) as List<WordEntity>
        val result = requireArguments().get(RESULT) as Pair<Int, Int>

        binding.correctNumberText.text = getString(R.string.correct) + result.first
        binding.incorrectNumberText.text =
            getString(R.string.incorrect) + (result.second - result.first)
        binding.againButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_trainingFragment).apply {
                bundleOf(TrainingFragment.CHANGED_LIST to changedList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var CHANGED_LIST: String = "CHANGED_LIST"
        var RESULT: String = "result"
    }

}