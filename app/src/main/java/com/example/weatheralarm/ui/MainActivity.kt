package com.example.weatheralarm.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.weatheralarm.R
import com.example.weatheralarm.databinding.ActivityMainBinding
import com.example.weatheralarm.util.util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getData()
        }else{
            finish()
        }
    }

    private val permissionResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ result : Map<String, Boolean> ->
        val deniedList: List<String> = result.filter { !it.value }.map { it.key }

        when {
            deniedList.isNotEmpty() -> {
                val map = deniedList.groupBy { permission -> if (shouldShowRequestPermissionRationale(permission)) "DENIED" else "EXPLAINED" }
                map["DENIED"]?.let { requestPermission() }
                map["EXPLAINED"]?.let { moveSetting() }
            }
            else -> {
                getData()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setInit()
        setObserver()

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            val locationData = getLocation()
            val date = util.setDate()
            viewModel.getData("JSON",14,1, date[0], date[1], locationData[0].toString(), locationData[1].toString())
//            viewModel.getData("JSON",14,1, 20240317,1700, locationData[0].toString(), locationData[1].toString())

        }else { // 권한이 없을 때 권한을 요구함
            requestPermission()
        }
    }

    private fun setInit(){
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        mBinding.apply {
            lifecycleOwner = this@MainActivity
            mainViewModel = viewModel
        }
    }

    private fun setObserver() {
        mBinding.apply {
            viewModel.weatherData.observe(this@MainActivity, Observer { it ->
                tempText.text = "${it.response.body.items.item.get(0).fcstValue}°C"
                errYn = false
            })

            viewModel.errData.observe(this@MainActivity, Observer {
                errMsg.text = it
                errYn = true
            })
        }
    }

    private fun getLocation() : Array<Int?> {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val locationProvider = LocationManager.GPS_PROVIDER


        var userLocation : Location? = if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            null
        }else{
            locationManager?.getLastKnownLocation(locationProvider)
        }


        var lat : Int? = userLocation?.latitude?.let { Math.floor(it).toInt() }
        var long : Int? = userLocation?.longitude?.let { Math.floor(it).toInt() }

        val data = arrayOf(lat,long)

        return data
    }


    private fun requestPermission(){
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionResultLauncher.launch(permissions)
    }

    private fun moveSetting(){
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setMessage("권한 설정화면으로 이동하시겠습니까?")
            .setPositiveButton("이동", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                resultLauncher.launch(intent)
            })
            .setNegativeButton("종료", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                finish()
            })
            .show()
    }


    private fun getData(){
        val locationData = getLocation()
        val date = util.setDate()
        viewModel.getData("JSON",14,1, date[0], date[1], locationData[0].toString(), locationData[1].toString())
    }



}