package com.shambhu.kisanputra.ui.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.shambhu.kisanputra.KisanPutraApp;
import com.shambhu.kisanputra.R;
import com.shambhu.kisanputra.data.temp.temp_data;
import com.squareup.timessquare.CalendarPickerView;

import static com.shambhu.kisanputra.KisanPutraApp.mPrefs;
import static com.shambhu.kisanputra.R.color;
import static com.shambhu.kisanputra.R.id;

public class Add_subscription_Activity extends AppCompatActivity /*implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/{
    Toolbar toolbar;
    ImageView img_imtem_pic;
    TextView txt_item_name, txt_item_qnty, txt_item_prise,txt_count_item;
    ImageView img_dic_item, img_incre_item;
    TextView txt_subscri_date;
    CardView card_everday, card_every_3_day, card_alternateday, card_every_7_day;
    TextView txt_everday, txt_every_3_day, txt_alternate, txt_every_7_day;
//    CalendarView calender;
    CalendarPickerView calendar;
    EditText edt_delivery_add;
    TextView txt_subscribe_ok;
//    LocationRequest mLocationRequest;
//    GoogleApiClient mGoogleApiClient;
//    Location mCurrentLocation;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    protected boolean hasLocationPermissions;
    protected boolean hasEdited = false;
    protected static final int REQUEST_PERMISSIONS_LOCATION = 2;
    Calendar myCalendar;
    Calendar nextYear = Calendar.getInstance();
    SimpleDateFormat sdf;
    LinearLayout line_cal;

    protected void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(INTERVAL);
//        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);
        toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        createLocationRequest();

        myCalendar = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 2);
        sdf = new SimpleDateFormat("dd/MM/yyyy");


       /* if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions())
                getLocationPermissions();
        }*/

//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();

        init();
        listener();
    }


    private void init() {
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


       String img_url= temp_data.subsription_item_list.get(0).getImg_url();
       String item_name= temp_data.subsription_item_list.get(0).getItem_name();
       String item_prise= temp_data.subsription_item_list.get(0).getItem_prise();
       String item_qnty= temp_data.subsription_item_list.get(0).getItem_qnty();
       String item_count= temp_data.subsription_item_list.get(0).getItem_count();
       String item_subscrip_date= temp_data.subsription_item_list.get(0).getItem_subscri_date();


        txt_item_name = findViewById(id.txt_item_name);
        txt_item_qnty = findViewById(id.txt_item_qnty);
        txt_item_prise = findViewById(id.txt_item_prise);
        txt_count_item = findViewById(id.txt_count_item);

        img_dic_item = findViewById(id.img_dic_item);
        img_imtem_pic = findViewById(id.img_imtem_pic);
        img_incre_item = findViewById(id.img_incre_item);

        txt_subscri_date = findViewById(id.txt_subscri_date);

        card_everday = findViewById(id.card_everday);
        card_every_3_day = findViewById(id.card_every_3_day);
        card_alternateday = findViewById(id.card_alternateday);
        card_every_7_day = findViewById(id.card_every_7_day);

        txt_everday = findViewById(id.txt_everday);
        txt_every_3_day = findViewById(id.txt_every_3_day);
        txt_alternate = findViewById(id.txt_alternate);
        txt_every_7_day = findViewById(id.txt_every_7_day);
//        calender = findViewById(id.calender);
        line_cal=findViewById(R.id.line_cal);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        try {
            Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
            calendar.init(d, nextYear.getTime())
                    .withSelectedDate(d).inMode(CalendarPickerView.SelectionMode.SINGLE);;

            disableView(line_cal);
            calendar.setCellClickInterceptor(null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date today = new Date();


        edt_delivery_add = findViewById(id.edt_delivery_add);
        txt_subscribe_ok = findViewById(id.txt_subscribe_ok);

        edt_delivery_add.setText(mPrefs.getPrefUserDaultLocation().getAddress());
        Glide.with(this).load(img_url).into(img_imtem_pic);
        txt_item_name.setText(item_name);
        txt_item_qnty.setText(item_qnty);
        txt_item_prise.setText(item_prise);
        txt_subscri_date.setText(item_subscrip_date);
        txt_count_item.setText(item_count);

    }
    public static void disableView(View v) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                disableView(child);
            }
        }
    }

    private void listener() {

//        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
//                String date = dayOfMonth + "/" + month + "/"+ year ;
//                Toast.makeText(Add_subscription_Activity.this, date, Toast.LENGTH_SHORT).show();
//
//            }
//        });

        img_incre_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int qty= Integer.parseInt(temp_data.subsription_item_list.get(0).getItem_count());
                qty=qty+1;
                temp_data.subsription_item_list.get(0).setItem_count(String.valueOf(qty));
                txt_count_item.setText(String.valueOf(qty));

               int prise= Integer.parseInt(temp_data.subsription_item_list.get(0).getItem_prise());
                prise=prise*qty;
//                temp_data.subsription_item_list.get(0).setItem_prise(String.valueOf(prise));
                txt_item_prise.setText(String.valueOf(prise));
            }
        });
        img_dic_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int qty= Integer.parseInt(temp_data.subsription_item_list.get(0).getItem_count());

                int prise= Integer.parseInt(txt_item_prise.getText().toString());
                prise=prise-Integer.parseInt(temp_data.subsription_item_list.get(0).getItem_prise());

                qty=qty-1;
                temp_data.subsription_item_list.get(0).setItem_count(String.valueOf(qty));
                txt_count_item.setText(String.valueOf(qty));


