package com.example.simplequizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName = findViewById<TextView>(R.id.tv_name)
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val btnFinish = findViewById<Button>(R.id.btn_finish)
        var totalQuestions = ""
        var correctAnswers = ""

        //using intent to recieve what was passed to this activity with intent
        tvName.text = intent.getStringExtra(Constants.USER_NAME)
        correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0).toString()
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0).toString()

       tvScore.text = "Your total score is: $correctAnswers/$totalQuestions"

        btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}