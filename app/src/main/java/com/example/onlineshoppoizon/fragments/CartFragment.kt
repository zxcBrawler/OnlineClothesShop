package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.ItemDetailsActivity
import com.example.onlineshoppoizon.adapters.CartAdapter
import com.example.onlineshoppoizon.databinding.FragmentCartBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import java.math.MathContext

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding, CartRepository>() {

    private lateinit var adapter : CartAdapter
    private var userId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val getUserId = userPreferences.get().asLiveData()

        getUserId.observe(viewLifecycleOwner, Observer {
            userId = it
            viewModel.getCart(userId.toLong())
        })
        viewModel.cartResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    val list : MutableList<Cart> = ArrayList()
                    list.addAll(it.value)

                    if (list.isNotEmpty()){
                        showElements()
                    }

                    var totalPrice = 0.0
                    val m = MathContext(5)
                    for (price in list){

                        totalPrice += (price.sizeClothes.clothes.priceClothes.toBigDecimal(m) *
                                price.quantity.toBigDecimal(m)).toDouble()
                    }


                    binding.totalPrice.text = totalPrice.toString()

                    binding.cartItems.layoutManager = LinearLayoutManager(view.context)
                    adapter = CartAdapter(list)
                    binding.cartItems.adapter = adapter
                    adapter.setOnItemClickListener(object: CartAdapter.OnItemClickListener{
                        override fun onItemClick(position: Long) {
                            val activity = ItemDetailsActivity::class.java
                            startNewActivity(activity, position.toInt())
                        }

                        override fun onDeleteItem(position: Long) {
                            viewModel.deleteFromCart(position)
                            list.removeIf { l -> l.id == position }
                            viewModel.cartResponse.observe(viewLifecycleOwner, Observer { its ->
                                when(its){
                                    is Resource.Success ->{
                                        if (list.isEmpty()){
                                            hideElements()
                                        }
                                    }
                                    is Resource.Failure ->{}
                                }
                            })
                        }

                        override fun onAddItem(position: Long) {
                            viewModel.updateQuantity(position, 1)
                            viewModel.cartResponse.observe(viewLifecycleOwner, Observer { p ->
                                when(p){
                                    is Resource.Success ->{}
                                    is Resource.Failure ->{}
                                }
                            })
                        }

                        override fun onDecreaseItem(position: Long) {
                            viewModel.updateQuantity(position, 2)
                            viewModel.cartResponse.observe(viewLifecycleOwner, Observer { a ->
                                when(a){
                                    is Resource.Success ->{}
                                    is Resource.Failure ->{}
                                }
                            })
                        }
                    })

                    for (item in list){
                        if (item.quantity.toInt() == 0){
                            list.remove(item)
                            viewModel.deleteFromCart(item.id)
                            viewModel.cartResponse.observe(viewLifecycleOwner, Observer { u ->
                                when(u){
                                    is Resource.Success ->{
                                        if (list.isEmpty()){
                                            hideElements()
                                        }

                                    }
                                    is Resource.Failure ->{}
                                }
                            })
                        }
                    }


                }
                is Resource.Failure -> {
                    Toast.makeText(context, it.errorBody.toString(), Toast.LENGTH_SHORT).show()

                }
            }
        })

    }


    override fun getViewModel(): Class<CartViewModel>
        = CartViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)

    override fun getFragmentRepository(): CartRepository
        = CartRepository(requestBuilder.buildRequest(ApiInterface::class.java))

    private fun hideElements(){
        binding.cartItems.visibility = View.GONE
        binding.totalPrice.visibility = View.GONE
        binding.totalText.visibility = View.GONE
        binding.continueButton.visibility = View.GONE
        binding.emptyCart.visibility = View.VISIBLE
        binding.emptyCartText.visibility = View.VISIBLE
    }
    private fun showElements(){
        binding.cartItems.visibility = View.VISIBLE
        binding.totalPrice.visibility = View.VISIBLE
        binding.totalText.visibility = View.VISIBLE
        binding.continueButton.visibility = View.VISIBLE
        binding.emptyCart.visibility = View.GONE
        binding.emptyCartText.visibility = View.GONE
    }
}