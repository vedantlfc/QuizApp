package com.example.quizappredux2.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.quizappredux2.Constants
import com.example.quizappredux2.R
import com.example.quizappredux2.firebase.FirestoreClass
import com.example.quizappredux2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : BaseActivity() {
    private var toolBarSignUpActivity : androidx.appcompat.widget.Toolbar? = null
    private lateinit var auth : FirebaseAuth
    private var btnSignIn : Button? = null
    private var etEmail : TextView? = null
    private var etPassword : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        toolBarSignUpActivity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_sign_in_activity)
        auth = Firebase.auth
        btnSignIn = findViewById(R.id.btn_sign_in)
        etEmail = findViewById(R.id.et_email_sign_in)
        etPassword = findViewById(R.id.et_password_sign_in)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()
        btnSignIn?.setOnClickListener {
            signInUser()
        }
    }

    private fun signInUser() {
        val email : String = etEmail?.text.toString().trim{it <= ' '}
        val password : String = etPassword?.text.toString().trim{it <= ' '}
        if(validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        FirestoreClass().signInUser(this)
                    } else {
                        showToast("Auth failed")
                    }
                }
        }

    }

    fun signInSuccess(user: User){
        hideProgressDialog()
        val name = user.name
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Constants.USER_NAME, name)
        startActivity(intent)
        showToast("Signed In!")
        finish()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolBarSignUpActivity)

        val actionBar = supportActionBar
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_color_back_24dp)
        }

        toolBarSignUpActivity?.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when{
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please Enter an Email Id")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please Enter a Password")
                false
            } else -> {
                return true
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!= null){

        }
    }


}