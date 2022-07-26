package com.example.wordsfactory.ui.navigation_fragments.training.questions

import androidx.lifecycle.ViewModel
import com.example.wordsfactory.dictionary_logic.database.WordEntity

class QuestionsViewModel : ViewModel() {

    private lateinit var fullList: List<WordEntity>
    private lateinit var currentWord: WordEntity
    private lateinit var currentQuestion: Question
    private lateinit var questionList: List<Question>
    fun passList(fullList: List<WordEntity>) {
        this.fullList = fullList
    }

    fun setCurrentWord(word: WordEntity) {
        currentWord = word
    }

    fun getCurrentWord(): WordEntity {
        return currentWord
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
            for (j in tempList.shuffled().take(2)) {
                answerList.add(j.word)
            }
            list.add(Question(question = meaning, answers = answerList.shuffled(), truth = i.word))
        }
        questionList = list

    }

    fun fullList() = fullList

    fun checkTheAnswer(clickedBtn: Int): Boolean {
        if (clickedBtn != -1) {
            if (currentQuestion.answers[clickedBtn - 1] == currentQuestion.truth) {
                fullList.find { it.word == currentQuestion.truth }?.learningRate = 1
                return true
            }
        }
        fullList.find { it.word == currentQuestion.truth }?.learningRate = -1
        return false
    }
}