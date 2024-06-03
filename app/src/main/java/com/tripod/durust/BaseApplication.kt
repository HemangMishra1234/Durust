package com.tripod.durust

import android.app.Application
import com.tripod.durust.data.HealthConnectManager

class BaseApplication: Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}