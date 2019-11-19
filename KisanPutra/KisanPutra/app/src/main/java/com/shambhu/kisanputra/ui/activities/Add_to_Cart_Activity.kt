package com.shambhu.kisanputra.ui.activities

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.models.response.defaultAddress.GetDefaultAddress
import com.shambhu.kisanputra.data.models.response.getCart_list_model
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface

import com.shambhu.kisanputra.ui.adapters.Cart_item_Adapter
import com.shambhu.kisanputra.utils.StaticData
import com.shambhu.kisanputra.mPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale

class Add_to_Cart_Activity : AppCompatActivity()/*, LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {

    lateinit var edt_delivery_add: EditText
    internal lateinit var edt_delivery_date: EditText
    internal lateinit var txt_total_rupis: TextView
    internal lateinit var txt_pace_order: TextView
    internal lateinit var toolbar_title: TextView
    internal lateinit var recycler_cart: RecyclerView
    internal lateinit var cart_item_adapter: Cart_item_Adapter
    internal lateinit var toolbar: Toolbar
//    internal lateinit var mLocationRequest: LocationRequest
//    internal lateinit var mGoogleApiClient: GoogleApiClient
    internal lateinit var mCurrentLocation: Location
    protected var hasLocationPermissions: Boolean = false
    protected var hasEdited = false
    lateinit var myCalendar: Calendar

//    @Inject
//    lateinit var homeviewmodelFactory: ViewModelFactory
//    lateinit var homeViewModel: getcartViewModel

    internal var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar!!.set(Calendar.YEAR, year)
            myCalendar!!.set(Calendar.MONTH, monthOfYear)
            myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

    protected fun createLocationRequest() {
//        mLocationRequest = LocationRequest()
//        mLocationRequest!!.interval = INTERVAL
//        mLocationRequest!!.fastestInterval = FASTEST_INTERVAL
//        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
//        AndroidInjection.inject(this)
//        homeViewModel = ViewModelProviders.of(this, homeviewmodelFactory).get(getcartViewModel::class.java)

        setContentView(R.layout.activity_add_to_cart)
        init()
//        fetchProducts()


        val apiService = ApiClient.getClient().create(ApiInterface::class.java)


        val call = apiService.getcartdata(StaticData.TOKENTYPE+ mPrefs.prefAuthToken)
        call.enqueue(object : Callback<getCart_list_model> {
            override fun onResponse(
                call: Call<getCart_list_model>,
                response: Response<getCart_list_model>
            ) {
                val statusCode = response.code()
                val cart_total = response.body()!!.cartTotal
                val list = response.body()!!.data

            val token=StaticData.TOKENTYPE+ mPrefs.prefAuthToken

                cart_item_adapter = Cart_item_Adapter(this@Add_to_Cart_Activity, list,cart_total,token,txt_total_rupis)
                recycler_cart!!.adapter = cart_item_adapter

                txt_total_rupis!!.text = resources.getString(R.string.Rs) + cart_total


            }

            override fun onFailure(call: Call<getCart_list_model>, t: Throwable) {
                // Log error here since request failed
              Toast.makeText(this@Add_to_Cart_Activity,"error",Toast.LENGTH_LONG).show()
            }
        })


      }

//    private fun fetchProducts() {
//
//        if (!ConnectivityStatus.isConnected(this)) {
//
//            return
//        }
//
//        homeViewModel.getcartlist(StaticData.TOKENTYPE+ mPrefs.prefAuthToken)
//
//        homeViewModel.getCartlistdetail().observe(this, Observer { resource ->
//            setSlider(resource!!.data as ArrayList<getCart_list_model.Data>)
//        })
//    }

//    fun setSlider(imageArray : ArrayList<getCart_list_model>){
//
//        if (imageArray.size > 1) {
//
//        }
//
//
//
//    }



    private fun init() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("Your Cart")
//        createLocationRequest()
        myCalendar = Calendar.getInstance()


       /* if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!checkForLocationPermissions())
                getLocationPermissions()
        }*/
