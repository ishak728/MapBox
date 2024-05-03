package com.ishak.myapplication

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class AppPermission {





    fun isLocationOk(context:Context):Boolean{
       return  ContextCompat.checkSelfPermission(
           context,
           android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
    }



}