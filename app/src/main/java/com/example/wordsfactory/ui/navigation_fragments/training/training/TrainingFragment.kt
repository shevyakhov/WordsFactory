package com.example.wordsfactory.ui.navigation_fragments.training.training

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
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.basicDuration
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.equalPartDivider
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.numberRange
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.ofIntFrom
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.ofIntTo
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.smallDuration
import com.example.wordsfactory.ui.navigation_fragments.training.training.TrainingFragment.ConstHelper.startColoringStringIndex
import com.example.wordsfactory.ui.navigation_fragments.training.questions.QuestionsFragment


class TrainingFragment : Fragment() {
    private var wordListSize: Int? = null
    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!
    private var animatorSet = AnimatorSet()
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
            startColoringStringIndex,
            startColoringStringIndex + (wordListSize?.toString()?.length ?: 0)
        )

        binding.startBtn.setOnClickListener {
            if (wordListSize != 0 && wordListSize != null) {
                timerAnimation(trainingViewModel.pairList(), wordList)

            } else
                Toast.makeText(context, "add more words to start training", Toast.LENGTH_SHORT)
                    .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancelAnimatorSet()
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
        if (!animatorSet.isRunning) {
            setRunningState()
            val animatorColor = trainingViewModel.colorValueAnimator().apply {
                duration = basicDuration
                addUpdateListener {
                    val color = it.animatedValue as Int
                    binding.progressBar.setIndicatorColor(color)
                    binding.countDown.setTextColor(color)
                }
            }

            val animatorProgress = ValueAnimator.ofInt(ofIntFrom, ofIntTo).apply {
                duration = basicDuration
                addUpdateListener {
                    binding.progressBar.progress = it.animatedValue as Int
                    val ind = it.animatedValue as Int / equalPartDivider
                    if (ind in numberRange) {
                        binding.countDown.text = list[ind].text
                    }
                }
            }

            val animatorDelayer = ValueAnimator.ofInt(ofIntFrom, ofIntTo).apply {
                duration = smallDuration
            }

            animatorSet = AnimatorSet().apply {
                play(animatorColor).with(animatorProgress).before(animatorDelayer)
                start()
            }

            animatorSet.doOnEnd {
                navigateToQuestionsFragment(wordList)
            }
        } else {
            cancelAnimatorSet()
            setReadyState()
        }
    }


    private fun navigateToQuestionsFragment(wordList: List<WordEntity>?) {
        findNavController().navigate(
            R.id.action_trainingFragment_to_questionsFragment,
            bundleOf(QuestionsFragment.WORDS to wordList)
        )
    }

    private fun setRunningState() {
        binding.timer.visibility = View.VISIBLE
        binding.startBtn.text = getText(R.string.cancel)
    }

    private fun setReadyState() {
        binding.apply {
            timer.visibility = View.INVISIBLE
            startBtn.text = getText(R.string.start)
        }
    }

    private fun cancelAnimatorSet() {
        animatorSet.apply {
            binding.progressBar.progress = 0
            removeAllListeners()
            cancel()
        }
    }

    data class AnimationPair(val color: String, val text: String)

    private object ConstHelper {
        val numberRange = 0..5
        const val startColoringStringIndex = 10
        const val equalPartDivider = 20
        const val ofIntFrom = 0
        const val ofIntTo = 100
        const val smallDuration: Long = 500
        const val basicDuration: Long = 6000
    }
}