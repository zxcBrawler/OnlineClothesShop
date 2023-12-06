package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.startNewActivityFromFragment
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.utils.visible
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding, CartRepository>() {

    private lateinit var adapter : CartAdapter
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

                    if (list.isNotEmpty()) {
                        showElements()
                    }

                    var doublePrice = 0.0
                    for (price in list) {

                        doublePrice += (price.sizeClothes.clothes.priceClothes.toBigDecimal() *
                                price.quantity.toBigDecimal()).toDouble()

                        val totalPrice = String.format("%.3f", doublePrice)

                        binding.totalPrice.text = totalPrice
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
                            dialog.setTitle("Delete this item?")
                                .setPositiveButton("Yes"
                                ) { newDialog, _ ->
                                    newDialog.dismiss()
                                    viewModel.deleteFromCart(position)
                                    list.removeIf { l -> l.id == position }
                                    viewModel.cartResponse.observe(viewLifecycleOwner) { its ->
                                        when (its) {
                                            is Resource.Success -> {
                                                if (list.isEmpty()) {
                                                    hideElements()
                                                }
                                            }

                                            is Resource.Failure -> {}
                                        }
                                    }
                                    updateCart(list)
                                }.setNegativeButton("No"){
                                        newDialog, _ ->
                                    newDialog.dismiss()
                                }. show()

                        }

                        override fun onAddItem(position: Long) {
                            for (item in list) {
                                if (item.quantity.toInt() < Const.MAX_ITEM_COUNT) {
                                    viewModel.updateQuantity(position, Const.ADD_ITEM)
                                    viewModel.cartResponse.observe(viewLifecycleOwner) { p ->
                                        when (p) {
                                            is Resource.Success -> {}
                                            is Resource.Failure -> {}
                                        }
                                    }
                                }
                                else {
                                    Toast.makeText(context,
                                        "Cannot add more than ${Const.MAX_ITEM_COUNT} of single item",
                                        Toast.LENGTH_SHORT).show()
                                }

                            }
                            updateCart(list)
                        }

                        override fun onDecreaseItem(position: Long) {
                            viewModel.updateQuantity(position, Const.DECREASE_ITEM)
                            viewModel.cartResponse.observe(viewLifecycleOwner) { a ->
                                when (a) {
                                    is Resource.Success -> {}
                                    is Resource.Failure -> {}
                                }
                            }
                            updateCart(list)
                        }
                    })
                    val itr = list.iterator()

                    while (itr.hasNext()){
                        val item = itr.next()
                        if (item.quantity.toInt() == 0){
                            viewModel.deleteFromCart(item.id)
                            itr.remove()
                            viewModel.cartResponse.observe(viewLifecycleOwner) { u ->
                                when (u) {
                                    is Resource.Success -> {
                                        if (list.isEmpty()) {
                                            hideElements()
                                        }
                                    }
                                    is Resource.Failure -> {}
                                }
                            }
                        }
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(context, it.errorBody.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.continueButton.setOnClickListener {
            val activity = DeliveryActivity::class.java
            startNewActivityFromFragment(activity)
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

    private fun updateCart(list : MutableList<Cart>){
        viewModel.getCart(userId.toLong())
        viewModel.cartResponse.observe(viewLifecycleOwner){ new ->
            when(new){
                is Resource.Success -> {
                    list.clear()
                    list.addAll(new.value)
                    FragmentHelper.openFragment(requireContext(), R.id.fragmentMainMenu, CartFragment())
                }
                is Resource.Failure -> {}
            }
        }
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