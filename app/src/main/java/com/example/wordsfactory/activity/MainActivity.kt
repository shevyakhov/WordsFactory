package com.example.wordsfactory.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.ActivityMainBinding
import com.example.wordsfactory.dictionary_logic.repository.app_viewmodel.AppViewModel
import com.example.wordsfactory.dictionary_logic.repository.Injection
import com.example.wordsfactory.ui.introduction_screens.intro.IntroFragment
import com.example.wordsfactory.ui.navigation_fragments.PlaceHolderFragment

class MainActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //AppViewModel
        appViewModel = ViewModelProvider(this, Injection.provideFactory(this))
            .get(AppViewModel::class.java)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.SplashTheme)//splash screen
        setContentView(activityMainBinding.root)
        startMainFragment()
    }

    private fun startMainFragment() {
        /* if user is created -> PlaceHolderFragment else IntroFragment*/
        if (appViewModel.checkForUser()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, IntroFragment.newInstance()).commit()
        } else
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHolder, PlaceHolderFragment.newInstance()).commit()
    }
}