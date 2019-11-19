package com.shambhu.kisanputra.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.shambhu.kisanputra.R
import com.shambhu.kisanputra.base.BaseFragment
import com.shambhu.kisanputra.data.models.Cart_item_Model
import com.shambhu.kisanputra.data.models.response.HomeProductResponse
import com.shambhu.kisanputra.data.rest_api.interceptor.ConnectivityStatus
import com.shambhu.kisanputra.data.temp.temp_data
import com.shambhu.kisanputra.mPrefs
import com.shambhu.kisanputra.ui.activities.Add_subscription_Activity
import com.shambhu.kisanputra.ui.activities.Detail_product_Activity
import com.shambhu.kisanputra.ui.adapters.GenericListAdapter
import com.shambhu.kisanputra.ui.adapters.SliderViewPagerAdapter
import com.shambhu.kisanputra.ui.adapters.essential_item_Adapter
import com.shambhu.kisanputra.utils.StaticData
import com.shambhu.kisanputra.viewmodels.HomeViewModel
import com.shambhu.kisanputra.viewmodels.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.frag_home.*
import java.util.ArrayList
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    lateinit var homeviewmodelFactory: ViewModelFactory
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel = ViewModelProviders.of(this, homeviewmodelFactory).get(HomeViewModel::class.java)
        hideKeyBoard()
        fetchProducts()
        return inflater.inflate(R.layout.frag_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onDetach() {
        super.onDetach()
    }


    private fun fetchProducts() {

        if (!ConnectivityStatus.isConnected(activity)) {
            showNetworkIssue()
            return
        }

        homeViewModel.getProductlists(StaticData.TOKENTYPE+ mPrefs.prefAuthToken)

        homeViewModel.loading.observe(this, Observer { isLoading ->
            handleProgressLoader(isLoading)
        })
        homeViewModel.dataLoadError.observe(this, Observer { error ->
            handleError(error)
        })
        homeViewModel.getProductDetails().observe(this, Observer { resource ->
            setSlider(resource!!.slider as ArrayList<HomeProductResponse.Slider>)
            setProductsSales(resource!!.product as ArrayList<HomeProductResponse.Product>)
            setEssentials(resource!!.others.essential.data as ArrayList<HomeProductResponse.Others.Essential.EssentialData>)
            setCategories(resource!!.categories as ArrayList<HomeProductResponse.Category>)
        })
    }

    fun setSlider(imageArray : ArrayList<HomeProductResponse.Slider>){
        if (imageArray.isEmpty())
            homeSlider.visibility=View.GONE
        else
            homeSlider.visibility=View.VISIBLE
            // val imageList : ArrayList<String> = arrayListOf()
            //  imageArray.forEach{imageList.add(it.image)}

            if (imageArray.size > 1) {
                homeSlider.clipToPadding = false
                homeSlider.pageMargin = 10
                homeSlider.setPadding(50, 0, 50, 0)
                homeSlider.offscreenPageLimit = 3
            }
            homeSlider.adapter = SliderViewPagerAdapter(view!!.context, imageArray)

    }

    fun setProductsSales(list : ArrayList<HomeProductResponse.Product>){
        if (list.isEmpty())
            product_layout.visibility=View.GONE
        else
            product_layout.visibility=View.VISIBLE
        product_recycler.adapter= GenericListAdapter<HomeProductResponse.Product>(

            list,R.layout.flashsaledata,
            object : GenericListAdapter.OnListItemClickListener<HomeProductResponse.Product>{
                override fun onListItemClicked(selItem: HomeProductResponse.Product, extra: Any?) {
//                    showToast(selItem!!.name)
                    val intent = Intent(activity, Detail_product_Activity::class.java).putExtra("id",selItem.catid)

                    activity.startActivity(intent)
                }
            }
        )
    }

    fun setEssentials(list : ArrayList<HomeProductResponse.Others.Essential.EssentialData>){
        if (list.isEmpty())
            esential_layout.visibility=View.GONE
        else
            esential_layout.visibility=View.VISIBLE
//        esential_recycler.adapter= GenericListAdapter<HomeProductResponse.Others.Essential.EssentialData>(
//            list,R.layout.essentail,
//            object : GenericListAdapter.OnListItemClickListener<HomeProductResponse.Others.Essential.EssentialData>{
//                override fun onListItemClicked(selItem: HomeProductResponse.Others.Essential.EssentialData, extra: Any?)
//                {
//                    temp_data.Cart_item_list.add(
//                        Cart_item_Model(selItem!!.name,
//                            " " + selItem!!.price, "" + selItem!!.image[0].url,
//                            1
//                        )
//                    )
//
//                    Toast.makeText(activity, "Item added to cart", Toast.LENGTH_SHORT).show()
//                    showToast(selItem!!.name)
//                }
//            }
//        )
        esential_recycler.adapter= essential_item_Adapter(getActivity(),list,mPrefs.prefAuthToken)
    }

    fun setCategories(list : ArrayList<HomeProductResponse.Category>){

        if (list.isEmpty())
            categories_layout.visibility=View.GONE
        else
            categories_layout.visibility=View.VISIBLE
        category_recycler.adapter= GenericListAdapter<HomeProductResponse.Category>(
            list,R.layout.category,
            object : GenericListAdapter.OnListItemClickListener<HomeProductResponse.Category>{
                override fun onListItemClicked(selItem: HomeProductResponse.Category, extra: Any?) {
                    showToast(selItem!!.name)
                }
            }
        )
    }

}
