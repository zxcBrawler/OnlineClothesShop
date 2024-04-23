package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.DeliveryActivity
import com.example.onlineshoppoizon.activities.ItemDetailsActivity
import com.example.onlineshoppoizon.activities.MainMenuActivity
import com.example.onlineshoppoizon.adapters.CartAdapter
import com.example.onlineshoppoizon.databinding.FragmentCartBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.*
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding, CartRepository>() {

    private lateinit var adapter : CartAdapter
    private var userId = 0
    private var doublePrice = 0.0
    private var totalPrice = ""
    private var token = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getUserId = userPreferences.get().asLiveData()
        val getUserToken = userPreferences.getToken().asLiveData()

        //getting userID from userPreferences
        getUserId.observe(viewLifecycleOwner) {
            userId = it
        }

        getUserToken.observe(viewLifecycleOwner){ userToken ->
            token = userToken
            viewModel.getCart("Bearer $token", userId.toLong()) // getting user cart info
        }
        viewModel.cartResponse.observe(viewLifecycleOwner) { // observing data that was returned from API`
            when (it) {
                is Resource.Success -> {
                    val list: MutableList<Cart> = ArrayList()
                    list.addAll(it.value)

                    if (list.isNotEmpty()) {
                        showElements()
                    }

                    // Getting every item price from cart in for() cycle
                    for (price in list) {
                        doublePrice += (price.sizeClothes.clothes.priceClothes.toBigDecimal() *
                                price.quantity.toBigDecimal()).toDouble() // counting total of each item based on item quantity

                        val totalPrice = String.format("%.3f", doublePrice) // format number to sting with 3 digits after point

                        binding.totalPrice.text = totalPrice // setting totalPrice TextView value to total price that had been counted
                    }


                    binding.cartItems.layoutManager = LinearLayoutManager(view.context)
                    adapter = CartAdapter(list)
                    binding.cartItems.adapter = adapter
                    adapter.setOnItemClickListener(object : CartAdapter.OnItemClickListener {
                        override fun onItemClick(position: Long) {
                            val activity = ItemDetailsActivity::class.java
                            startNewActivityWithId(activity, position.toInt())
                        }

                        override fun onDeleteItem(position: Long) {
                            var dialog = MaterialAlertDialogBuilder(context!!, R.style.CustomDialogTheme)
                            dialog.setTitle(getString(R.string.delete_this_item))
                                .setPositiveButton(getString(R.string.yes)
                                ) { newDialog, _ ->
                                    newDialog.dismiss()
                                    getUserToken.observe(viewLifecycleOwner){ userToken ->
                                        token = userToken
                                        viewModel.deleteFromCart("Bearer $token",position, userId.toLong())
                                    }
                                    list.removeIf { l -> l.id == position }
                                    viewModel.cartResponse.observe(viewLifecycleOwner) { its ->
                                        when (its) {
                                            is Resource.Success -> {
                                                if (list.isEmpty()) {
                                                    hideElements()
                                                }
                                            }

                                            is Resource.Failure -> {
                                                Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }.setNegativeButton(getString(R.string.no)){
                                        newDialog, _ ->
                                    newDialog.dismiss()
                                }. show()
                            getUserToken.observe(viewLifecycleOwner){ userToken ->
                                token = userToken
                                viewModel.getCart("Bearer $token", userId.toLong()) // getting user cart info
                            }

                            viewModel.cartResponse.observe(viewLifecycleOwner){ new ->
                                when(new){
                                    is Resource.Success -> {
                                        list.clear()
                                        list.addAll(new.value)
                                        binding.totalPrice.text = "0"
                                        doublePrice = 0.0
                                        for (price in list) {
                                            doublePrice += (price.sizeClothes.clothes.priceClothes.toBigDecimal() *
                                                    price.quantity.toBigDecimal()).toDouble()

                                            totalPrice = String.format("%.3f", doublePrice)

                                            binding.totalPrice.text = totalPrice
                                        }
                                        val itr = list.iterator()

                                        while (itr.hasNext()){
                                            val item = itr.next()
                                            if (item.quantity.toInt() == 0){

                                                getUserToken.observe(viewLifecycleOwner){ userToken ->
                                                    token = userToken
                                                    viewModel.deleteFromCart("Bearer $token", item.id, userId.toLong())
                                                }
                                                itr.remove()
                                                viewModel.cartResponse.observe(viewLifecycleOwner) { u ->
                                                    when (u) {
                                                        is Resource.Success -> {
                                                            if (list.isEmpty()) {
                                                                hideElements()
                                                            }
                                                        }
                                                        is Resource.Failure -> {
                                                            Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    is Resource.Failure -> {
                                        Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            if (list.isNotEmpty()) {
                                showElements()
                            }
                        }

                        override fun onAddItem(position: Long) {
                            for (item in list) {
                                if (item.quantity.toInt() < Const.MAX_ITEM_COUNT) {
                                    getUserToken.observe(viewLifecycleOwner){ userToken ->
                                        token = userToken
                                        viewModel.updateQuantity("Bearer $token", position, Const.ADD_ITEM, userId.toLong())
                                    }

                                    viewModel.cartResponse.observe(viewLifecycleOwner) { p ->
                                        when (p) {
                                            is Resource.Success -> {
                                            }
                                            is Resource.Failure -> {
                                                Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                                else {
                                    Toast.makeText(context,
                                        getString(
                                            R.string.cannot_add_more_than_of_single_item
                                        ),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                            updateList(list, getUserToken)
                        }

                        override fun onDecreaseItem(position: Long) {
                            getUserToken.observe(viewLifecycleOwner){ userToken ->
                                token = userToken
                                viewModel.updateQuantity("Bearer $token", position, Const.DECREASE_ITEM, userId.toLong())
                            }

                            viewModel.cartResponse.observe(viewLifecycleOwner) { a ->
                                when (a) {
                                    is Resource.Success -> {}
                                    is Resource.Failure -> {
                                        Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            updateList(list, getUserToken)
                        }

                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.continueButton.setOnClickListener {
            val activity = DeliveryActivity::class.java
            startNewActivityWithCartSum(activity, doublePrice)
        }
    }

    private fun updateList(list : MutableList<Cart>, getUserToken : LiveData<String>){
        getUserToken.observe(viewLifecycleOwner){ userToken ->
            token = userToken
            viewModel.getCart("Bearer $token", userId.toLong())
        }

        viewModel.getCart("Bearer $token", userId.toLong())
        viewModel.cartResponse.observe(viewLifecycleOwner){ new ->
            when(new){
                is Resource.Success -> {
                    list.clear()
                    list.addAll(new.value)
                    binding.totalPrice.text = "0"
                    doublePrice = 0.0
                    for (price in list) {
                        doublePrice += (price.sizeClothes.clothes.priceClothes.toBigDecimal() *
                                price.quantity.toBigDecimal()).toDouble()

                        val totalPrice = String.format("%.3f", doublePrice)

                        binding.totalPrice.text = totalPrice
                    }
                    val itr = list.iterator()

                    while (itr.hasNext()){
                        val item = itr.next()
                        if (item.quantity.toInt() == 0){
                            getUserToken.observe(viewLifecycleOwner){ userToken ->
                                token = userToken
                                viewModel.deleteFromCart("Bearer $token", item.id, userId.toLong())
                            }

                            itr.remove()
                            viewModel.cartResponse.observe(viewLifecycleOwner) { u ->
                                when (u) {
                                    is Resource.Success -> {
                                        if (list.isEmpty()) {
                                            hideElements()
                                        }
                                    }
                                    is Resource.Failure -> {
                                        Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (list.isNotEmpty()) {
            showElements()
        }

    }

    private fun hideElements(){
        binding.cartItems.visible(false)
        binding.totalPrice.visible(false)
        binding.totalText.visible(false)
        binding.continueButton.visible(false)
        binding.emptyCart.visible(true)
        binding.emptyCartText.visible(true)
    }
    private fun showElements(){
        binding.cartItems.visible(true)
        binding.totalPrice.visible(true)
        binding.totalText.visible(true)
        binding.continueButton.visible(true)
        binding.emptyCart.visible(false)
        binding.emptyCartText.visible(false)
    }



    override fun getViewModel(): Class<CartViewModel>
            = CartViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)

    override fun getFragmentRepository(): CartRepository
            = CartRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}