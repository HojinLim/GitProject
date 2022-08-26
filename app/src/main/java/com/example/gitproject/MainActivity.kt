package com.example.gitproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val auth: FirebaseAuth= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.moveToLoginButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        if(auth.currentUser != null) {
            startActivity(Intent(this, ShowUserActivity::class.java))
        }
    }
}
