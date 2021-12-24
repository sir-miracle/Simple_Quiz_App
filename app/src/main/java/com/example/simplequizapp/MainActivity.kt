 package com.example.simplequizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = findViewById<EditText>(R.id.name_edit_text)
        val startBtn = findViewById<Button>(R.id.btn_start)
            startBtn.setOnClickListener {

                if (name.text.isNotBlank()){
                    val intent = Intent(this, QuizQuestionsActivity::class.java)
                    //use intent's "put extra" to pass data from one activity to another
                    intent.putExtra(Constants.USER_NAME, name.text.toString())
                    //now as you start the activity (QuizQuestionsActivity), you can retrieve the information you passed
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                }

            }
    }
}