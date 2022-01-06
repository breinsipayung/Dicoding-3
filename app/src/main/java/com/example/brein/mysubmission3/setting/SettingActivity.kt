package com.example.brein.mysubmission3.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.brein.mysubmission3.databinding.ActivitySettingBinding
import com.example.brein.mysubmission3.helper.ViewModelFactory

class SettingActivity : AppCompatActivity() {
    private lateinit var viewModel: SettingViewModel
    private lateinit var settingBinding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)
        viewModel = settingViewModel(this as AppCompatActivity)
        title = "setting"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        darkTheme()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun darkTheme(){
        settingBinding.theme.apply {
            viewModel.getTheme().observe(this@SettingActivity, {
                if(it){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    this.isChecked = true
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    this.isChecked = false
                }
            })
            setOnCheckedChangeListener { _: CompoundButton?, isCheck: Boolean ->
                viewModel.saveTheme(isCheck)
            }
        }
    }

    private fun settingViewModel(activity: AppCompatActivity) : SettingViewModel{
        val theme = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,theme)[SettingViewModel::class.java]
    }
}