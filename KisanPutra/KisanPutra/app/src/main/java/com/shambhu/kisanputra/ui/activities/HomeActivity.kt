package com.shambhu.kisanputra.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseActivity
import com.shambhu.kisanputra.data.models.CommonStringIntegerModel
import com.shambhu.kisanputra.data.models.response.defaultAddress.GetDefaultAddress
import com.shambhu.kisanputra.data.rest_api.apis.ApiClient
import com.shambhu.kisanputra.data.rest_api.apis.ApiInterface
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.ui.adapters.GenericListAdapter
import com.shambhu.kisanputra.ui.fragments.HomeFragment
import com.shambhu.kisanputra.ui.fragments.My_wallet_Fragment
import com.shambhu.kisanputra.utils.StaticData
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigation
import com.tenclouds.fluidbottomnavigation.FluidBottomNavigationItem
import com.tenclouds.fluidbottomnavigation.listener.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeActivity : BaseActivity() {

    // private val TAG = "" + this::class.java.getSimpleName()
    lateinit var fluidBottomNavigation: FluidBottomNavigation
    val items = ArrayList<FluidBottomNavigationItem>()
    val homeFragment: HomeFragment = HomeFragment()
    val walletFrag: My_wallet_Fragment = My_wallet_Fragment()

    private val navItemList: ArrayList<CommonStringIntegerModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar_main)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        checkLocation()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar_main,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)

        fluidBottomNavigation = findViewById(R.id.fluidBottomNavigation) as FluidBottomNavigation
        /* fluidBottomNavigation.accentColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
         fluidBottomNavigation.backColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
         fluidBottomNavigation.textColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
         fluidBottomNavigation.iconColor = ContextCompat.getColor(this, R.color.colorPrimary)
         fluidBottomNavigation.iconSelectedColor = ContextCompat.getColor(this, R.color.iconSelectedColor)*/

        items.add(
            FluidBottomNavigationItem(
                getString(R.string.nav_schedule),
                ContextCompat.getDrawable(this, R.drawable.nav_schedule)
            )
        )
        items.add(
            FluidBottomNavigationItem(
                getString(R.string.nav_wattel),
                ContextCompat.getDrawable(this, R.drawable.nav_wallet)
            )
        )
        items.add(
            FluidBottomNavigationItem(
                getString(R.string.nav_home),
                ContextCompat.getDrawable(this, R.drawable.nav_home)
            )
        )
        items.add(
            FluidBottomNavigationItem(
                getString(R.string.nav_plans),
                ContextCompat.getDrawable(this, R.drawable.nav_plan)
            )
        )
        items.add(
            FluidBottomNavigationItem(
                getString(R.string.nav_support),
                ContextCompat.getDrawable(this, R.drawable.nav_support)
            )
        )

        fluidBottomNavigation.items = items


        fluidBottomNavigation.onTabSelectedListener = object : OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                when (items[position].title) {
                    getString(R.string.nav_schedule) -> {
                    }
                    getString(R.string.nav_wattel) -> {

//                        val walletFragment: My_wallet_Fragment = My_wallet_Fragment()
                        replaceFragment(R.id.homeContainer, walletFrag, false)
                    }
                    getString(R.string.nav_home) -> {
                        replaceFragment(R.id.homeContainer, homeFragment, false)
                    }
                    getString(R.string.nav_plans) -> {

                    }
                    getString(R.string.nav_support) -> {

                    }
                }
            }
        }

        updateUsernameOnDrawer()
        setUpNavigationItemList()

        findViewById<ImageView>(R.id.img_noti).setOnClickListener(View.OnClickListener
        {
            val intent = Intent(this, Notification_Activity::class.java)
            startActivity(intent)
        })
        findViewById<ImageView>(R.id.img_cart).setOnClickListener(View.OnClickListener
        {
            val intent = Intent(this, Add_to_Cart_Activity::class.java)
            startActivity(intent)
        })
        getDefaultAddress()
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
                if(status==true) {
                    mPrefs.prefUserDaultLocation= response.body()!!.data?.get(0)
                }
            }

            override fun onFailure(call: Call<GetDefaultAddress>, t: Throwable) {
                // Log error here since request failed
                Toast.makeText(this@HomeActivity,"error", Toast.LENGTH_LONG).show()

//                    startActivity(Intent(this@Detail_Address_Activity,Addres_add_Activity::class.java))
            }
        })
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
    }

    private fun setUpNavigationItemList() {

        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_subscription),
                R.drawable.nav_1
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_wallet),
                R.drawable.nav_2
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_billing_history),
                R.drawable.nav_3
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_contactus),
                R.drawable.nav_4
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_setting),
                R.drawable.nav_5
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_aboutus),
                R.drawable.nav_6
            )
        )
        navItemList.add(
            CommonStringIntegerModel(
                getString(R.string.nav_drawer_logout),
                R.drawable.nav_7
            )
        )

        //nav_recycler_view.addItemDecoration(DividerItemDecoration(getDrawable(R.drawable.grey)))
        nav_recycler_view.adapter = GenericListAdapter(navItemList, R.layout.menu_item,
            object : GenericListAdapter.OnListItemClickListener<CommonStringIntegerModel> {
                override fun onListItemClicked(selItem: CommonStringIntegerModel, extra: Any?) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    setNavigationFlow(selItem.str)
                }
            })
        openUpHomeScreen()

    }


    fun updateUsernameOnDrawer() {
        txt_username.text = mPrefs.prefUserDetails!!.name
    }


    private fun openUpHomeScreen() {
        // toolbar_title.text = getString(R.string.nav_home)
        fluidBottomNavigation.selectTab(2)
        replaceFragment(R.id.homeContainer, homeFragment, false)
    }

    private fun openUpwalletScreen() {
        fluidBottomNavigation.selectTab(1)
        replaceFragment(R.id.homeContainer, walletFrag, false)
    }

    private fun setNavigationFlow(screenName: String) {

        when (screenName) {
            getString(R.string.nav_drawer_subscription) -> {
                val intent = Intent(this, My_subscription_Activity::class.java)
                startActivity(intent)
            }
            getString(R.string.nav_drawer_wallet) -> {
                openUpwalletScreen()
            }
            getString(R.string.nav_drawer_billing_history) -> {
                //  openUpAppointmentsRequests()
            }
            getString(R.string.nav_drawer_contactus) -> {
                // openUpAppointmentsScreen()
            }

            getString(R.string.nav_drawer_setting) -> {
                //  openUpNotificationScreen()
            }
            getString(R.string.nav_drawer_aboutus) -> {
                // openUpSettingsScreen()
            }
            getString(R.string.nav_drawer_logout) -> {
                logOutFromApp()
            }
        }

    }


    override fun hitLogoutAPI() {
        super.hitLogoutAPI()

        mPrefs.clearUserDetails()
        restartApp()
    }
}