//                temp_data.subsription_item_list.get(0).setItem_prise(String.valueOf(prise));
                txt_item_prise.setText(String.valueOf(prise));
            }
        });


        card_everday.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txt_everday.setTextColor(getResources().getColor(R.color.white));
                card_everday.setCardBackgroundColor(getResources().getColor(color.green1));

                txt_every_3_day.setTextColor(getResources().getColor(color.black));
                card_every_3_day.setCardBackgroundColor(getResources().getColor(color.white));

                txt_alternate.setTextColor(getResources().getColor(color.black));
                card_alternateday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_7_day.setTextColor(getResources().getColor(color.black));
                card_every_7_day.setCardBackgroundColor(getResources().getColor(color.white));

                ArrayList<Date> dates = new ArrayList<Date>();
                try {
                    Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
                    dates.add(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int i=0;i<18;i++)
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dates.get(i));
                    //Incrementing the date by 1 day
                    c.add(Calendar.DAY_OF_MONTH, 1);
                    String newDate = sdf.format(c.getTime());
                    Date d = null;
                    try {
                        d = sdf.parse(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dates.add(d);
                    if(i==17)
                    {
                        calendar.init(new Date(), nextYear.getTime()) //
                                .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                                .withSelectedDates(dates);
                        disableView(line_cal);
                        calendar.setCellClickInterceptor(null);
                    }
                }

            }
        });
        card_every_3_day.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txt_everday.setTextColor(getResources().getColor(R.color.black));
                card_everday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_3_day.setTextColor(getResources().getColor(color.white));
                card_every_3_day.setCardBackgroundColor(getResources().getColor(color.green1));

                txt_alternate.setTextColor(getResources().getColor(color.black));
                card_alternateday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_7_day.setTextColor(getResources().getColor(color.black));
                card_every_7_day.setCardBackgroundColor(getResources().getColor(color.white));


                ArrayList<Date> dates = new ArrayList<Date>();
                try {
                    Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
                    dates.add(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int i=0;i<18;i=i+3)
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dates.get(dates.size()-1));
                    //Incrementing the date by 1 day
                    c.add(Calendar.DAY_OF_MONTH, 3);
                    String newDate = sdf.format(c.getTime());
                    Date d = null;
                    try {
                        d = sdf.parse(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dates.add(d);
                    calendar.init(new Date(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                            .withSelectedDates(dates);
                    disableView(line_cal);
                    calendar.setCellClickInterceptor(null);
                }

            }
        });
        card_alternateday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_everday.setTextColor(getResources().getColor(R.color.black));
                card_everday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_3_day.setTextColor(getResources().getColor(color.black));
                card_every_3_day.setCardBackgroundColor(getResources().getColor(color.white));

                txt_alternate.setTextColor(getResources().getColor(color.white));
                card_alternateday.setCardBackgroundColor(getResources().getColor(color.green1));

                txt_every_7_day.setTextColor(getResources().getColor(color.black));
                card_every_7_day.setCardBackgroundColor(getResources().getColor(color.white));

                ArrayList<Date> dates = new ArrayList<Date>();
                try {
                    Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
                    dates.add(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int i=0;i<18;i=i+2)
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dates.get(dates.size()-1));
                    //Incrementing the date by 1 day
                    c.add(Calendar.DAY_OF_MONTH, 2);
                    String newDate = sdf.format(c.getTime());
                    Date d = null;
                    try {
                        d = sdf.parse(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dates.add(d);
                    calendar.init(new Date(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                            .withSelectedDates(dates);
                    disableView(line_cal);
                    calendar.setCellClickInterceptor(null);
                }



            }
        });
        card_every_7_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                txt_everday.setTextColor(getResources().getColor(R.color.black));
                card_everday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_3_day.setTextColor(getResources().getColor(color.black));
                card_every_3_day.setCardBackgroundColor(getResources().getColor(color.white));

                txt_alternate.setTextColor(getResources().getColor(color.black));
                card_alternateday.setCardBackgroundColor(getResources().getColor(color.white));

                txt_every_7_day.setTextColor(getResources().getColor(color.white));
                card_every_7_day.setCardBackgroundColor(getResources().getColor(color.green1));


                ArrayList<Date> dates = new ArrayList<Date>();
                try {
                    Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
                    dates.add(d);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                for(int i=0;i<18;i=i+7)
                {
                    Calendar c = Calendar.getInstance();
                    c.setTime(dates.get(dates.size()-1));
                    //Incrementing the date by 1 day
                    c.add(Calendar.DAY_OF_MONTH, 7);
                    String newDate = sdf.format(c.getTime());
                    Date d = null;
                    try {
                        d = sdf.parse(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dates.add(d);
                    calendar.init(new Date(), nextYear.getTime()) //
                            .inMode(CalendarPickerView.SelectionMode.MULTIPLE) //
                            .withSelectedDates(dates);
                    disableView(line_cal);
                    calendar.setCellClickInterceptor(null);
                }
            }
        });

        edt_delivery_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
