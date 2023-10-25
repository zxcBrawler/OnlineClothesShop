package com.example.onlineshoppoizon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.adapters.ColorsAdapter
import com.example.onlineshoppoizon.adapters.SizesAdapter
import com.example.onlineshoppoizon.databinding.ActivityItemDetailsBinding
import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.Colors
import com.example.onlineshoppoizon.model.SizeClothes
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.ItemDetailsViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class ItemDetailsActivity : BaseActivity<ItemDetailsViewModel, ActivityItemDetailsBinding, ItemDetailsRepository>() {
    private lateinit var adapter : ColorsAdapter
    private lateinit var sizesAdapter: SizesAdapter
    var selectedColor : Int = 0
    var selectedSize : Int = 0

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
                    val list : MutableList<Colors> = ArrayList()
                    for(i in it.value){
                        if(it.value[i.id.toInt() - 1].clothes.idClothes == id){
                            list.add(Colors(it.value[i.id.toInt() - 1].colors.colorId,
                                it.value[i.id.toInt() - 1].colors.nameColor,
                                it.value[i.id.toInt() - 1].colors.hex))
                        }
                    }
                    binding.colorsRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    adapter = ColorsAdapter(list)
                    binding.colorsRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object: ColorsAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            selectedColor = list[position].colorId.toInt()
                            binding.colorText.text = "Selected color: ${list[position].nameColor}"
                        }
                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(this, it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getClothesSizes()
        viewModel.clothesSizesResponse.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    val list : MutableList<SizeClothes> = ArrayList()
                    for(i in it.value){
                        if(it.value[i.id - 1].clothes.idClothes == id){
                            list.add(
                                SizeClothes(it.value[i.id - 1].sizeClothes.idSize,
                                it.value[i.id - 1].sizeClothes.nameSize,
                                )
                            )
                        }
                    }
                    binding.sizesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    sizesAdapter = SizesAdapter(list)
                    binding.sizesRecycler.adapter = sizesAdapter
                    sizesAdapter.setOnItemClickListener(object: SizesAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            selectedSize = list[position].idSize
                            binding.sizesText.text = "Selected size: ${list[position].nameSize}"
                        }
                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(this, it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.itemAvailability.setOnClickListener {
            val itemIntent  = Intent(this, ItemAvailabilityActivity::class.java)
            itemIntent.putExtra("id", id)
            itemIntent.putExtra("selectedSize", selectedSize)
            itemIntent.putExtra("selectedColor", selectedColor)

            if (binding.colorText.text.isEmpty() || binding.sizesText.text.isEmpty()){
                Toast.makeText(this, "Choose size and color first", Toast.LENGTH_SHORT).show()
            }
            else {
                startActivity(itemIntent)
            }
        }
    }

    override fun getViewModel(): Class<ItemDetailsViewModel> =
        ItemDetailsViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityItemDetailsBinding
     = ActivityItemDetailsBinding.inflate(inflater)

    override fun getActivityRepository(): ItemDetailsRepository
    = ItemDetailsRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}