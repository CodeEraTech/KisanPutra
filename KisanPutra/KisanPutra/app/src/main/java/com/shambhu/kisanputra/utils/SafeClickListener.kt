package com.shambhu.kisanputra.utils

import android.os.SystemClock
import android.view.View

class SafeClickListener(var defaultInterval: Int = 1000, val onSafeCLick: (View) -> Unit) : View.OnClickListener {

    var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}