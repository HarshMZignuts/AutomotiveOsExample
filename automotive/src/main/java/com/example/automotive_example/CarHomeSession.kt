package com.example.automotive_example

import android.content.Intent
import android.media.tv.TvInputService.Session
import androidx.car.app.Screen

class CarHomeSession : androidx.car.app.Session() {
    override fun onCreateScreen(intent: Intent): Screen {
       return CarHomeScreen(carContext)
    }
}