package com.example.quizappredux2

data class Question(
    val id: Int,
    val questionString: String,
    val Image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
