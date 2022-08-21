package com.example.quizappredux2.activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.quizappredux2.R
import com.example.quizappredux2.firebase.FirestoreClass
import com.example.quizappredux2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    private var toolBarSignUpActivity : androidx.appcompat.widget.Toolbar? = null
    private var etName : EditText? = null
    private var etEmail : EditText? = null
    private var etPassword : EditText? = null
    private var btnSignUp : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        toolBarSignUpActivity = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_sign_up_activity)
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnSignUp = findViewById(R.id.btn_sign_up)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()

        btnSignUp?.setOnClickListener {
            registerUser()
        }
    }

    fun userRegisteredSuccess(){
        showToast("Successfully Registered")
        hideProgressDialog()
        FirebaseAuth.getInstance().signOut()
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

    private fun registerUser(){
        val name: String = etName?.text.toString().trim{ it <= ' '}
        val email: String = etEmail?.text.toString().trim{ it <= ' '}
        val password: String = etPassword?.text.toString().trim{ it <= ' '}

        if(validateForm(name, email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!

                    val user = User(firebaseUser.uid, name, registeredEmail)
                    FirestoreClass().registerUser(this, user)
                } else {
                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please Enter a Name")
                false
            }
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
}