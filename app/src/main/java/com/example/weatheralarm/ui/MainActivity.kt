package com.example.weatheralarm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.weatheralarm.R
import com.example.weatheralarm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val binding get() = mBinding!! // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.lifecycleOwner = this@MainActivity
        binding.mainViewModel = viewModel

        viewModel.getData("JSON",14,1, 20230531,1700,"57","112")

    }
}