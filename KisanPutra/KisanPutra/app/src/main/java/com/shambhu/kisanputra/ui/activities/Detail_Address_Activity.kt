package com.shambhu.kisanputra.ui.activities

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dmgdesignuk.locationutils.easylocationutility.EasyLocationUtility
import com.dmgdesignuk.locationutils.easylocationutility.LocationRequestCallback
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.kisanputra.data.models.DetailAddressModel
import com.shambhu.kisanputra.data.models.response.detail_Add_address.DetailAddAddress
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface
import com.shambhu.kisanputra.data.rest_api.interceptor.ConnectivityStatus
import com.shambhu.kisanputra.databinding.DetailAddressBinding
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.utils.StaticData
import com.shambhu.kisanputra.viewmodels.UserDetailsViewModel
import com.shambhu.kisanputra.viewmodels.ViewModelFactory
import com.shambhu.social.UserModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail__address_.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class Detail_Address_Activity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    override fun onMarkerClick(p0: Marker) = false

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        setUpMap()
    }

    private fun setUpMap() {
        // 1

        if (checkLocation()) {
            mMap.isMyLocationEnabled = true

// 2
            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                // Got last known location. In some rare situations this can be null.
                // 3
                if (location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        } else {
            checkLocation()
        }


    }

    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    companion object {
        private val INTERVAL = (1000 * 10).toLong()
        private val FASTEST_INTERVAL = (1000 * 5).toLong()
        protected val REQUEST_PERMISSIONS_LOCATION = 2
        fun disableView(v: View) {

            v.setOnTouchListener { v, event -> true }

            if (v is ViewGroup) {
                for (i in 0 until v.childCount) {
                    val child = v.getChildAt(i)
                    disableView(child)
                }
            }
        }
    }

    lateinit var userModel: UserModel
    lateinit var binding: DetailAddressBinding
    lateinit var detailAddressModel: DetailAddressModel
    lateinit var mapFragment: SupportMapFragment
    lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mLocationRequest: LocationRequest
    lateinit var mGoogleApiClient: GoogleApiClient
    var mCurrentLocation: Location? = null
    protected var hasLocationPermissions = false
    protected var hasEdited = false
    lateinit var edt_delivery_add: EditText
    lateinit var edt_building_name: EditText
    lateinit var edt_flate_no: EditText

    var addr = ""
    var zipcode = ""
    var city = ""
    var state = ""
    var contry = ""

    //  private val mMap: GoogleMap? = null

    @Inject
    lateinit var userdetailFactory: ViewModelFactory
    lateinit var mUserViewModel: UserDetailsViewModel

    var address: String? = null
    var addressnew: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail__address_)

        detailAddressModel = DetailAddressModel(false, "", "", "", "")
        checkLocation()
        createLocationRequest()
// Initialize Places.
        // Places.initialize(applicationContext, getString(R.))

// Create a new Places client instance.
        // val placesClient = Places.createClient(this)

        mUserViewModel =
            ViewModelProviders.of(this, userdetailFactory).get(UserDetailsViewModel::class.java)
        hideKeyBoard()
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_home_map) as SupportMapFragment

        intent = this.intent
        if (intent != null) {
            if (null!=intent.getParcelableExtra("userModel")) {
                userModel = intent.getParcelableExtra("userModel")
                mUserViewModel.socialRegister(userModel, StaticData.LOGINTYPE_MOBILE)
            }
        }
        binding.rg.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                val chrbhome: RadioButton = findViewById<RadioButton>(checkedId)
                detailAddressModel._ind = chrbhome.id == rbhome.id
            }
        })


