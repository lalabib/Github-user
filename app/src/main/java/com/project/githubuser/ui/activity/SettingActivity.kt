package com.project.githubuser.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.databinding.ActivitySettingBinding
import com.project.githubuser.preference.SettingPreferences
import com.project.githubuser.preference.dataStore
import com.project.githubuser.viewModel.SettingPreferenceViewModel
import com.project.githubuser.viewModel.ViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var settingPreferenceViewModel: SettingPreferenceViewModel
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnBack.setOnClickListener { finish() }

        val pref = SettingPreferences.getInstance(dataStore)
        settingPreferenceViewModel =
            ViewModelProvider(this, ViewModelFactory(pref))[SettingPreferenceViewModel::class.java]

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked: Boolean ->
            settingPreferenceViewModel.saveThemeSetting(isChecked)
        }

        settingPreferenceViewModel.getThemeSetting().observe(this) { state ->
            Log.d("Settings", "$state")

            val theme = if (state) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO

            AppCompatDelegate.setDefaultNightMode(theme)

            binding.switchTheme.isChecked = state
        }
    }
}