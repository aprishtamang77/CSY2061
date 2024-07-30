package com.example.notessqlite.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //call super class oncreate method
        setContentView(R.layout.activity_login) //set content view to the layout

        val usernameEditText: EditText = findViewById(R.id.usernameEditText) //find username edittext view
        val passwordEditText: EditText = findViewById(R.id.passwordEditText) //find password edittext view
        val loginButton: Button = findViewById(R.id.loginButton) //find the login button view

        loginButton.setOnClickListener { //onclick listener on login button
            val username = usernameEditText.text.toString() //get text form the username EditText then convert to string
            val password = passwordEditText.text.toString() //get text form password EditText then convert to string

            if (username == "admin" && password == "password") { //simple text for demo
                //successful login
                val intent = Intent(this, MainActivity::class.java) //create intent to start main activity
                intent.putExtra("USERNAME", username) //put username to intent as extra
                startActivity(intent) //start main activity
                finish() //finish login activity so the user cant return
            } else {
                //login failed
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show() //toast msg and indicated the login fail
            }
        }
    }
}