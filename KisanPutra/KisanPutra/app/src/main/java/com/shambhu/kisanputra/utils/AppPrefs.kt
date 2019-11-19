package com.shambhu.kisanputra.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.data.models.response.defaultAddress.GetDefaultAddress

class AppPrefs(context: Context) {

    val APP_PREF_FILE = "com.shambhu.kisanputra.prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(APP_PREF_FILE, 0)
    /** 0 --> MODE_PRIVATE **/

    val PREF_DEVICE_TOKEN = "pref_token"
    val PREF_LANGUAGE = "pref_language"
    val PREF_HEADER_LANG = "pref_header_lang"
    val PREF_AUTH_TOKEN = "pref_auth_token"
    val PREF_USER_DETAILS = "pref_user_details"
    val PREF_USER_DEFAULT_ADDRESS = "pref_user_default_address"

    var prefDeviceToken: String
        get() = prefs.getString(PREF_DEVICE_TOKEN, "djfhdsfdsmjfhj")
        set(value) = prefs.edit().putString(PREF_DEVICE_TOKEN, value).apply()

    var prefLanguage: String
        get() = prefs.getString(PREF_LANGUAGE, StaticData.LANG_ENGLISH)
        set(value) = prefs.edit().putString(PREF_LANGUAGE, value).apply()

    var prefHeaderLang: String
        get() = prefs.getString(PREF_HEADER_LANG, "en")
        set(value) = prefs.edit().putString(PREF_HEADER_LANG, value).apply()

    var prefAuthToken: String
        get() = prefs.getString(PREF_AUTH_TOKEN, "")
        set(value) = prefs.edit().putString(PREF_AUTH_TOKEN, value).apply()

    var prefUserDetails: UserData?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_USER_DETAILS, ""))) {
                return Gson().fromJson(prefs.getString(PREF_USER_DETAILS, ""), UserData::class.java)
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_USER_DETAILS, inString).apply()
        }
    var prefUserDaultLocation: GetDefaultAddress.Datum?
        get() {
            if (!TextUtils.isEmpty(prefs.getString(PREF_USER_DEFAULT_ADDRESS, ""))) {
                return Gson().fromJson(prefs.getString(PREF_USER_DEFAULT_ADDRESS, ""),  GetDefaultAddress.Datum::class.java)
            } else
                return null
        }
        set(value) {
            val gson = GsonBuilder().create()
            val inString = gson.toJson(value)
            prefs.edit().putString(PREF_USER_DEFAULT_ADDRESS, inString).apply()
        }
    fun clearUserDetails() {
        prefs.edit().clear().apply()
    }
}
