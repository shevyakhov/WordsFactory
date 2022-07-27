package com.example.wordsfactory.ui.navigation_fragments.training.questions

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentQuestionsBinding
import com.example.wordsfactory.dictionary_logic.database.WordEntity

@Suppress("UNCHECKED_CAST")
class QuestionsFragment : Fragment() {
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private var animatorSet = AnimatorSet()
    private lateinit var questionsViewModel: QuestionsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsViewModel = ViewModelProvider(this)[QuestionsViewModel::class.java]

        binding.backBtn.setOnClickListener {
            animatorSet.removeAllListeners()
            animatorSet.end()
            animatorSet.cancel()
            findNavController().navigate(R.id.action_questionsFragment_to_trainingFragment)
        }
        val fullList: List<WordEntity> = requireArguments().get(WORDS) as List<WordEntity>
        val questionSet = fullList.sortedBy { it.learningRate }.shuffled().take(10)

        createQuiz(fullList, questionSet)
        startQuestion(questionNumber)

        binding.firstAnswer.setOnClickListener {
            performAnswer(FIRST_BUTTON_ID)
        }
        binding.secondAnswer.setOnClickListener {
            performAnswer(SECOND_BUTTON_ID)
        }
        binding.thirdAnswer.setOnClickListener {
            performAnswer(THIRD_BUTTON_ID)
        }
    }

    private fun createQuiz(fullList: List<WordEntity>, questionSet: List<WordEntity>) {
        questionsViewModel.apply {
            passList(fullList)
            createQuestions(questionSet)
            questionSetSize = questionSet.size
        }
    }

    private fun startQuestion(number: Int) {
        if (number <= questionSetSize) {
            val colorAnimator = ValueAnimator.ofInt(
                0, 100
            ).apply {
                addUpdateListener {
                    binding.progressBar.progress = it.animatedValue as Int
                }
                duration = 5000
            }

            animatorSet.apply {
                val question = questionsViewModel.getCurrentQuestion(number)
                questionsViewModel.setCurrentQuestion(question)
                setViewsByQuestion(number, questionSetSize, question)
                play(colorAnimator)
                start()
            }.doOnEnd {
                if (number + 1 <= questionSetSize) {
                    questionsViewModel.checkTheAnswer(NO_ANSWER_ID)
                    startQuestion(number + 1)
                } else
                    startResultFragment()
            }
        } else
            startResultFragment()
    }

    private fun setViewsByQuestion(questionNumber: Int, maximum: Int, question: Question) {
        with(binding) {
            counter.text = "${questionNumber}/${maximum}"
            questionText.text = question.question
            firstAnswer.text = question.answers[0]
            secondAnswer.text = question.answers[1]
            thirdAnswer.text = question.answers[2]
        }
    }

    private fun performAnswer(btnNumber: Int) {
        questionsViewModel.checkTheAnswer(btnNumber)
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()
        questionNumber++
        startQuestion(questionNumber)
    }

    private fun startResultFragment() {
        findNavController().navigate(
            R.id.action_questionsFragment_to_resultFragment,
            bundleOf(
                ResultFragment.CHANGED_LIST to questionsViewModel.result(),
                ResultFragment.RESULT to (questionsViewModel.rightCount() to questionSetSize)
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        questionNumber = 1
        questionSetSize = 0
        _binding = null
    }

    companion object {
        private var FIRST_BUTTON_ID = 1
        private var NO_ANSWER_ID = -1
        private var SECOND_BUTTON_ID = 2
        private var THIRD_BUTTON_ID = 3
        private var questionNumber = 1
        private var questionSetSize = 0
        var WORDS: String = "words"
    }
}