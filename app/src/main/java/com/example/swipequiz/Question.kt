package com.example.swipequiz

data class Question(

    var question: String,
    var correctAnswer: Boolean

) {
    companion object {
        val QUESTIONS = arrayOf(
            "Dit is een Android app",
            "1 + 1 = 3"
        )

        val ANSWERS = arrayOf(
            true,
            false
        )
    }
}