package com.example.projectcapstone.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.projectcapstone.MainActivity
import com.example.projectcapstone.R
import com.example.projectcapstone.authentication.login.LoginActivity
import com.example.projectcapstone.model.UserPreference
import com.example.projectcapstone.viewmodel.MainViewModel
import com.example.projectcapstone.viewmodel.ViewModelUserFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setupViewModel()
        supportActionBar?.hide()
    }
    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {
            if (it.isLogin){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}



