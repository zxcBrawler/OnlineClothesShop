package com.example.onlineshoppoizon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.onlineshoppoizon.databinding.ActivityItemDetailsBinding
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.ItemDetailsViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class ItemDetailsActivity : BaseActivity<ItemDetailsViewModel, ActivityItemDetailsBinding, ItemDetailsRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this@ItemDetailsActivity, R.color.grey_white)
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        viewModel.getClothesById(id)
        viewModel.clothesByIdResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    //TODO(finish colors and sizes)
                    binding.productName.text = it.value.nameClothes
                    binding.productBarcode.text = it.value.barcode
                    binding.productPrice.text = it.value.priceClothes + " p"
                }
                is Resource.Failure -> {
                    //TODO
                    Toast.makeText(this, it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getPhotos()
        viewModel.clothesPhotoResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    binding.carousel.registerLifecycle(lifecycle)
                    val list : MutableList<CarouselItem> = ArrayList()
                    for(i in it.value){
                        if(it.value[i.id.toInt() - 1].clothes.idClothes == id){
                            list.add(CarouselItem(it.value[i.id.toInt() - 1].clothesPhoto.photoAddress))
                        }
                    }
                    binding.carousel.setData(list)
                }
                is Resource.Failure -> {
                    //TODO
                    Toast.makeText(this, it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.getClothesColors()
        viewModel.clothesColorsResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {

                }
                is Resource.Failure -> {
                }
            }
        })
        viewModel.getClothesSizes()
        viewModel.clothesSizesResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {

                }
                is Resource.Failure -> {
                }
            }
        })
    }

    override fun getViewModel(): Class<ItemDetailsViewModel> =
        ItemDetailsViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityItemDetailsBinding
     = ActivityItemDetailsBinding.inflate(inflater)

    override fun getActivityRepository(): ItemDetailsRepository
    = ItemDetailsRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}