//        getDefaultAddress()
       /* mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()*/

        edt_delivery_add = findViewById<View>(R.id.edt_delivery_add) as EditText
        edt_delivery_date = findViewById<View>(R.id.edt_delivery_date) as EditText
        txt_total_rupis = findViewById<View>(R.id.txt_total_rupis) as TextView
        txt_pace_order = findViewById<View>(R.id.txt_pace_order) as TextView
        toolbar_title = findViewById<View>(R.id.toolbar_title) as TextView
        toolbar_title!!.text = "Your Cart"

        recycler_cart = findViewById<View>(R.id.recycler_cart) as RecyclerView
        recycler_cart!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        cart_item_adapter = Cart_item_Adapter(this@Add_to_Cart_Activity, temp_data.Cart_item_list)
//        recycler_cart!!.adapter = cart_item_adapter
//
//        txt_total_rupis!!.text = resources.getString(R.string.Rs) + temp_data.Total_rupis
        txt_pace_order!!.setOnClickListener {
            val intent = Intent(this@Add_to_Cart_Activity, Add_to_Money_Avtivity::class.java)
            startActivity(intent)
        }


        edt_delivery_add!!.setOnClickListener {
            val intent = Intent(this@Add_to_Cart_Activity, Detail_Address_Activity::class.java)
            startActivity(intent)
//            openPlaceAutoCompleteViewSource()
        }
        edt_delivery_date!!.setOnClickListener {
            DatePickerDialog(
                this@Add_to_Cart_Activity,
                R.style.DialogTheme,
                date,
                this.myCalendar
                    .get(Calendar.YEAR),
                this.myCalendar.get(Calendar.MONTH),
                this.myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        edt_delivery_add.setText(mPrefs.prefUserDaultLocation?.address)
    }

    private fun getDefaultAddress() {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getDefaultAddresses(StaticData.TOKENTYPE+ mPrefs.prefAuthToken)
        call.enqueue(object : Callback<GetDefaultAddress> {
            override fun onResponse(
                call: Call<GetDefaultAddress>,
                response: Response<GetDefaultAddress>
            ) {
                val statusCode = response.code()

                val status = response.body()!!.status
                if(status==true)
                {      var addressStr=""
                    if (response.body()!!.data?.get(0)!!.address!=null){
                        addressStr= response.body()!!.data?.get(0)!!.address!!
                    }


                }
            }

            override fun onFailure(call: Call<GetDefaultAddress>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(this@Add_to_Cart_Activity,"error",Toast.LENGTH_LONG).show()

//                    startActivity(Intent(this@Detail_Address_Activity,Addres_add_Activity::class.java))
            }
        })
    }

  /*  private fun getCompleteAddressString(LATITUDE: Double, LONGITUDE: Double): String {
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
    }*/

    /*fun openPlaceAutoCompleteViewSource() {


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


    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppConstants.SEARCH_SOURCE && resultCode == Activity.RESULT_OK) {

            val place = Autocomplete.getPlaceFromIntent(data!!)
            hasEdited = true
            edt_delivery_add.setText(place.address!!.toString())
        }

    }

    public override fun onStart() {
        super.onStart()

//        mGoogleApiClient.connect()
    }

    public override fun onStop() {
        super.onStop()
//        mGoogleApiClient.disconnect()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onConnected(bundle: Bundle?) {

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
    }*/

    /*protected fun checkForLocationPermissions(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                *//*String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);*//*
                hasLocationPermissions = false
            } else {
                hasLocationPermissions = true
            }
        } else {
            hasLocationPermissions = true
        }
        return hasLocationPermissions;
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
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION)
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


    }*/

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edt_delivery_date.setText(sdf.format(myCalendar.time))
    }

    companion object {
        private val INTERVAL = (1000 * 10).toLong()
        private val FASTEST_INTERVAL = (1000 * 5).toLong()
        protected val REQUEST_PERMISSIONS_LOCATION = 2
    }
}
