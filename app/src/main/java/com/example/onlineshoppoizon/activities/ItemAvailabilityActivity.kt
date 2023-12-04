package com.example.onlineshoppoizon.activities

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.adapters.ItemAvailabilityAdapter
import com.example.onlineshoppoizon.databinding.ActivityItemAvailabilityBinding
import com.example.onlineshoppoizon.databinding.MapPopupBinding
import com.example.onlineshoppoizon.model.ShopGarnish
import com.example.onlineshoppoizon.repository.ItemAvailabilityRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.MapKitInitializer
import com.example.onlineshoppoizon.viewmodel.ItemAvailabilityViewModel
import com.yandex.mapkit.MapKitFactory


class ItemAvailabilityActivity : BaseActivity<ItemAvailabilityViewModel, ActivityItemAvailabilityBinding, ItemAvailabilityRepository>() {
    private lateinit var adapter : ItemAvailabilityAdapter
    private lateinit var bind : MapPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitInitializer.initialize(Const.MAPKIT_API_KEY, this)
        bind = MapPopupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bind.root)

        val intent = intent
        val selectedColor = intent.getIntExtra("selectedColor", 0)
        val selectedSize = intent.getIntExtra("selectedSize", 0)
        viewModel.getItemAvailability(selectedColor.toLong(), selectedSize.toLong())
        viewModel.itemAvailabilityResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val list: MutableList<ShopGarnish> = ArrayList()

                    list.addAll(it.value)

                    Toast.makeText(applicationContext, list.size.toString(), Toast.LENGTH_SHORT)
                        .show()
                    binding.availabilityRecycler.layoutManager = LinearLayoutManager(this)
                    adapter = ItemAvailabilityAdapter(list)
                    binding.availabilityRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        ItemAvailabilityAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            dialog.show()
                        }
                    })
                }

                is Resource.Failure -> {

                }
            }
        }
    }

    //TODO()
    private fun showShop(){

    }

    override fun getViewModel(): Class<ItemAvailabilityViewModel> =
        ItemAvailabilityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityItemAvailabilityBinding =
        ActivityItemAvailabilityBinding.inflate(inflater)

    override fun getActivityRepository(): ItemAvailabilityRepository =
        ItemAvailabilityRepository(requestBuilder.buildRequest(ApiInterface::class.java))

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        bind.map.onStart()
    }

    override fun onStop() {
        bind.map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}