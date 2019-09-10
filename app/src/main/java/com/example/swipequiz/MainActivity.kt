package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager and adapter
        rvQuestions.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        // Add lines between different questions
        rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        // Add questions to the view
        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()

        // Enable swiping
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    // Enable left and right swiping and add actions
    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            // Do nothing when onMove() is called
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // If user swiped right answer is true, else it is false
                var answer: Boolean = false
                if (direction == ItemTouchHelper.RIGHT) {
                    answer = true
                }

                submitAnswer(answer, questions[position])
                // Get question back into the view
                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

        return ItemTouchHelper(callback)
    }

    // Check if answer is correct or not and display snackbar
    private fun submitAnswer(answer: Boolean, question: Question) {
        if (answer == question.correctAnswer) {
            makeSnackBar(getString(R.string.correct) + answer)
        } else {
            makeSnackBar(getString(R.string.incorrect) + answer)
        }
    }

    // Creates snackbar with specified text
    private fun makeSnackBar(text: String) {
        Snackbar.make(findViewById(R.id.rvQuestions), text, Snackbar.LENGTH_SHORT).show()
    }
}
