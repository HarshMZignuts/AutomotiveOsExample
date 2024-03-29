package com.example.automotive_example

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class CarHomeService : CarAppService() {
    override fun createHostValidator(): HostValidator {
       return if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0){
           HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
       }else{
           HostValidator.Builder(applicationContext)
               .addAllowedHosts(androidx.car.app.R.array.hosts_allowlist_sample)
               .build()
       }
    }

    override fun onCreateSession(): Session {
        return CarHomeSession()
    }
}