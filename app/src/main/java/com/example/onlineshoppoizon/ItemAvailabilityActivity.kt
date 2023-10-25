package com.example.onlineshoppoizon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.adapters.ItemAvailabilityAdapter
import com.example.onlineshoppoizon.databinding.ActivityItemAvailabilityBinding
import com.example.onlineshoppoizon.databinding.ActivityItemDetailsBinding
import com.example.onlineshoppoizon.model.ShopGarnish
import com.example.onlineshoppoizon.repository.ItemAvailabilityRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.ItemAvailabilityViewModel

class ItemAvailabilityActivity : BaseActivity<ItemAvailabilityViewModel, ActivityItemAvailabilityBinding, ItemAvailabilityRepository>() {
    private lateinit var adapter : ItemAvailabilityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val selectedColor = intent.getIntExtra("selectedColor", 0)
        val selectedSize = intent.getIntExtra("selectedSize", 0)
        viewModel.getItemAvailability()
        viewModel.itemAvailabilityResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    val list : MutableList<ShopGarnish> = ArrayList()
                    for(i in it.value){
                        if(it.value[i.shopGarnishId.toInt() - 1].colorClothes.id == selectedColor.toLong()
                            && it.value[i.shopGarnishId.toInt() - 1].sizeClothes.id == selectedSize){
                            list.add(ShopGarnish(it.value[i.shopGarnishId.toInt() - 1].shopGarnishId,
                                it.value[i.shopGarnishId.toInt() - 1].colorClothes,
                                it.value[i.shopGarnishId.toInt() - 1].sizeClothes,
                                it.value[i.shopGarnishId.toInt() - 1].shopAddresses,
                                it.value[i.shopGarnishId.toInt() - 1].quantity))
                        }
                    }
                    Toast.makeText(applicationContext, list.size.toString(), Toast.LENGTH_SHORT).show()
                    binding.availabilityRecycler.layoutManager = LinearLayoutManager(this)
                    adapter = ItemAvailabilityAdapter(list)
                    binding.availabilityRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object : ItemAvailabilityAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            TODO("Not yet implemented")
                        }

                    })

                }
                is Resource.Failure -> {

                }
            }
        })

    }

    override fun getViewModel(): Class<ItemAvailabilityViewModel> =
        ItemAvailabilityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityItemAvailabilityBinding =
        ActivityItemAvailabilityBinding.inflate(inflater)

    override fun getActivityRepository(): ItemAvailabilityRepository =
        ItemAvailabilityRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}