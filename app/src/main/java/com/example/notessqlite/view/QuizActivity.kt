package com.example.notessqlite.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.R

//define QuizActivity class that extends AppCompatActivity
class QuizActivity : AppCompatActivity() {

    //declare lateinit properties for the UI elements
    private lateinit var questionTextView: TextView
    private lateinit var answerButton1: Button
    private lateinit var answerButton2: Button
    private lateinit var answerButton3: Button
    private lateinit var answerButton4: Button
    private lateinit var scoreTextView: TextView

    // List of questions for the quiz
    private val questions = listOf(
        //create question objects with text, answers, and correct answer index
        Question("What is the capital of Nepal?", listOf("Bhaktapur", "Kathmandu", "Pokhara", "Chitwan"), 1),
        Question("Which is the national bird of Nepal?", listOf("Parrot", "Danphe", "Peacock", "Eagle"), 1),
        Question("Which is the smallest district of Nepal?", listOf("Kathmandu", "Pokhara", "Bhaktapur", "Dharan"), 2)
    )

    //init current question index and score
    private var currentQuestionIndex = 0
    private var score = 0

    //override onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to activity_quiz layout
        setContentView(R.layout.activity_quiz)

        //bind ui elements to their corresponding views
        questionTextView = findViewById(R.id.questionTextView)
        answerButton1 = findViewById(R.id.answerButton1)
        answerButton2 = findViewById(R.id.answerButton2)
        answerButton3 = findViewById(R.id.answerButton3)
        answerButton4 = findViewById(R.id.answerButton4)
        scoreTextView = findViewById(R.id.scoreTextView)

        //set click listeners for the answer buttons
        answerButton1.setOnClickListener { checkAnswer(0) }
        answerButton2.setOnClickListener { checkAnswer(1) }
        answerButton3.setOnClickListener { checkAnswer(2) }
        answerButton4.setOnClickListener { checkAnswer(3) }

        //load first question
        loadQuestion()
    }

    //method to load a question
    private fun loadQuestion() {
        //check if there are more questions
        if (currentQuestionIndex < questions.size) {
            //get current question
            val currentQuestion = questions[currentQuestionIndex]
            //set question text and answer button texts
            questionTextView.text = currentQuestion.text
            answerButton1.text = currentQuestion.answers[0]
            answerButton2.text = currentQuestion.answers[1]
            answerButton3.text = currentQuestion.answers[2]
            answerButton4.text = currentQuestion.answers[3]
        } else {
            //id no more questions show the final score
            questionTextView.text = "Quiz finished! Your final score is $score"
            scoreTextView.text = "Final Score: $score"
            // Disable the answer buttons
            disableAnswerButtons()
        }
    }

    //method to disable answer buttons
    private fun disableAnswerButtons() {
        answerButton1.isEnabled = false
        answerButton2.isEnabled = false
        answerButton3.isEnabled = false
        answerButton4.isEnabled = false
    }

    //methodto check the selected answer
    private fun checkAnswer(answerIndex: Int) {
        //getcorrect answer index for the current question
        val correctAnswerIndex = questions[currentQuestionIndex].correctAnswerIndex
        // Check if the selected answer is correct and update the score
        if (answerIndex == correctAnswerIndex) {
            score++
        }
        //move to the next question
        currentQuestionIndex++

        //checkif there are more questions
        if (currentQuestionIndex < questions.size) {
            // Update the score display and load the next question
            scoreTextView.text = "Score: $score"
            loadQuestion()
        } else {
            //if no more questions, show the final score and disable the answer buttons
            questionTextView.text = "Quiz finished! Your final score is $score"
            scoreTextView.text = "Final Score: $score"
            disableAnswerButtons()
        }
    }

    //data class to represent a quiz question
    data class Question(val text: String, val answers: List<String>, val correctAnswerIndex: Int)
}
