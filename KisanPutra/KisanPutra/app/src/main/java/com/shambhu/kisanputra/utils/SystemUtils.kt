package com.shambhu.kisanputra.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import java.io.File

public class SystemUtils {


    companion object {

        val PERMISSIONS_REQUEST_CAMERA = 14
        val PERMISSIONS_REQUEST_STORAGE = 15
        val PERMISSIONS_REQUEST_LOCATION = 16
        var PERMISSIONS_LOCATION =
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        var PERMISSIONS_CAMERA = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var PERMISSIONS_STORAGE =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


        fun isNetworkConnected(context: Context): Boolean {
            val connectivitymanager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkinfo = connectivitymanager.activeNetworkInfo
            return if (networkinfo == null || !networkinfo.isConnectedOrConnecting) {
                false
            } else {
                true
            }
        }

    }

    fun getInstance() : SystemUtils{
        return this
    }


    fun getTempMediaDirectory(context: Context): String? {
        val state = Environment.getExternalStorageState()
        var dir: File? = null
        if (Environment.MEDIA_MOUNTED == state) {
            dir = context.externalCacheDir
        } else {
            dir = context.cacheDir
        }

        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }
        return if (dir!!.exists() && dir.isDirectory) {
            dir.absolutePath
        } else null
    }



}