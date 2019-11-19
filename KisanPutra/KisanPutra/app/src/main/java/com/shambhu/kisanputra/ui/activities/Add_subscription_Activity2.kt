package com.shambhu.kisanputra.ui.activities

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat

import com.bumptech.glide.Glide
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.temp.temp_data
import com.squareup.timessquare.CalendarPickerView

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.Locale

import com.shambhu.kisanputra.R.color
import com.shambhu.kisanputra.R.id
import com.shambhu.kisanputra.R.layout

class Add_subscription_Activity2 : AppCompatActivity(), LocationListener,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    lateinit var toolbar: Toolbar
    lateinit var img_imtem_pic: ImageView
    lateinit var txt_item_name: TextView
    lateinit var txt_item_qnty: TextView
    lateinit var txt_item_prise: TextView
    lateinit var txt_count_item: TextView
    lateinit var img_dic_item: ImageView
    lateinit var img_incre_item: ImageView
    lateinit var txt_subscri_date: TextView
    lateinit var card_everday: CardView
    lateinit var card_every_3_day: CardView
    lateinit var card_alternateday: CardView
    lateinit var card_every_7_day: CardView
    lateinit var txt_everday: TextView
    lateinit var txt_every_3_day: TextView
    lateinit var txt_alternate: TextView
    lateinit var txt_every_7_day: TextView
    //    CalendarView calender;
    lateinit var calendar: CalendarPickerView
    lateinit var edt_delivery_add: EditText
    lateinit var txt_subscribe_ok: TextView

    lateinit var mLocationRequest: LocationRequest
    lateinit var mGoogleApiClient: GoogleApiClient
    var mCurrentLocation: Location? = null
    protected var hasLocationPermissions = false
    protected var hasEdited = false

    lateinit var myCalendar: Calendar
     var nextYear = Calendar.getInstance()
    lateinit var sdf: SimpleDateFormat
    lateinit var line_cal: LinearLayout

     var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = INTERVAL
        mLocationRequest.fastestInterval = FASTEST_INTERVAL
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_add_subscription)
        toolbar = findViewById<View>(id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        createLocationRequest()

        myCalendar = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 2)
        sdf = SimpleDateFormat("dd/MM/yyyy")


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (checkForLocationPermissions())
            {

            }else{
                getLocationPermissions()
            }

        }

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()

        init()
        listener()
    }


    private fun init() {
        /*
         ImageView img_imtem_pic;
    TextView txt_item_name,txt_item_qnty,txt_item_prise;
    ImageView img_dic_item,img_incre_item;
    TextView txt_subscri_date;
    CardView card_everday,card_every_3_day,card_alternateday,carc_every_7_day;
    TextView txt_everday,txt_every_3_day,txt_alternate,txt_every_7_day;
    CalendarView calender;
    EditText edt_delivery_add;
    TextView txt_subscribe_ok;
    */


        val img_url = temp_data.subsription_item_list[0].img_url
        val item_name = temp_data.subsription_item_list[0].item_name
        val item_prise = temp_data.subsription_item_list[0].item_prise
        val item_qnty = temp_data.subsription_item_list[0].item_qnty
        val item_count = temp_data.subsription_item_list[0].item_count
        val item_subscrip_date = temp_data.subsription_item_list[0].item_subscri_date


        txt_item_name = findViewById(id.txt_item_name)
        txt_item_qnty = findViewById(id.txt_item_qnty)
        txt_item_prise = findViewById(id.txt_item_prise)
        txt_count_item = findViewById(id.txt_count_item)

        img_dic_item = findViewById(id.img_dic_item)
        img_imtem_pic = findViewById(id.img_imtem_pic)
        img_incre_item = findViewById(id.img_incre_item)

        txt_subscri_date = findViewById(id.txt_subscri_date)

        card_everday = findViewById(id.card_everday)
        card_every_3_day = findViewById(id.card_every_3_day)
        card_alternateday = findViewById(id.card_alternateday)
        card_every_7_day = findViewById(id.card_every_7_day)

        txt_everday = findViewById(id.txt_everday)
        txt_every_3_day = findViewById(id.txt_every_3_day)
        txt_alternate = findViewById(id.txt_alternate)
        txt_every_7_day = findViewById(id.txt_every_7_day)
        //        calender = findViewById(id.calender);
        line_cal = findViewById(id.line_cal)

        calendar = findViewById<View>(id.calendar_view) as CalendarPickerView
        try {
            val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
            calendar.init(d, nextYear.time)
                .withSelectedDate(d).inMode(CalendarPickerView.SelectionMode.SINGLE)

            disableView(line_cal)
            calendar.setCellClickInterceptor(null)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //        Date today = new Date();


        edt_delivery_add = findViewById(id.edt_delivery_add)
        txt_subscribe_ok = findViewById(id.txt_subscribe_ok)

        Glide.with(this).load(img_url).into(img_imtem_pic)
        txt_item_name.text = item_name
        txt_item_qnty.text = item_qnty
        txt_item_prise.text = item_prise
        txt_subscri_date.text = item_subscrip_date
        txt_count_item.text = item_count

    }

    private fun listener() {

        //        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
        //            @Override
        //            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
        //                String date = dayOfMonth + "/" + month + "/"+ year ;
        //                Toast.makeText(Add_subscription_Activity.this, date, Toast.LENGTH_SHORT).show();
        //
        //            }
        //        });

        img_incre_item.setOnClickListener {
            var qty = Integer.parseInt(temp_data.subsription_item_list[0].item_count)
            qty = qty + 1
            temp_data.subsription_item_list[0].item_count = qty.toString()
            txt_count_item.text = qty.toString()

            var prise = Integer.parseInt(temp_data.subsription_item_list[0].item_prise)
            prise = prise * qty
            //                temp_data.subsription_item_list.get(0).setItem_prise(String.valueOf(prise));
            txt_item_prise.text = prise.toString()
        }
        img_dic_item.setOnClickListener {
            var qty = Integer.parseInt(temp_data.subsription_item_list[0].item_count)

            var prise = Integer.parseInt(txt_item_prise.text.toString())
            prise = prise - Integer.parseInt(temp_data.subsription_item_list[0].item_prise)

            qty = qty - 1
            temp_data.subsription_item_list[0].item_count = qty.toString()
            txt_count_item.text = qty.toString()


            //                temp_data.subsription_item_list.get(0).setItem_prise(String.valueOf(prise));
            txt_item_prise.text = prise.toString()
        }


        card_everday.setOnClickListener {
            txt_everday.setTextColor(resources.getColor(color.white))
            card_everday.setCardBackgroundColor(resources.getColor(color.green1))

            txt_every_3_day.setTextColor(resources.getColor(color.black))
            card_every_3_day.setCardBackgroundColor(resources.getColor(color.white))

            txt_alternate.setTextColor(resources.getColor(color.black))
            card_alternateday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_7_day.setTextColor(resources.getColor(color.black))
            card_every_7_day.setCardBackgroundColor(resources.getColor(color.white))

            val dates = ArrayList<Date>()
            try {
                val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
                dates.add(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            for (i in 0..17) {
                val c = Calendar.getInstance()
                c.time = dates[i]
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 1)
                val newDate = sdf.format(c.time)
                var d: Date? = null
                try {
                    d = sdf.parse(newDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                dates.add(d!!)
                if (i == 17) {
                    calendar.init(Date(), nextYear.time) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                        .withSelectedDates(dates)
                    disableView(line_cal)
                    calendar.setCellClickInterceptor(null)
                }
            }
        }
        card_every_3_day.setOnClickListener {
            txt_everday.setTextColor(resources.getColor(color.black))
            card_everday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_3_day.setTextColor(resources.getColor(color.white))
            card_every_3_day.setCardBackgroundColor(resources.getColor(color.green1))

            txt_alternate.setTextColor(resources.getColor(color.black))
            card_alternateday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_7_day.setTextColor(resources.getColor(color.black))
            card_every_7_day.setCardBackgroundColor(resources.getColor(color.white))


            val dates = ArrayList<Date>()
            try {
                val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
                dates.add(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            var i = 0
            while (i < 18) {
                val c = Calendar.getInstance()
                c.time = dates[dates.size - 1]
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 3)
                val newDate = sdf.format(c.time)
                var d: Date? = null
                try {
                    d = sdf.parse(newDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                dates.add(d!!)
                calendar.init(Date(), nextYear.time) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                    .withSelectedDates(dates)
                disableView(line_cal)
                calendar.setCellClickInterceptor(null)
                i = i + 3
            }
        }
        card_alternateday.setOnClickListener {
            txt_everday.setTextColor(resources.getColor(color.black))
            card_everday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_3_day.setTextColor(resources.getColor(color.black))
            card_every_3_day.setCardBackgroundColor(resources.getColor(color.white))

            txt_alternate.setTextColor(resources.getColor(color.white))
            card_alternateday.setCardBackgroundColor(resources.getColor(color.green1))

            txt_every_7_day.setTextColor(resources.getColor(color.black))
            card_every_7_day.setCardBackgroundColor(resources.getColor(color.white))

            val dates = ArrayList<Date>()
            try {
                val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
                dates.add(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            var i = 0
            while (i < 18) {
                val c = Calendar.getInstance()
                c.time = dates[dates.size - 1]
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 2)
                val newDate = sdf.format(c.time)
                var d: Date? = null
                try {
                    d = sdf.parse(newDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                dates.add(d!!)
                calendar.init(Date(), nextYear.time) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                    .withSelectedDates(dates)
                disableView(line_cal)
                calendar.setCellClickInterceptor(null)
                i = i + 2
            }
        }
        card_every_7_day.setOnClickListener {
            txt_everday.setTextColor(resources.getColor(color.black))
            card_everday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_3_day.setTextColor(resources.getColor(color.black))
            card_every_3_day.setCardBackgroundColor(resources.getColor(color.white))

            txt_alternate.setTextColor(resources.getColor(color.black))
            card_alternateday.setCardBackgroundColor(resources.getColor(color.white))

            txt_every_7_day.setTextColor(resources.getColor(color.white))
            card_every_7_day.setCardBackgroundColor(resources.getColor(color.green1))


            val dates = ArrayList<Date>()
            try {
                val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
                dates.add(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            var i = 0
            while (i < 18) {
                val c = Calendar.getInstance()
                c.time = dates[dates.size - 1]
                //Incrementing the date by 1 day
                c.add(Calendar.DAY_OF_MONTH, 7)
                val newDate = sdf.format(c.time)
                var d: Date? = null
                try {
                    d = sdf.parse(newDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                dates.add(d!!)
                calendar.init(Date(), nextYear.time) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                    .withSelectedDates(dates)
                disableView(line_cal)
                calendar.setCellClickInterceptor(null)
                i = i + 7
            }
        }

        edt_delivery_add.setOnClickListener { openPlaceAutoCompleteViewSource() }
        txt_subscribe_ok.setOnClickListener { }
        txt_subscri_date.setOnClickListener {
            val dd = DatePickerDialog(
                this@Add_subscription_Activity2, R.style.DialogTheme, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dd.datePicker.minDate = System.currentTimeMillis() - 1000
            dd.show()
        }

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
        }

    }

    public override fun onStart() {
        super.onStart()

        mGoogleApiClient.connect()
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
                edt_delivery_add.setText(" $Address_curent_location")
            }
        }
    }

    protected fun checkForLocationPermissions():Boolean {
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


    }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_subscri_date.text = sdf.format(myCalendar.time)
        temp_data.subsription_item_list[0].item_subscri_date = sdf.format(myCalendar.time)
        try {
            val d = sdf.parse(temp_data.subsription_item_list[0].item_subscri_date)
            calendar.init(d, nextYear.time)
                .withSelectedDate(d).inMode(CalendarPickerView.SelectionMode.SINGLE)
            disableView(line_cal)
            calendar.setCellClickInterceptor(null)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

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
}
