package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.ClothesAdapter
import com.example.onlineshoppoizon.databinding.ActivityFilteredCatalogueBinding
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.repository.FilteredCatalogueRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.FilteredCatalogueViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class FilteredCatalogueActivity : BaseActivity<FilteredCatalogueViewModel, ActivityFilteredCatalogueBinding, FilteredCatalogueRepository>() {
    private lateinit var adapter : ClothesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent.getIntExtra("id", 0)
        viewModel.getTypeClothes(intent.toLong())

        viewModel.typeClothesResponse.observe(this){
            when(it){
                is Resource.Success -> {

                    val list : MutableList<Clothes> = arrayListOf()
                    list.addAll(it.value)

                    if (list.size > 0){
                        binding.nameType.text = list[0].typeClothes.nameType
                    }

                    binding.itemsRecycler.layoutManager = GridLayoutManager(applicationContext, 2)
                    adapter = ClothesAdapter(it.value)
                    binding.itemsRecycler.adapter = setAnimationAlpha(adapter)
                    adapter.setOnItemClickListener(object : ClothesAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val activity = ItemDetailsActivity::class.java
                            startNewActivityWithId(activity, it.value[position].idClothes)
                        }

                    })

                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

                }
            }
        }
        binding.logo.setOnClickListener {
            finishActivity()
        }
    }
    private fun setAnimationAlpha (adapter : RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder>) : AlphaInAnimationAdapter {
        val alphaInAnimationAdapter = AlphaInAnimationAdapter(adapter)
        alphaInAnimationAdapter.setDuration(400)
        alphaInAnimationAdapter.setInterpolator(AccelerateDecelerateInterpolator())
        alphaInAnimationAdapter.setFirstOnly(true)

        return alphaInAnimationAdapter

    }

    override fun getViewModel(): Class<FilteredCatalogueViewModel> =
        FilteredCatalogueViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityFilteredCatalogueBinding =
        ActivityFilteredCatalogueBinding.inflate(inflater)

    override fun getActivityRepository(): FilteredCatalogueRepository =
        FilteredCatalogueRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}