package com.example.wordsfactory.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.ActivityMainBinding
import com.example.wordsfactory.fragment.IntroFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.SplashTheme)
        setContentView(binding.root)
        startMainFragment()
    }

    private fun startMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentHolder, IntroFragment.newInstance()).commit()
    }
}