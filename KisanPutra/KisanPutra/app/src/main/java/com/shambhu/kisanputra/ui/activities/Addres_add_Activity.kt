package com.shambhu.kisanputra.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.models.response.defaultAddress.SetDefaultAddress
import com.shambhu.kisanputra.data.models.response.detail_Add_address.DetailAddAddress
import com.shambhu.kisanputra.data.models.response.detail_address.Datum
import com.shambhu.kisanputra.data.models.response.detail_address.DetailAddress
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.ui.adapters.Address_item_Adapter
import com.shambhu.kisanputra.utils.StaticData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class Addres_add_Activity : AppCompatActivity() {
    lateinit var toolbar_title: TextView
    lateinit var toolbar_main: Toolbar
    lateinit var recycler_address: RecyclerView
    internal lateinit var address_item_Adapter: Address_item_Adapter
    lateinit var continue_btn: Button
    var isSetDefault: Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addres_add)

        init()
        getaddressdata()
    }

    fun init() {
        toolbar_main = findViewById<View>(R.id.toolbar_main) as Toolbar
        setSupportActionBar(toolbar_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar_title = findViewById<View>(R.id.toolbar_title) as TextView
        toolbar_title.text = "Saved Address"

        continue_btn = findViewById<View>(R.id.continue_btn) as Button

        recycler_address = findViewById<View>(R.id.recycler_address) as RecyclerView
        recycler_address.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        continue_btn.setOnClickListener(View.OnClickListener
        {
            if (isSetDefault==true){
                var intent: Intent = Intent(this, HomeActivity::class.java)
                // intent.putExtra("userModel",userModel)
                startActivity(intent)
            }else{
                Toast.makeText(this@Addres_add_Activity, "First select address as default.", Toast.LENGTH_LONG).show()
            }


        })


    }

    fun getaddressdata() {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call = apiService.getAllAddresses(StaticData.TOKENTYPE + mPrefs.prefAuthToken)
        call.enqueue(object : Callback<DetailAddress> {
            override fun onResponse(
                call: Call<DetailAddress>,
                response: Response<DetailAddress>
            ) {
                val statusCode = response.code()
                val list = response.body()!!.data

                val token = StaticData.TOKENTYPE + mPrefs.prefAuthToken

                address_item_Adapter = Address_item_Adapter(this@Addres_add_Activity, list, token,object:Address_item_Adapter.AddressClickedListener{
                    override fun addDefaultAddress(datum: Datum?, position: Int) {
                        setDefaultAddress(position.toString())
                        Toast.makeText(this@Addres_add_Activity, "set Default btn click", Toast.LENGTH_LONG).show()
                    }

                    override fun deleteAddress(datum: Datum?, position: Int) {
                        Toast.makeText(this@Addres_add_Activity, "delete btn click", Toast.LENGTH_LONG).show()
                    }

                })
                recycler_address.adapter = address_item_Adapter

            }

            override fun onFailure(call: Call<DetailAddress>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(this@Addres_add_Activity, "error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setDefaultAddress(address_id:String) {
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)

        val params = HashMap<String, String>()
        params["address_id"] = address_id

        val call = apiService.setDefaultAddress(StaticData.TOKENTYPE+ mPrefs.prefAuthToken,params)
        call.enqueue(object : Callback<SetDefaultAddress> {
            override fun onResponse(
                call: Call<SetDefaultAddress>,
                response: Response<SetDefaultAddress>
            ) {
                val statusCode = response.code()

                val status = response.body()!!.status
                if(status==true)
                {
                    isSetDefault=true
                    Toast.makeText(this@Addres_add_Activity, "Address set default successfully", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@Addres_add_Activity, response.body()!!.message , Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SetDefaultAddress>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(this@Addres_add_Activity,"error",Toast.LENGTH_LONG).show()

//                    startActivity(Intent(this@Detail_Address_Activity,Addres_add_Activity::class.java))
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
