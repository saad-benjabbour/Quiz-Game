package com.example.quizgame

import android.content.Context
import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.userNameInput
import kotlinx.android.synthetic.main.activity_login.userPasswordInput
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // make the app use the full screen of the phone
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // registration Link
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
            performLogin()
        }
    }

    // login function
    private fun performLogin()
    {
        val userName = userNameInput.text.toString()
        val userPassword = userPasswordInput.text.toString()

        if(userPassword.isEmpty() || userName.isEmpty())
        {
            Toast.makeText(this, "Please Fill all of the required information!", Toast.LENGTH_SHORT)
                .show()
            return
        }
        try {
            val database = this.openOrCreateDatabase("Quiz", Context.MODE_PRIVATE, null)
            val userInfo = database.rawQuery("SELECT * FROM Player WHERE player_name = '"+userName+"'" + " AND password = "+ "'"+userPassword+"'", null)
            DatabaseUtils.dumpCursorToString(userInfo)
            while(userInfo.moveToNext())
            {
                println("UserName"+userInfo.getString(1))
                println("Userpsswd"+userInfo.getString(2))
            }

            userInfo.moveToFirst()
            if(userInfo.count > 0)
            {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CategoryActivity::class.java)
                intent.putExtra(Constants.USER_NAME, userName)
                startActivity(intent)
            }else
            {
                Toast.makeText(this, "UserName/password are incorrect", Toast.LENGTH_SHORT).show()
            }
            userInfo?.close()

        }catch(e: Exception)
        {
            e.printStackTrace()
        }
    }

}