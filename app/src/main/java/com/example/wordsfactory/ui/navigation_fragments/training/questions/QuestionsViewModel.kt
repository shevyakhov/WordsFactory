package com.example.wordsfactory.ui.navigation_fragments.training.questions

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.dictionary_logic.database.WordEntity

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
        for (i in questionSet) {
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
        questionList = list

    }

    fun result() =
        fullList.filter { it.learningRate != constList.find { constWord -> constWord.word == it.word }?.learningRate }
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