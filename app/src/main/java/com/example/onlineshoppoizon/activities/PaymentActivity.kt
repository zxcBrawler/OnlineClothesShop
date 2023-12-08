package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.AddressAdapter
import com.example.onlineshoppoizon.adapters.CardAdapter
import com.example.onlineshoppoizon.adapters.CardPaymentAdapter
import com.example.onlineshoppoizon.adapters.ItemsPaymentsAdapter
import com.example.onlineshoppoizon.databinding.ActivityPaymentBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.PaymentRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.utils.visible
import com.example.onlineshoppoizon.viewmodel.PaymentViewModel

class PaymentActivity : BaseActivity<PaymentViewModel, ActivityPaymentBinding, PaymentRepository>() {
    private lateinit var adapter : CardPaymentAdapter
    private lateinit var adapterCart : ItemsPaymentsAdapter
    var selectedCardId : Long  = 0
    private val cartIds : MutableList<Long> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val typeDelivery = intent.getLongExtra("typeDelivery", 0)
        val shopAddress = intent.getLongExtra("shopAddress", 0)
        val address = intent.getLongExtra("address", 0)
        var sum = intent.getDoubleExtra("sum", 0.0)

        var userId : Long = 0

        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()
            viewModel.getUserCards(userId)
            viewModel.getCart(userId)
        }

        if (typeDelivery.toInt() == 2){
            binding.itemTotalText.text = String.format("%.3f", sum)
            val newSum = sum + 0.3
            binding.totalCostText.text = String.format("%.3f", newSum)
        } else {
            binding.deliveryText.visible(false)
            binding.deliveryCostText.visible(false)
            binding.totalCostText.text = String.format("%.3f", sum)
            binding.itemTotalText.text = String.format("%.3f", sum)
        }

         viewModel.cartResponse.observe(this){
             when(it) {
                 is Resource.Success -> {
                     val cart : MutableList<Cart> = arrayListOf()
                     cart.addAll(it.value)
                     binding.items.layoutManager = LinearLayoutManager(this)
                     adapterCart = ItemsPaymentsAdapter(cart)
                     binding.items.adapter = adapterCart

                 }
                 is Resource.Failure -> {
                     Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

                 }
             }
         }

        viewModel.cardResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    var cardList : MutableList<UserCard> = arrayListOf()
                    cardList.addAll(it.value)
                    binding.cards.layoutManager = LinearLayoutManager(this)
                    adapter = CardPaymentAdapter(cardList)
                    binding.cards.adapter = adapter
                    adapter.setOnItemClickListener(object : CardPaymentAdapter.OnItemClickListener{
                        override fun onItemClick(position: Long) {
                            selectedCardId = position
                        }
                    })


                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.cartResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    for (item in it.value){
                        cartIds.add(item.id)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }



        binding.pay.setOnClickListener {
            if (typeDelivery.toInt() == 1){
                viewModel.placeNewOrder(sum.toString(),selectedCardId,typeDelivery,shopAddress,null,cartIds)
            }
            else {
                sum += 0.3
                viewModel.placeNewOrder(sum.toString(),selectedCardId,typeDelivery,null,address,cartIds)
            }




            viewModel.orderResponse.observe(this){
                when(it){
                    is Resource.Success -> {
                        viewModel.clearUserCart(userId)
                        viewModel.deleteResponse.observe(this){ its ->
                            when(its){
                                is Resource.Success -> {
                                    startNewActivityFromActivity(MainMenuActivity::class.java)
                                }
                                is Resource.Failure -> {
                                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                    }
                    is Resource.Failure -> {
                        Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun getViewModel(): Class<PaymentViewModel> =
        PaymentViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityPaymentBinding =
        ActivityPaymentBinding.inflate(inflater)

    override fun getActivityRepository(): PaymentRepository =
        PaymentRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}