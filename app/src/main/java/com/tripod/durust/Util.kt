package com.tripod.durust

import android.content.res.Resources
import android.util.DisplayMetrics


fun getScreenHeightInDP(): Int {
    val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val heightInPx = displayMetrics.heightPixels
    return heightInPx / displayMetrics.density.toInt()
}