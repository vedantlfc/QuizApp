package com.example.quizappredux2.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizappredux2.Constants
import com.example.quizappredux2.R
import com.example.quizappredux2.firebase.FirestoreClass


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appTitle = findViewById<TextView>(R.id.appTitle)
        val appGreeting = findViewById<TextView>(R.id.appGreeting)
        val btnTopic1 : Button = findViewById(R.id.btnTopic1)
        val etName : EditText = findViewById(R.id.et_name)

        var text = intent.getStringExtra(Constants.USER_NAME)
        showToast(text.toString())
        appGreeting.text = "Welcome $text"

        btnTopic1.setOnClickListener{
            if(etName.text.isEmpty()){
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()
            }

        }
    }
}