package com.example.gitproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gitproject.fragment.AccountFragment
import com.example.gitproject.fragment.ChatFragment
import com.example.gitproject.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShowUserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showuser)



        /*
        findViewById<Button>(R.id.logoutButton).setOnClickListener { //로그아웃
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
         */
        val homeFragment= HomeFragment()
        val chatFragment= ChatFragment()
        val accountFragment= AccountFragment()

        changeFragment(homeFragment)

        val bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> changeFragment(homeFragment)
                R.id.chat -> changeFragment(chatFragment)
                R.id.myPage -> changeFragment(accountFragment)

            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .apply{
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }


}