package com.example.wordsfactory.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.ActivityMainBinding
import com.example.wordsfactory.dictionary_logic.AppViewModel
import com.example.wordsfactory.dictionary_logic.Injection
import com.example.wordsfactory.main_fragments.IntroFragment
import com.example.wordsfactory.main_fragments.PlaceHolderFragment

class MainActivity : AppCompatActivity() {
    private lateinit var vm: AppViewModel

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //AppViewModel
        vm = ViewModelProvider(this, Injection.provideFactory(this))
            .get(AppViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.SplashTheme)//splash screen
        setContentView(binding.root)
        startMainFragment()
    }

    private fun startMainFragment() {
        /* if user is created -> PlaceHolderFragment else IntroFragment*/
        if (vm.checkForUser()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, IntroFragment.newInstance()).commit()
        } else
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, PlaceHolderFragment.newInstance()).commit()
    }
}