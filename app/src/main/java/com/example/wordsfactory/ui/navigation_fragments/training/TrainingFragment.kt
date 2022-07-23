package com.example.wordsfactory.ui.navigation_fragments.training

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentTrainingBinding
import com.example.wordsfactory.dictionary_logic.repository.Injection
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.AppViewModel
import com.example.wordsfactory.ui.navigation_fragments.training.questions.QuestionsFragment


class TrainingFragment : Fragment() {
    private var wordListSize: Int? = null
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!
    private lateinit var appViewModel: AppViewModel
    private lateinit var trainingViewModel: TrainingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appViewModel = ViewModelProvider(this, Injection.provideFactory(requireContext()))
            .get(AppViewModel::class.java)
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        val wordList = appViewModel.getAllWordsFromDB()
        wordListSize = wordList?.size


        binding.howManyWordsText.text = wordListSize?.let { trainingViewModel.getText(it) }

        binding.howManyWordsText.setColouredSpan(
            R.color.primaryOrange,
            10,
            10 + (wordListSize?.toString()?.length ?: 0)
        )


        binding.startBtn.setOnClickListener {
            // (activity as MainActivity).trainingFragment(this)
            findNavController().navigate(
                R.id.action_trainingFragment_to_questionsFragment,
                bundleOf(QuestionsFragment.WORDS to wordList)
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun TextView.setColouredSpan(color: Int, starts: Int, ends: Int) {
        val spannableString = SpannableString(text)
        try {
            spannableString.setSpan(
                ForegroundColorSpan(Color.rgb(227, 86, 42)),
                starts,
                ends,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = spannableString
        } catch (e: IndexOutOfBoundsException) {
            println("error")
        }
    }
}