package com.project.githubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewPropertyAnimator
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.databinding.ActivitySplashScreenBinding
import com.project.githubuser.preference.SettingPreferences
import com.project.githubuser.preference.dataStore
import com.project.githubuser.viewModel.SettingPreferenceViewModel
import com.project.githubuser.viewModel.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
    private val time: Long = 1300
    private val alpha0 = 0f
    private val alpha1 = 1f
    private var propertyAnim: ViewPropertyAnimator? = null

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var settingPreferenceViewModel: SettingPreferenceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        settingPreferenceViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[SettingPreferenceViewModel::class.java]

        settingPreferenceViewModel.getThemeSetting().observe(this) { state ->
            Log.d("Settings", "$state")

            val theme = if (state) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(theme)
            propertyAnim?.start()
        }

        binding.imgSplash.alpha = alpha0
        propertyAnim = binding.imgSplash.animate().setDuration(time).alpha(alpha1).withEndAction {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun onDestroy() {
        propertyAnim?.cancel()
        super.onDestroy()
    }
}