package com.example.simplequizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var progressbar: ProgressBar
    lateinit var progressText: TextView
    lateinit var question: TextView
    lateinit var flagImage: ImageView
    lateinit var option1: TextView
    lateinit var option2: TextView
    lateinit var option3: TextView
    lateinit var option4: TextView
    lateinit var submitBtn: Button

    lateinit var mUserName: String //will use this to retrieve the username passed from main activity
                                    //with the intent put extra
    private var mCorrectAnswers: Int = 0
    private var mCurrentPosition: Int = 1
    lateinit var mQuestionsList: ArrayList<Question>
    private var mSelectedOptionPosition: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME).toString() //the username from the main activity

        progressbar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.tv_progress)
        question = findViewById(R.id.tv_question)
        flagImage = findViewById(R.id.iv_image_flag)
        option1 = findViewById(R.id.tv_option_one)
        option2 = findViewById(R.id.tv_option_two)
        option3 = findViewById(R.id.tv_option_three)
        option4 = findViewById(R.id.tv_option_four)
        submitBtn =findViewById(R.id.btn_submit)

        option1.setOnClickListener(this)
        option2.setOnClickListener(this)
        option3.setOnClickListener(this)
        option4.setOnClickListener(this)

        submitBtn.setOnClickListener(this)

         mQuestionsList = Constants.getQuestions() //populate your questions list (array)
                                                    // with the questions from the Question class

        setQuestion()//sets a single question from the questions list array
        defaultOptionsView()//sets the default view for the button and options

    }

    private fun setQuestion() {

        defaultOptionsView()

        var currentQuestion: Question = mQuestionsList[mCurrentPosition - 1]
        progressbar.progress = mCurrentPosition
        progressText.text = "$mCurrentPosition/${progressbar.max}"
        question.text = currentQuestion.question

        flagImage.setImageResource(currentQuestion.image)

        option1.text = currentQuestion.optionOne
        option2.text = currentQuestion.optionTwo
        option3.text = currentQuestion.optionThree
        option4.text = currentQuestion.optionFour


        if (mCurrentPosition == mQuestionsList.size){
             submitBtn.text ="FINISH"
        }else{
            submitBtn.text ="SUBMIT"
        }

    }

    //each time a new question is set, this function is called first to reset the views of the options
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        option1.let {
            options.add(0,it)
        }

        option2.let {
            options.add(1,it)
        }

        option3.let {
            options.add(2,it)
        }

        option4.let {
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD )
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.tv_option_one -> {
                option1.let { selectedOptionView(it,1) }
            }

            R.id.tv_option_two -> {
                option2.let { selectedOptionView(it,2) }
            }

            R.id.tv_option_three -> {
                option3.let { selectedOptionView(it,3) }
            }

            R.id.tv_option_four -> {
                option4.let { selectedOptionView(it,4) }
            }

            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionsList.size -> {
                            setQuestion()
                        }

                        else -> {
                            //start the result activity, passing data to it with the intent put extra
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList.size )
                            startActivity(intent)
                            finish()// finish makes sure you cant go back to previous activity when
                                    // you click on back button on the phone

                        }

                    }
                }else{
                    val question = mQuestionsList.get(mCurrentPosition-1)

                    if (question.correctAnswer != mSelectedOptionPosition){

                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }else{
                            mCorrectAnswers++

                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList.size){
                        submitBtn.text = "FINISH"
                    }else{
                        submitBtn.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0

                }
            }

        }
    }

    private fun answerView(answer: Int, drawableView: Int){
            when(answer){

                1 -> {
                    option1.background = ContextCompat.getDrawable(this, drawableView)
                }

                2 -> {
                    option2.background = ContextCompat.getDrawable(this, drawableView)
                }

                3 -> {
                    option3.background = ContextCompat.getDrawable(this, drawableView)
                }

                4 -> {
                    option4.background = ContextCompat.getDrawable(this, drawableView)
                }
            }

    }

}