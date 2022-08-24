package com.example.gitproject

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= Firebase.auth

        initLoginButton()
        initSignUpButton()
        initEmailAndPasswordEditText()
    }
    private fun initLoginButton(){
        val loginButton= findViewById<AppCompatButton>(R.id.loginButton)
        loginButton.setOnClickListener{
            val email= getInputEmail()
            val password= getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        //finish()
                        startActivity(Intent(this, ShowUserActivity::class.java))
                    }else{
                        Toast.makeText(this, "잘못 입력하였거나 계정이 존재하지 않습니다!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun initSignUpButton(){
        val signUpButton= findViewById<AppCompatButton>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            val email= getInputEmail()
            val password= getInputPassword()
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        Toast.makeText(this, "회원가입에 성공하였습니다! 로그인 버튼을 눌러 로그인해주세요.", Toast.LENGTH_SHORT).show()
                       // finish()
                    }else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다!", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun getInputEmail(): String{
        return findViewById<EditText>(R.id.emailEditTextView).text.toString()
    }
    private fun getInputPassword(): String{
        return findViewById<EditText>(R.id.passwordEditTextView).text.toString()
    }
    private fun initEmailAndPasswordEditText() {
        val emailEditText= findViewById<EditText>(R.id.emailEditTextView)
        val passwordEditText= findViewById<EditText>(R.id.passwordEditTextView)
        val signUpButton= findViewById<AppCompatButton>(R.id.signUpButton)
        val loginButton= findViewById<AppCompatButton>(R.id.loginButton)

        emailEditText.addTextChangedListener {
            val enable= emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled= enable
            signUpButton.isEnabled= enable
        }
        passwordEditText.addTextChangedListener {
            val enable= emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
            loginButton.isEnabled= enable
            signUpButton.isEnabled= enable
        }
    }
}