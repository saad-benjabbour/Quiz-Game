package com.example.quizgame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        backLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_register.setOnClickListener {
            performRegistration()
        }
    }

    // registration
    private fun performRegistration()
    {
        val userName = userNameInput.text.toString()
        val userPassword = userPasswordInput.text.toString()
        val userConfirmPassword = userConfirmPasswordInput.text.toString()

        if(userPassword.isEmpty() || userName.isEmpty() || userConfirmPassword.isEmpty())
        {
            Toast.makeText(this, "Please Fill all of the required information!", Toast.LENGTH_SHORT).show()
            return
        }else if(userPassword != userConfirmPassword)
        {
            Toast.makeText(this, "the password don't match", Toast.LENGTH_SHORT).show()
            return
        }
        // insert to the database
        try {
            val database = this.openOrCreateDatabase("Quiz", Context.MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS Player (player_id INTEGER PRIMARY KEY AUTOINCREMENT, player_name TEXT NOT NULL," +
                    "password TEXT)")
            val sqlString = "INSERT INTO Player (player_name, password) values(?, ?)"
            val statement = database.compileStatement(sqlString)
            // filling the formal parameters
            statement.bindString(1, userName)
            statement.bindString(2, userPassword)
            statement.execute()
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        }catch(e: Exception)
        {
            e.printStackTrace()
        }




    }
}