//        mapFragment.getMapAsync { googleMap ->
//            mMap = googleMap
//        }
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (checkForLocationPermissions2()) {

            } else {
                getLocationPermissions()
            }

        }

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        mGoogleApiClient.connect()


        edt_delivery_add = findViewById(R.id.edt_delivery_add)
        edt_building_name = findViewById(R.id.edt_building_name)
        edt_flate_no = findViewById(R.id.edt_flate_no)
        edt_delivery_add.setOnClickListener {
            openPlaceAutoCompleteViewSource()
        }



        mUserViewModel.loading.observe(this, Observer { isLoading ->
            handleProgressLoader(isLoading)
        })
        mUserViewModel.dataLoadError.observe(this, Observer { error ->
            handleError(error)
        })
        mUserViewModel.getRegisterLiveData().observe(this, Observer { resource ->
            if (resource != null) {
                mPrefs.prefUserDetails = resource
                mPrefs.prefAuthToken = resource.token

            } else {
                handleError(StaticData.UNDEFINED)
            }

        })


    }

    private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
        var strAdd = ""
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")

                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()

            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        return strAdd
    }

    fun openPlaceAutoCompleteViewSource() {


        Places.initialize(applicationContext, resources.getString(R.string.google_maps_key))

        //        PlacesClient placesClient = Places.createClient(this);

        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .setCountry("IN")
            .build(this)
        startActivityForResult(intent, AppConstants.SEARCH_SOURCE)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppConstants.SEARCH_SOURCE && resultCode == RESULT_OK) {

            val place = Autocomplete.getPlaceFromIntent(data!!)
            hasEdited = true
            edt_delivery_add.setText(place.address!!.toString())
            addressnew = place.address!!.toString()


            var geocoder = Geocoder(this, Locale.ENGLISH)
            var addresses =
                geocoder.getFromLocation(place.latLng!!.latitude, place.latLng!!.longitude, 1)
            var address: Address? = null

            addressnew = addresses[0].getAddressLine(0) + "," + addresses[0].subAdminArea
            addr = addresses[0].getAddressLine(0) + "," + addresses[0].subAdminArea
            city = addresses[0].locality
            state = addresses[0].adminArea
            contry = addresses[0].countryName

            for (i in addresses.indices) {
                address = addresses[i]
                if (address!!.postalCode != null) {
                    zipcode = address.postalCode
                    break
                }

            }

        }

    }

    public override fun onStart() {
        super.onStart()

        checkLocation()
        createLocationRequest()
    }

    public override fun onStop() {
        super.onStop()
        mGoogleApiClient.disconnect()
    }


    override fun onConnected(bundle: Bundle?) {

        startLocationUpdates()
    }

    protected fun startLocationUpdates() {
        val pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient, mLocationRequest, this
        )

    }

    override fun onConnectionSuspended(i: Int) {

    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {

    }


    override fun onLocationChanged(location: Location) {
        if (!hasEdited) {
            mCurrentLocation = location
            if (null != mCurrentLocation) {
                val lat = mCurrentLocation!!.latitude.toString()
                val lng = mCurrentLocation!!.longitude.toString()
                val Address_curent_location = getCompleteAddressString(
                    mCurrentLocation!!.latitude,
                    mCurrentLocation!!.longitude
                )
//                edt_delivery_add.setText(" $Address_curent_location")

            }
        }


    }

    protected fun checkForLocationPermissions2(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                /*String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);*/
                hasLocationPermissions.apply { false }
            } else {
                hasLocationPermissions.apply { true }

            }
        } else {
            hasLocationPermissions.apply { true }
        }
    }

    protected fun getLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                ActivityCompat.requestPermissions(
                    this, permissions,
                    REQUEST_PERMISSIONS_LOCATION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
            if (grantResults.size == 2) {
                hasLocationPermissions =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }


    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v) {
            continue_btn -> {


                //register here
                setRegistrationProcess(StaticData.LOGINTYPE_MOBILE)

                // intent.putExtra("userModel",userModel)


                //redircttoHome()
            }
        }
    }

    fun setRegistrationProcess(type: String) {


        if (!ConnectivityStatus.isConnected(this)) {
            showNetworkIssue()
            return
        }

//            mUserViewModel.socialRegister(userModel, type)
//            mUserViewModel.loading.observe(this, Observer { isLoading ->
//                handleProgressLoader(isLoading)
//            })
//            mUserViewModel.dataLoadError.observe(this, Observer { error ->
//                handleError(error)
//            })
//            mUserViewModel.getRegisterLiveData().observe(this, Observer { resource ->
//                if (resource != null) {
//                    mPrefs.prefUserDetails=resource!!
//                    mPrefs.prefAuthToken=resource!!.token
//                    redircttoHome()
//                    }else {
//                    handleError(StaticData.UNDEFINED)
//                }
//
//            })

        redircttoHome()

    }

    fun redircttoHome() {


        var act_add =
            edt_flate_no.text.toString() + "  , " + edt_building_name.text.toString() + " , " + addressnew

        if (edt_delivery_add.text.isEmpty() || edt_flate_no.text.isEmpty() || edt_building_name.text.isEmpty())
            showAlertDialog("Please select address first", "OK", "", "",
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog!!.dismiss()
                    }
                })
        else {
            val apiService = ApiClient.getClient().create(ApiInterface::class.java)

            val params = HashMap<String, String>()
            params["address"] = act_add
            params["landmark"] = addr
            params["pincode"] = zipcode
            params["country"] = contry
            params["starte"] = state
            params["city"] = city
            params["isDefault"] = "1"


            val call = apiService.add_Address(StaticData.TOKENTYPE + mPrefs.prefAuthToken, params)
            call.enqueue(object : Callback<DetailAddAddress> {
                override fun onResponse(
                    call: Call<DetailAddAddress>,
                    response: Response<DetailAddAddress>
                ) {
                    val statusCode = response.code()

                    val status = response.body()!!.status
                    if (status) {
                        startActivity(
                            Intent(
                                this@Detail_Address_Activity,
                                Addres_add_Activity::class.java
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<DetailAddAddress>, t: Throwable) {
                    // Log error here since request failed
                    Toast.makeText(this@Detail_Address_Activity, "error", Toast.LENGTH_LONG).show()

//                    startActivity(Intent(this@Detail_Address_Activity,Addres_add_Activity::class.java))
                }
            })

        }


//        var intent: Intent = Intent(this,HomeActivity::class.java)
//       // intent.putExtra("userModel",userModel)
//        startActivity(intent)
    }

    override fun staartLocationTracking() {
        super.staartLocationTracking()

        Log.e("loc : ", "Entered in location listner ")


        val locationUtility: EasyLocationUtility = EasyLocationUtility(this)

        locationUtility.getCurrentLocationUpdates(object : LocationRequestCallback {
            override fun onLocationResult(p0: Location?) {

                // use location model from here
                val locationModel = getUserLocation(p0)
                address = locationModel!!.state
                showToast(locationModel.state)
            }

            override fun onFailedRequest(p0: String?) {
                locationUtility.getLastKnownLocation(object : LocationRequestCallback {
                    override fun onLocationResult(p0: Location?) {
                        // use location model from here
                        val locationModel = getUserLocation(p0)
                        address = locationModel!!.state
                        showToast(locationModel.state)
                    }

                    override fun onFailedRequest(p0: String?) {
                        showToast(p0.toString())
                        showToast(p0!!)
                    }
                })
            }
        })
    }
}
