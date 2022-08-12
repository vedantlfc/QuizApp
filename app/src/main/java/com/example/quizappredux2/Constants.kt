package com.example.quizappredux2

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question>{
        val questionsList = ArrayList<Question>()

        val que1 = Question(
            1,
            "What country was Gandhi from?",
            R.drawable.ic_bg,
            "India",
            "Nepal",
            "United Kingdom",
            "South Africa",
            1
        )

        val que2 = Question(
            2,
            "What country was MLK from?",
            R.drawable.ic_bg,
            "India",
            "UK",
            "United States",
            "Gabon",
            3
        )

        val que3 = Question(
            3,
            "What country was Mandela from?",
            R.drawable.ic_bg,
            "Pakistan",
            "Nepal",
            "Jamaica",
            "South Africa",
            4
        )



        questionsList.add(que1)
        questionsList.add(que2)
        questionsList.add(que3)

        return questionsList
    }
}