package com.example.onlineshoppoizon.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.ColorsAdapter
import com.example.onlineshoppoizon.adapters.SizesAdapter
import com.example.onlineshoppoizon.databinding.ActivityItemDetailsBinding
import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.Colors
import com.example.onlineshoppoizon.model.PhotosOfClothes
import com.example.onlineshoppoizon.model.SizeClothes
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithClothesInfo
import com.example.onlineshoppoizon.viewmodel.ItemDetailsViewModel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class ItemDetailsActivity : BaseActivity<ItemDetailsViewModel, ActivityItemDetailsBinding, ItemDetailsRepository>() {

    private lateinit var adapter : ColorsAdapter
    private lateinit var sizesAdapter: SizesAdapter
    private var selectedColor : Int = 0
    private var selectedSize : Int = 0
    private val list : MutableList<ClothesColors> = ArrayList()
    private val listClothesSizes : MutableList<ClothesSizeClothes> = ArrayList()
    private val photoList : MutableList<PhotosOfClothes> = ArrayList()
    private var userId = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val clothesId = intent.getIntExtra("id", 0)
        val getUserId = userPreferences.get().asLiveData()

        getUserId.observe(this) {
            userId = it
        }

        viewModel.getClothesById(clothesId)
        viewModel.clothesByIdResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.productName.text = it.value.nameClothes
                    binding.productBarcode.text = it.value.barcode
                    binding.productPrice.text = it.value.priceClothes + getString(R.string.p)
                }

                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getPhotos(clothesId.toLong())
        viewModel.clothesPhotoResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.carousel.registerLifecycle(lifecycle)
                    val list: MutableList<CarouselItem> = ArrayList()
                    photoList.addAll(it.value)

                    for (photo in photoList) {
                        list.add(CarouselItem(photo.clothesPhoto.photoAddress))
                    }
                    binding.carousel.setData(list)
                }

                is Resource.Failure -> {
                    Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getClothesColors(clothesId.toLong())
        viewModel.clothesColorsResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val listColors: MutableList<Colors> = ArrayList()
                    list.addAll(it.value)

                    for (color in list) {
                        listColors.add(color.colors)
                    }

                    binding.colorsRecycler.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    adapter = ColorsAdapter(listColors)
                    binding.colorsRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object : ColorsAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            selectedColor = listColors[position].colorId.toInt()
                            binding.colorText.text = getString(R.string.selected_color) + listColors[position].nameColor
                        }
                    })
                }

                is Resource.Failure -> {
                    Toast.makeText(this, getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.getClothesSizes(clothesId.toLong())
        viewModel.clothesSizesResponse.observe(this) {
            when (it) {
                is Resource.Success -> {

                    val listSizes: MutableList<SizeClothes> = ArrayList()
                    listClothesSizes.addAll(it.value)
                    for (size in listClothesSizes) {
                        listSizes.add(size.sizeClothes)
                    }

                    binding.sizesRecycler.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    sizesAdapter = SizesAdapter(listSizes)
                    binding.sizesRecycler.adapter = sizesAdapter
                    sizesAdapter.setOnItemClickListener(object : SizesAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            selectedSize = listSizes[position].id
                            binding.sizesText.text =
                                getString(R.string.selected_size) + listSizes[position].nameSize
                        }
                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(this,
                        getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.back.setOnClickListener {
            finishActivity()
        }

        binding.itemAvailability.setOnClickListener {
            if (selectedSize == 0 || selectedColor == 0){
                Toast.makeText(this,
                    getString(R.string.choose_size_and_color_first), Toast.LENGTH_SHORT).show()
            }
            else {
                val activity = ItemAvailabilityActivity::class.java
                startNewActivityWithClothesInfo(activity,clothesId, selectedSize, selectedColor)
            }

        }
        binding.back.setOnClickListener {
            finishActivity()
        }
        binding.addToCart.setOnClickListener {
            if (selectedSize == 0 || selectedColor == 0){
                Toast.makeText(this, getString(R.string.choose_size_and_color_first), Toast.LENGTH_SHORT).show()
            }
            else {
                val foundColor = list.find { it.colors.colorId.toInt() == selectedColor }
                val foundSize = listClothesSizes.find { it.sizeClothes.id == selectedSize }
                if (foundSize != null && foundColor != null) {
                    viewModel.checkIfItemExistsInCart(
                        foundSize.sizeClothes.id.toLong(),
                        foundColor.colors.colorId,
                        userId.toLong(),
                        clothesId.toLong()
                    )
                    viewModel.existsResponse.observe(this) { exists ->
                        when(exists) {
                            is Resource.Success -> {
                                if (exists.value.toInt() != -1){
                                    viewModel.updateQuantity(exists.value, 1, userId.toLong())
                                    viewModel.existsResponse.observe(this){ r ->
                                        when(r){
                                            is Resource.Success -> {
                                                Toast.makeText(
                                                    this,
                                                    getString(R.string.item_already_exists_in_cart_updating_quantity),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                this.finish()
                                            }
                                            is Resource.Failure -> {
                                            }
                                        }
                                    }
                                }
                                else {
                                    viewModel.addToCart(userId, foundColor.id.toInt(),1, foundSize.id)
                                    viewModel.cartResponse.observe(this) {
                                        when (it) {
                                            is Resource.Success -> {
                                                Toast.makeText(
                                                    this,
                                                    it.value.toString(),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                this.finish()
                                            }
                                            is Resource.Failure -> {
                                                Toast.makeText(
                                                    this,
                                                    it.errorCode.toString(),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    }
                                }
                            }
                            is Resource.Failure -> {}
                        }
                    }
                }
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