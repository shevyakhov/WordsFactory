package com.example.wordsfactory.ui.navigation_fragments.training

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentTrainingBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity
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
            10,
            10 + (wordListSize?.toString()?.length ?: 0)
        )

        binding.startBtn.setOnClickListener {
            if (wordListSize != 0) {
                val pairList = listOf(
                    AnimationPair(getString(R.string.orange), getString(R.string._5)),
                    AnimationPair(getString(R.string.blue), getString(R.string._4)),
                    AnimationPair(getString(R.string.green), getString(R.string._3)),
                    AnimationPair(getString(R.string.yellow), getString(R.string._2)),
                    AnimationPair(getString(R.string.red), getString(R.string._1)),
                    AnimationPair(getString(R.string.orange), getString(R.string._GO)),
                )
                timerAnimation(pairList, wordList)

            } else
                Toast.makeText(context, "add more words to start training", Toast.LENGTH_SHORT)
                    .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun TextView.setColouredSpan(starts: Int, ends: Int) {
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

    private fun timerAnimation(list: List<AnimationPair>, wordList: List<WordEntity>?) {
        binding.timer.visibility = View.VISIBLE

        val animator = ValueAnimator.ofArgb(
            requireContext().getColor(R.color.primaryOrange),
            requireContext().getColor(R.color.animBlue),
            requireContext().getColor(R.color.animYellow),
            requireContext().getColor(R.color.animGreen),
            requireContext().getColor(R.color.animRed),
            requireContext().getColor(R.color.primaryOrange)
        ).apply {
            duration = 6000
            addUpdateListener {
                val color = it.animatedValue as Int
                binding.progressBar.setIndicatorColor(color)
                binding.countDown.setTextColor(color)
            }
        }

        val animatorProgress = ValueAnimator.ofInt(0, 100).apply {
            duration = 6000
            addUpdateListener {
                binding.progressBar.progress = it.animatedValue as Int
                val ind = it.animatedValue as Int / 20
                if (ind in 0..5) {
                    binding.countDown.text = list[ind].text
                }
            }
        }

        val animatorDelayer = ValueAnimator.ofInt(0, 100).apply {
            duration = 500
        }

        val animatorSet = AnimatorSet().apply {
            play(animator).with(animatorProgress).before(animatorDelayer)
            start()
        }

        animatorSet.doOnEnd {
            findNavController().navigate(
                R.id.action_trainingFragment_to_questionsFragment,
                bundleOf(QuestionsFragment.WORDS to wordList)
            )
        }
    }

    // TODO: cancel animation on cancel
    data class AnimationPair(val color: String, val text: String)
}