//                openPlaceAutoCompleteViewSource();
                Intent intent =new Intent(Add_subscription_Activity.this, Detail_Address_Activity.class);
                startActivity(intent);
            }
        });
        txt_subscribe_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
        txt_subscri_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dd = new DatePickerDialog(Add_subscription_Activity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dd.show();
            }
        });

    }
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }

    public void openPlaceAutoCompleteViewSource() {


        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));

//        PlacesClient placesClient = Places.createClient(this);

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN")
                .build(this);
        startActivityForResult(intent, AppConstants.INSTANCE.getSEARCH_SOURCE());


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.INSTANCE.getSEARCH_SOURCE() && resultCode == RESULT_OK) {

            Place place = Autocomplete.getPlaceFromIntent(data);
            hasEdited = true;
            edt_delivery_add.setText(place.getAddress().toString());
        }

    }

    @Override
    public void onStart() {
        super.onStart();

//        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
//        mGoogleApiClient.disconnect();
    }


    /*@Override
    public void onConnected(Bundle bundle) {

        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationChanged(Location location) {
        if (!hasEdited) {
            mCurrentLocation = location;
            if (null != mCurrentLocation) {
                String lat = String.valueOf(mCurrentLocation.getLatitude());
                String lng = String.valueOf(mCurrentLocation.getLongitude());
                String Address_curent_location = getCompleteAddressString(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//                edt_delivery_add.setText(" " + Address_curent_location);
            }
        }
    }

    protected boolean checkForLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                *//*String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);*//*
                return hasLocationPermissions = false;
            } else {
                return hasLocationPermissions = true;
            }
        } else {
            return hasLocationPermissions = true;
        }
    }

    protected void getLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,};
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS_LOCATION) {
            if (grantResults.length == 2) {
                hasLocationPermissions = grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            }
        }


    }*/
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_subscri_date.setText(sdf.format(myCalendar.getTime()));
        temp_data.subsription_item_list.get(0).setItem_subscri_date(sdf.format(myCalendar.getTime()));
        try {
            Date d = sdf.parse(temp_data.subsription_item_list.get(0).getItem_subscri_date());
            calendar.init(d, nextYear.getTime())
                    .withSelectedDate(d).inMode(CalendarPickerView.SelectionMode.SINGLE);
            disableView(line_cal);
            calendar.setCellClickInterceptor(null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
           finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
