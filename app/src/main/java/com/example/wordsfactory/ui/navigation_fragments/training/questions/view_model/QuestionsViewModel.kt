package com.example.wordsfactory.ui.navigation_fragments.training.questions.view_model

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.dictionary_logic.database.WordEntity
import com.example.wordsfactory.ui.navigation_fragments.training.questions.QuestionsFragment

class QuestionsViewModel : ViewModel() {

    private lateinit var fullList: List<WordEntity>
    private lateinit var constList: List<WordEntity>
    private lateinit var currentQuestion: Question
    private var rightCount = 0
    private lateinit var questionList: List<Question>
    fun passList(fullList: List<WordEntity>) {
        this.fullList = fullList
        constList = this.fullList
    }

    fun getCurrentQuestion(number: Int): Question {
        return questionList[number - 1]
    }

    fun setCurrentQuestion(q: Question) {
        currentQuestion = q
    }

    fun createQuestions(questionSet: List<WordEntity>) {
        val list = ArrayList<Question>()
        for (i in questionSet.shuffled()) {
            val tempList = fullList.filter { it != i }
            val meaning = i.meanings.random().definition
            val answerList = ArrayList<String>()
            answerList.add(i.word)
            if (tempList.size >= 2) {
                for (j in tempList.shuffled().take(2)) {
                    answerList.add(j.word)
                }
            } else {
                answerList.add("reading")
                answerList.add("cooking")
            }
            list.add(Question(question = meaning, answers = answerList.shuffled(), truth = i.word))
        }
        questionList = list.shuffled()

    }

    fun makeQuestionSet(list: List<WordEntity>): List<WordEntity> {
        val set = ArrayList<WordEntity>()
        val notLearned =
            list.filter { it.learningRate == -1 || it.learningRate == 0 }.shuffled().take(10)
        val learned = list.filter { it.learningRate == 1 }.shuffled().take(10)
        if (notLearned.size == 10) {
            return notLearned
        } else {
            set.addAll(notLearned)
            for (i in 1..10 - set.size) {
                set.add(learned[i - 1])
            }
            return set
        }
    }

    internal fun createQuiz(list: List<WordEntity>, questionSet: List<WordEntity>) {
        passList(list)
        createQuestions(questionSet)
        QuestionsFragment.questionSetSize = questionSet.size
    }

    fun result(): List<WordEntity> {
        val result = ArrayList<WordEntity>()
        for (i in fullList) {
            val beforeWord = constList.find { it.word == i.word }
            if (i.word == beforeWord?.word || i.learningRate != beforeWord?.learningRate) {
                result.add(i)
            }
        }
        return result
    }
    // might return empty list, not quite sure, needs checking

    fun rightCount() = rightCount

    fun checkTheAnswer(clickedBtn: Int): Boolean {
        if (clickedBtn != -1) {
            if (currentQuestion.answers[clickedBtn - 1] == currentQuestion.truth) {
                fullList.find { it.word == currentQuestion.truth }?.learningRate = 1
                rightCount++
                return true
            }
        }
        fullList.find { it.word == currentQuestion.truth }?.learningRate = -1
        return false
    }
}