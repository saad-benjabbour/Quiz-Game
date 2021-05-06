package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.DatabaseUtils
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    var question: Cursor ?= null
    var correctAnswer: String = ""
    var index: Int ?= null
    var answer:String = ""
    var clickCount : Int = 0
    var userName : String? = null
    var userScore : Int = 0
    var questionCount : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)


        userName = intent.getStringExtra(Constants.USER_NAME)
        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)

        val information = intent.getStringExtra(Constants.CAT_NAME)
        // restoring the default view
        defaultOptionsView()

        try {
            val database = this.openOrCreateDatabase("Quiz", Context.MODE_PRIVATE, null)

            question = database.rawQuery("SELECT * FROM Question WHERE category_name = '"+information+"'", null)
            // Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(question))
            questionCount = question?.count!!
            question?.moveToFirst() // 1

            setQuestion()

        }catch(e:Exception)
        {
            Log.e("error in the database", "-------Database--------");
        }

    }

    private fun setQuestion()
    {
        tv_questions.text = question!!.getString(question!!.getColumnIndex("question_name"))
        tv_option_one.text = question!!.getString(question!!.getColumnIndex("option_1"))
        tv_option_two.text = question!!.getString(question!!.getColumnIndex("option_2"))
        tv_option_three.text = question!!.getString(question!!.getColumnIndex("option_3"))
        tv_option_four.text = question!!.getString(question!!.getColumnIndex("option_4"))
        correctAnswer = question!!.getString(question!!.getColumnIndex("correctAnswer"))
    }
        override fun onClick(v: View?)
        {

        when(v?.id)
        {
            R.id.tv_option_one ->
            {
                selectedOptionView(tv_option_one)
                answer = tv_option_one.text.toString()
                index = 1
            }
            R.id.tv_option_two ->
            {
                selectedOptionView(tv_option_two)
                answer = tv_option_two.text.toString()
                index = 2
            }
            R.id.tv_option_three ->
            {
                selectedOptionView(tv_option_three)
                answer = tv_option_three.text.toString()
                index = 3
            }
            R.id.tv_option_four ->
            {
                selectedOptionView(tv_option_four)
                answer = tv_option_four.text.toString()
                index = 4
            }
            R.id.btn_submit ->
            {
                if(clickCount == 0)
                {
                    // the first click...
                    if(answer != correctAnswer)
                    {
                        answerView(R.drawable.wrong_option_color_bg, answer)
                    }
                    else
                        userScore++ // point for user
                    answerView(R.drawable.correct_option_color_bg, correctAnswer)
                    if(question == null)
                        btn_submit.text = "FINISH"
                    else
                        btn_submit.text = "go to the next question"
                    clickCount++ // 1
                }else if(clickCount == 1)
                {
                    // the second click
                    defaultOptionsView()
                    when
                    {
                        question != null && question!!.moveToNext() ->
                        {
                            setQuestion()
                        }else ->
                        {
                            val intent = Intent(this, ScoreActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra("userScore", userScore.toString())
                            intent.putExtra(Constants.QUESTION_COUNT, questionCount.toString())
                            startActivity(intent)
                        }
                    }
                    clickCount = 0
                }

            }

        }
    }
    private fun answerView(drawableView : Int, answer : String)
    {
        when(answer)
        {
            "OPTION A" ->
                {
                    tv_option_one.background = ContextCompat.getDrawable(
                        this,
                        drawableView
                    )
            }
            "OPTION B" ->
            {
                tv_option_two.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            "OPTION C" ->
            {
                tv_option_three.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            "OPTION D" ->
            {
                tv_option_four.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
    private fun defaultOptionsView()
    {
        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)
        for(option in options)
        {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
        btn_submit.text = "Submit"

    }
    private fun selectedOptionView(tv: TextView)
    {
        defaultOptionsView()
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selector_option_border_bg
        )
    }
}
