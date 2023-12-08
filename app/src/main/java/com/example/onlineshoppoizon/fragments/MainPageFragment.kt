package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.ItemDetailsActivity
import com.example.onlineshoppoizon.activities.MainMenuActivity
import com.example.onlineshoppoizon.adapters.ClothesAdapter
import com.example.onlineshoppoizon.databinding.FragmentMainPageBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.MainPageViewModel
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class MainPageFragment : BaseFragment<MainPageViewModel, FragmentMainPageBinding,MainPageRepository >() {
    private lateinit var adapter : ClothesAdapter
    private var userId = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getUserId = userPreferences.get().asLiveData()

        getUserId.observe(viewLifecycleOwner) {
            userId = it
            viewModel.getCart(userId.toLong())
        }

        viewModel.cartResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val list: MutableList<Cart> = ArrayList()
                    list.addAll(it.value)

                    var itemQuantity = 0

                    for (item in list) {
                        itemQuantity += item.quantity.toInt()
                    }
                    binding.quantity.text = itemQuantity.toString()
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getClothes()

        viewModel.clothesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.itemsRecycler.layoutManager = GridLayoutManager(view.context, 2)

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
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.cart.setOnClickListener {
            val activity = requireActivity() as MainMenuActivity
            activity.binding.bottomNav.selectedItemId = R.id.cart
        }
    }

    private fun setAnimationAlpha (adapter : RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder>) : AlphaInAnimationAdapter{
        val alphaInAnimationAdapter = AlphaInAnimationAdapter(adapter)
        alphaInAnimationAdapter.setDuration(400)
        alphaInAnimationAdapter.setInterpolator(AccelerateDecelerateInterpolator())
        alphaInAnimationAdapter.setFirstOnly(true)

        return alphaInAnimationAdapter

    }

    override fun getViewModel(): Class<MainPageViewModel>
        = MainPageViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): MainPageRepository {
        val api = requestBuilder.buildRequest(ApiInterface::class.java)
        return MainPageRepository(api)
    }
}