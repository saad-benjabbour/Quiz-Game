package com.example.quizgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity : AppCompatActivity() {
    private var userName : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // retrieving the user name from LoginActivity
        userName = intent.getStringExtra(Constants.USER_NAME)
        // setting the click event on each category
        foodCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "FOOD")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }

        musicCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "MUSIC")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }
        historyCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "HISTORY")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }
        SportCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "SPORT")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }
        ScienceCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "SCIENCE")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }
        ArtCat.setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(Constants.CAT_NAME, "ART")
            intent.putExtra(Constants.USER_NAME, userName)
            startActivity(intent)
        }
    }
}