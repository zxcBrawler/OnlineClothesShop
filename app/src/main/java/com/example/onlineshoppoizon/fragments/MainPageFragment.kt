package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.ItemDetailsActivity
import com.example.onlineshoppoizon.activities.MainMenuActivity
import com.example.onlineshoppoizon.adapters.ClothesAdapter
import com.example.onlineshoppoizon.databinding.ActivityMainBinding
import com.example.onlineshoppoizon.databinding.FragmentMainPageBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.MainPageViewModel

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
                    TODO()
                }
            }
        }

        viewModel.getClothes()

        viewModel.clothesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.itemsRecycler.layoutManager = GridLayoutManager(view.context, 2)
                    adapter = ClothesAdapter(it.value)
                    binding.itemsRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object : ClothesAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val activity = ItemDetailsActivity::class.java
                            startNewActivityWithId(activity, it.value[position].idClothes)
                        }

                    })
                    Toast.makeText(requireContext(), it.value.toString(), Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.errorCode.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.cart.setOnClickListener {
            val activity = requireActivity() as MainMenuActivity
            activity.binding.bottomNav.selectedItemId = R.id.cart
            FragmentHelper.openFragment(requireContext(), R.id.fragmentMainMenu, CartFragment())
        }
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