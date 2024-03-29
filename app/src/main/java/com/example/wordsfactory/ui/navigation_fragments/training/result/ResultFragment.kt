package com.example.wordsfactory.ui.navigation_fragments.training.result

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentResultBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.dictionary_logic.repository.Injection


@Suppress("UNCHECKED_CAST")
class ResultFragment : Fragment() {
    private lateinit var resultViewModel: ResultViewModel
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultViewModel = ViewModelProvider(
            this,
            Injection.provideFactoryResult(requireContext())
        )[ResultViewModel::class.java]

        val changedList: List<WordEntity> = requireArguments().get(CHANGED_LIST) as List<WordEntity>
        val result = requireArguments().get(RESULT) as Pair<Int, Int>

        resultViewModel.updateDb(changedList)
        val stats = resultViewModel.getStatistics()
        showTrainingResult(result)
        saveData(stats)

        resultViewModel.updateWidget(requireContext())
        resultViewModel.setNotification(requireContext())

        binding.againButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_trainingFragment)
        }

    }


    @SuppressLint("SetTextI18n")
    private fun showTrainingResult(result: Pair<Int, Int>) {
        binding.correctNumberText.text = getString(R.string.correct) + result.first
        binding.incorrectNumberText.text =
            getString(R.string.incorrect) + (result.second - result.first)
    }


    private fun saveData(stats: Pair<Int, Int>) {
        val learned = stats.first
        val all = stats.second
        val sharedPreferences = this.activity?.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(SHARED_PREFS_LEARNED, learned)
        editor?.putInt(SHARED_PREFS_ALL, all)
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SHARED_PREFS = "SHARED_PREFS"
        private const val SHARED_PREFS_LEARNED = "SHARED_PREFS_LEARNED"
        private const val SHARED_PREFS_ALL = "SHARED_PREFS_ALL"
        var CHANGED_LIST: String = "CHANGED_LIST"
        var RESULT: String = "result"
    }

}