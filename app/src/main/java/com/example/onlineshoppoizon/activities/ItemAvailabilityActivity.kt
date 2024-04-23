package com.example.onlineshoppoizon.activities

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
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
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.ItemAvailabilityViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider


class ItemAvailabilityActivity : BaseActivity<ItemAvailabilityViewModel, ActivityItemAvailabilityBinding, ItemAvailabilityRepository>() {
    private lateinit var adapter : ItemAvailabilityAdapter
    private lateinit var bind : MapPopupBinding
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitInitializer.initialize(Const.MAPKIT_API_KEY, this)
        bind = MapPopupBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        dialog.setContentView(bind.root)

        val getUserToken = userPreferences.getToken().asLiveData()

        getUserToken.observe(this){ userToken ->
            token = userToken
            val intent = intent
            val selectedColor = intent.getIntExtra("selectedColor", 0)
            val selectedSize = intent.getIntExtra("selectedSize", 0)
            viewModel.getItemAvailability("Bearer $token", selectedSize.toLong(), selectedColor.toLong())
        }

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
                            bind.map.map.move(CameraPosition(Point(list[position].shopAddressesGarnish.latitude.toDouble(), list[position].shopAddressesGarnish.longitude.toDouble()),16.5f, 0.0f, 0.0f))
                            bind.map.map.mapObjects.addPlacemark(Point(list[position].shopAddressesGarnish.latitude.toDouble(), list[position].shopAddressesGarnish.longitude.toDouble()), ImageProvider.fromBitmap(createPointIcon()))
                        }
                    })
                }

                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

                }
            }
        }
        binding.back.setOnClickListener {
            finishActivity()
        }
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
    private fun createPointIcon(): Bitmap {

        return Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.xclogo
            ), 70, 70, true
        )
    }
}