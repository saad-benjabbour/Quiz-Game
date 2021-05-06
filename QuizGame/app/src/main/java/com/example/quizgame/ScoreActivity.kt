package com.example.quizgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // retrieving the userName
        val userName = intent.getStringExtra(Constants.USER_NAME)

        tv_name.text = userName

        // setting up the score
        val score = intent.getStringExtra("userScore")
        val questionCount = intent.getStringExtra(Constants.QUESTION_COUNT)
        tv_score.text = "Your score is" + score + "out of" + questionCount

    }
}