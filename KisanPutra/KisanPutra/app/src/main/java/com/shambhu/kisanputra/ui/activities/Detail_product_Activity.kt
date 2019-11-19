package com.shambhu.kisanputra.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.data.models.about_model
import com.shambhu.kisanputra.data.models.response.detail_product.DetailProduct
import com.shambhu.kisanputra.data.models.response.detail_product.Image
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.ui.adapters.about_item_detail_Adapter
import com.shambhu.kisanputra.ui.adapters.about_item_img_Adapter
import com.shambhu.kisanputra.utils.LinePagerIndicatorDecoration
import com.shambhu.kisanputra.utils.StaticData
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class Detail_product_Activity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var recycler_item_img: RecyclerView
    lateinit var recycler_item_about: RecyclerView
    lateinit var txt_name: TextView
    lateinit var txt_qnty: TextView
    lateinit var txt_sale_prise: TextView
    lateinit var txt_mrp: TextView
    lateinit var txt_sale_off_msg: TextView
    lateinit var btn_add_to_cart: Button
    lateinit var about_item_img_adapter: about_item_img_Adapter
    lateinit var aboutItemDetailAdapter: about_item_detail_Adapter


    lateinit var img_list: List<Image>
    lateinit var about_produst: ArrayList<about_model>
    lateinit var item_name: String
    lateinit var item_qnty: String
    lateinit var messurment: String
    lateinit var item_mrp: String
    lateinit var item_sale_prise: String
    lateinit var iten_sale_msg: String
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id")
        }
        init()
        settempdata()

    }

    private fun settempdata() {

        val apiService = ApiClient.getClient().create(ApiInterface::class.java)

        val call2 = apiService.detailproduct(StaticData.TOKENTYPE + mPrefs.prefAuthToken, Integer.valueOf(id))
        call2.enqueue(object : Callback<DetailProduct> {

            override fun onResponse(
                call: Call<DetailProduct>,
                response: retrofit2.Response<DetailProduct>
            ) {
                //                        String msg = response.body().getMessage();
                val data = response.body()!!.data

                val status = response.body()!!.status
                if (status!!) {
                    item_name = data.name
                    item_qnty = data.qty.toString()
                    item_sale_prise = data.price.toString()
                    item_mrp = data.price.toString()
                    messurment = data.messurment.toString()
                    iten_sale_msg = "19% Off"

                    img_list = data.image

                    //                img_list.add("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/homeslider/grains.jpg");
                    //                img_list.add("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/products/product_gallery/grains.jpg");
                    //                img_list.add("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/homeslider/grains.jpg");
                    //                img_list.add("http://ec2-54-89-26-9.compute-1.amazonaws.com/uploads/products/product_gallery/grains.jpg");

                    about_produst = ArrayList()
                    about_produst.add(about_model(false, data.description, "", ""))

                            listener()
                }


            }

            override fun onFailure(call: Call<DetailProduct>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(this@Detail_product_Activity, "Fail", Toast.LENGTH_SHORT).show()

            }
        })


    }

    private fun listener() {
        recycler_item_img.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        about_item_img_adapter = about_item_img_Adapter(this, img_list)
        recycler_item_img.adapter = about_item_img_adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recycler_item_img)
        recycler_item_img.addItemDecoration(LinePagerIndicatorDecoration())

        recycler_item_about.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        aboutItemDetailAdapter = about_item_detail_Adapter(this, about_produst)
        recycler_item_about.adapter = aboutItemDetailAdapter


        btn_add_to_cart.setOnClickListener {
            Toast.makeText(
                this@Detail_product_Activity,
                "Add to cart click",
                Toast.LENGTH_SHORT
            ).show()
        }

        txt_name.setText(item_name)
        txt_qnty.setText(item_qnty+" "+messurment)
        txt_sale_prise.setText(item_sale_prise)
        txt_mrp.setText(item_mrp)

    }

    private fun init() {
        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recycler_item_img = findViewById(R.id.recycler_item_img)
        recycler_item_about = findViewById(R.id.recycler_item_about)

        txt_name = findViewById(R.id.txt_name)
        txt_qnty = findViewById(R.id.txt_qnty)
        txt_sale_prise = findViewById(R.id.txt_sale_prise)
        txt_mrp = findViewById(R.id.txt_mrp)
        txt_sale_off_msg = findViewById(R.id.txt_sale_off_msg)

        btn_add_to_cart = findViewById(R.id.btn_add_to_cart)
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
}
