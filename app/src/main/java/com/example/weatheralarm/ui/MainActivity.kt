package com.example.weatheralarm.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.lifecycleOwner = this@MainActivity
        binding.mainViewModel = viewModel


        val TAG_CODE_PERMISSION_LOCATION = 100  //지역변수

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this, "권한 확인 되었습니다.", Toast.LENGTH_SHORT).show()

            //권한 확인이 되었을 때 실행 코드
            val locationData = getLocation()
            viewModel.getData("JSON",14,1, 20230606,1700,locationData.get(0).toString(),locationData.get(1).toString())
//            viewModel.getData("JSON",14,1, 20230606,1700,"37","126")


        }else { // 권한이 없을 때 권한을 요구함
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, TAG_CODE_PERMISSION_LOCATION)
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "권한 확인 되었습니다.", Toast.LENGTH_SHORT).show()
            val locationData = getLocation()
            viewModel.getData("JSON",14,1, 20230606,1700,locationData.get(0).toString(),locationData.get(1).toString())
        }else{
            Toast.makeText(this, "설정에서 권한을 허가 해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() : Array<Int?> {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val locationProvider = LocationManager.GPS_PROVIDER


        var userLocation : Location? = locationManager?.getLastKnownLocation(locationProvider)

        var lat : Int? = userLocation?.latitude?.let { Math.floor(it).toInt() }
        var long : Int? = userLocation?.longitude?.let { Math.floor(it).toInt() }

        val data = arrayOf(lat,long)

        return data
    }



}