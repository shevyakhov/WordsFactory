package com.example.wordsfactory.ui.navigation_fragments.training.questions.view_model

data class Question(
    val question: String,
    val answers: List<String>,
    val truth: String
)
