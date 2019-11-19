package com.shambhu.kisanputra.base

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ColorStateListInflaterCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.shambhu.kisanputra.data.models.UserData
import com.shambhu.kisanputra.BuildConfig
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.models.UserLocationModel
import com.shambhu.kisanputra.utils.LocaleHelper
import com.shambhu.kisanputra.utils.SafeClickListener
import com.shambhu.kisanputra.utils.StaticData
import com.shambhu.kisanputra.utils.SystemUtils
import com.shambhu.social.SocialLoginType
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.IOException
import java.util.*

public open class BaseActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = BaseActivity::class.java.simpleName
    val REQ_IMAGE_PERM = 22
    private val permissionList = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var mDialog: AlertDialog? = null
    //lateinit var transferUtility : TransferUtility
   // var isAspect : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAwsImageUploading()
        checkLocation()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }

    fun initAwsImageUploading(){
        // Initializes TransferUtility, always do this before using it.
      //  transferUtility = AWSFileUploading.getTransferUtility(this@BaseActivity)!!
    }

    /** function will be implemented in Child classes whenever required  **/
    override fun onClick(v: View?) { }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                handleFinalCroppedImage(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.error, Toast.LENGTH_LONG).show()
            }
        }*/
    }

    fun handleProgressLoader(isLoading: Boolean){
        if (isLoading) {
            progress_layout.visibility = View.VISIBLE
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else {
            progress_layout.visibility = View.GONE
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    fun handleError(error: String){
        if(!TextUtils.isEmpty(error)){
            var message = error
            if (error.equals(StaticData.TIMEOUT)) {
                message =getString(R.string.timeout_error)
            } else if (error.equals(StaticData.UNDEFINED)) {
                message =getString(R.string.undefined_error)
            } else if (error.equals(StaticData.NODATA_FOUND))
                message=getString(R.string.no_data_found)
            showAlertDialog(message, "", "", getString(R.string.ok), null)
        }
    }

    /** In case no network is available, alert the user with a message **/
    fun showNetworkIssue() {

        showAlertDialog(
            getString(R.string.no_internet),
            getString(R.string.retry),
            getString(R.string.ok),
            "",
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> retryAction()
                    DialogInterface.BUTTON_NEGATIVE -> failureAccepted()
                }
            })
    }


    fun showAlertDialog(
        message: String?,
        positive: String,
        negative: String,
        neutral: String,
        listener: DialogInterface.OnClickListener?) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(message)
        if (!TextUtils.isEmpty(positive)) {
            builder.setPositiveButton(positive, listener)
        }
        if (!TextUtils.isEmpty(negative)) {
            builder.setNegativeButton(negative, listener)
        }
        if (!TextUtils.isEmpty(neutral)) {
            builder.setNeutralButton(neutral, listener)
        }
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
        mDialog = builder.create()

        mDialog!!.setOnShowListener( object : DialogInterface.OnShowListener {
            override fun onShow(dialog: DialogInterface?) {
                mDialog!!.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@BaseActivity,R.color.green_common));
                mDialog!!.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@BaseActivity,R.color.black));
            }
        })
        mDialog!!.show()
    }

    /** UserData has opted to retry API calling which failed earlier due to Network unavailability **/
    open fun retryAction() {

    }

    /** UserData has accepted API calling failure due to Network unavailability **/
    open fun failureAccepted() {

    }

    fun hideKeyBoard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
        } catch (e: Exception) {
            printLog("BaseActivity", e.toString())
        }

    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    fun printLog(tag:String, log:String){
        if(BuildConfig.DEBUG)
            Log.d(tag, log)
    }

    /** Image Selection Tasks **/

    fun verifyImagePermissions(isAspect : Boolean) {
       // this.isAspect=isAspect
        val requiredPermission = ArrayList<String>()
        for (perm in permissionList) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                requiredPermission.add(perm)
            }
        }
        if (!requiredPermission.isEmpty()) {
            showAlertDialog(getString(R.string.image_perm), "", "", getString(R.string.allow),
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_NEUTRAL -> ActivityCompat.requestPermissions(
                            this,
                            requiredPermission.toTypedArray(),
                            REQ_IMAGE_PERM
                        )
                    }
                })
        } else {
           // continueWithImageTasks()
        }
    }

    fun checkForLocationPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) && ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    //  openSetting(2);
                    requestPermissions(SystemUtils.PERMISSIONS_LOCATION, SystemUtils.PERMISSIONS_REQUEST_LOCATION)

                } else {
                    requestPermissions(SystemUtils.PERMISSIONS_LOCATION, SystemUtils.PERMISSIONS_REQUEST_LOCATION)
                }

            } else {
                //                actionCamera();
                //staartLocationTracking();
                requestLocationDialog()
            }
        } else {
            //            actionCamera();
            requestLocationDialog()
            // staartLocationTracking();
        }
    }

    internal var connectionCallbacks: GoogleApiClient.ConnectionCallbacks =
        object : GoogleApiClient.ConnectionCallbacks {
            override fun onConnected(bundle: Bundle?) {

            }

            override fun onConnectionSuspended(i: Int) {

            }
        }
    internal var connectionFailedListener: GoogleApiClient.OnConnectionFailedListener =
        GoogleApiClient.OnConnectionFailedListener { }

    internal var googleApiClient: GoogleApiClient? = null

    fun requestLocationDialog() {
        if (googleApiClient == null)
            googleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionFailedListener).build()

        googleApiClient!!.connect()

        val locationRequest = LocationRequest.create()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(30 * 1000)
        locationRequest.setFastestInterval(5 * 1000)
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        //**************************
        builder.setAlwaysShow(true) //this is the key ingredient
        //**************************

        val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback(ResultCallback<LocationSettingsResult> { result ->
            val status = result.status
            val state = result.getLocationSettingsStates()
            when (status.getStatusCode()) {
                LocationSettingsStatusCodes.SUCCESS ->
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    staartLocationTracking()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        status.startResolutionForResult(
                            this@BaseActivity, 1000
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        // Ignore the error.
                        Log.e("play loc error ", e.toString())
                    }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    showToast("Please install Google Play Services")
            }
        })

    }

    open fun staartLocationTracking(){

    }

    internal fun getUserLocation(location: Location?) : UserLocationModel?{
        var userLocationModel : UserLocationModel? =null
        val geocoder = Geocoder(this@BaseActivity, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(location!!.latitude, location!!.longitude, 1)
            val obj = addresses[0]
            var add = obj.getAddressLine(0)

            add = add + "," + obj.countryName
            add = add + "," + obj.countryCode
            add = add + "," + obj.adminArea
            add = add + "," + obj.postalCode
            add = add + "," + obj.subAdminArea
            add = add + "," + obj.locality
            add = add + "," + obj.subThoroughfare

            Log.e("IGA location", "Address : $add")
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            userLocationModel = UserLocationModel(obj.countryName, add, location!!.latitude, location!!.longitude)
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            userLocationModel=null
        } finally {
            userLocationModel=null
        }
        return userLocationModel
    }

    fun checkLocation(): Boolean {
            if (checkGPS())
                return true
            else {
                checkForLocationPermissions()
                Toast.makeText(this, "Location not found, Try again", Toast.LENGTH_SHORT).show()
                return false
            }
    }

    fun checkGPS(): Boolean {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            true
        } else {
//              checkForLocationPermissions();
            false
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SystemUtils.PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.size >= 2) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                //  staartLocationTracking();
                    requestLocationDialog()
            } else {
                showToast("Permission required for capture image")
            }
        }

        if (requestCode == REQ_IMAGE_PERM) {
            val permissionResults = HashMap<String, Int>()
            var deniedCount = 0

            /** Add all denied permissions  */
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults[permissions[i]] = grantResults[i]
                    deniedCount++
                }
            }

            /** all permissions granted  */
            if (deniedCount == 0) {
               // continueWithImageTasks()
            } else {
                for ((permName, permResult) in permissionResults) {
                    /** permission denied (first time, when 'Dont ask again' has not been checked).
                     * ask again, explaining the requirement
                     * shouldShowRequestPermissionRationale wil return true
                     */
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        showAlertDialog(getString(R.string.image_perm), "", "", getString(R.string.allow),
                            DialogInterface.OnClickListener { _, which ->
                                when (which) {
                                  //  DialogInterface.BUTTON_NEUTRAL -> verifyImagePermissions(isAspect)
                                }
                            })
                    }
                    /** Permission has been denied and Dont ask again checked shouldShowrequestPermissionRationale will return false **/
                    else {
                        showAlertDialog(getString(R.string.permission_alert), "", "", getString(R.string.ok),
                            DialogInterface.OnClickListener { _, which ->
                                when (which) {
                                    DialogInterface.BUTTON_NEUTRAL -> {
                                        val _intent = Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", packageName, null)
                                        )
                                        _intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(_intent)
                                        this@BaseActivity.finish()
                                    }
                                }
                            })
                    }
                }
            }
        }
    }

    open fun handleFinalCroppedImage(imageUri: Uri) {}

    /*
     * Begins to upload the file specified by the file path to AWS
     */

    open fun fileUploadSuccess(imageKey: String) {}

    open fun fileUploadError(){}

    fun showToast(msg : String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun replaceFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction = supportFragmentManager.beginTransaction().replace(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    fun addFragment(container: Int, fragment: Fragment, enab_backStack: Boolean) {
        var fragmentTransaction = supportFragmentManager.beginTransaction().add(container, fragment)
        if (enab_backStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    open fun openUpQuestionaireScreen(){

    }

    fun setSpannable(
        textView: TextView,
        message: String,
        matched_string: String,
        color: Int,
        underlineStatus: Boolean /*, setOnClickListener: OnClickListener*/
    ) {

        var spannableStr = SpannableStringBuilder(message)


        var start = message.indexOf(matched_string, 1)
        spannableStr.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            start + matched_string.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val clickableSpan = object : ClickableSpan() {

            override fun onClick(textView: View) {
                // Toast.makeText(textView.context,"clicked",Toast.LENGTH_LONG).show()
                //  setOnClickListener.onClick(textView)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = underlineStatus
            }
        }

        spannableStr.setSpan(clickableSpan, start, start + matched_string.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)



        textView.setLinkTextColor(color)
        textView.setText(spannableStr, TextView.BufferType.SPANNABLE)
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = ContextCompat.getColor(textView.context, android.R.color.transparent)
    }

    fun getRangedArray(from : Int ,to : Int , plusOneMore : Boolean) : ArrayList<String>{
        var rangList : ArrayList<String> = ArrayList()
        for (i in from..to){
            rangList.add(""+i);
            if (i==to && plusOneMore)
                rangList.add(""+i+"+")
        }
        return rangList
    }

    fun logOutFromApp(){
        showAlertDialog(getString(R.string.are_you_sure_to_logout),getString(R.string.yes),getString(R.string.no),"",DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    hitLogoutAPI()
                }
            }
        })
    }

    open fun hitLogoutAPI() {

    }

    fun restartApp(){
        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(i)
    }

    fun getLoginType(type : SocialLoginType) : String{
        when(type){
            SocialLoginType.FACEBOOK-> return StaticData.LOGINTYPE_FACEBOOK
            SocialLoginType.GOOGLE-> return StaticData.LOGINTYPE_GOOGLE
            SocialLoginType.TRUECALLER-> return StaticData.LOGINTYPE_TRUECALLER
            else-> return StaticData.LOGINTYPE_MOBILE
        }
    }

}
