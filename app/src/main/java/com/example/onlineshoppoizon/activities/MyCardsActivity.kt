package com.example.onlineshoppoizon.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.CardAdapter
import com.example.onlineshoppoizon.adapters.ItemAvailabilityAdapter
import com.example.onlineshoppoizon.databinding.ActivityMyCardsBinding
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.MyCardsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.MyCardsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyCardsActivity : BaseActivity<MyCardsViewModel, ActivityMyCardsBinding, MyCardsRepository>() {
    private lateinit var adapter : CardAdapter
    private var userId : Long = 0
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getUserToken = userPreferences.getToken().asLiveData()

        val intent = intent.getIntExtra("id", 0)
        userId = intent.toLong()
        getUserToken.observe(this){ userToken ->
            token = userToken
            viewModel.getUserCards("Bearer $token", userId)
        }


        viewModel.cardResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    var cardList : MutableList<UserCard> = arrayListOf()
                    cardList.addAll(it.value)
                    binding.recyclerCards.layoutManager = LinearLayoutManager(this)
                    adapter = CardAdapter(cardList)
                    binding.recyclerCards.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        CardAdapter.OnItemClickListener {
                        override fun onItemClick(position: Long) {
                            Toast.makeText(applicationContext, cardList[position.toInt()].card.cvv, Toast.LENGTH_SHORT).show()
                        }

                        override fun onItemDelete(position: Long) {
                            var dialog = MaterialAlertDialogBuilder(this@MyCardsActivity, R.style.CustomDialogTheme)
                            dialog.setTitle(getString(R.string.delete_this_card))
                                .setPositiveButton(getString(R.string.yes)
                            ) {
                                    newDialog, _ ->
                                newDialog.dismiss()
                                    getUserToken.observe(this@MyCardsActivity){ userToken ->
                                        token = userToken
                                        viewModel.deleteCard("Bearer $token", position)
                                        viewModel.deleteResponse.observe(this@MyCardsActivity){ its ->
                                            when (its){
                                                is Resource.Success -> {
                                                    Toast.makeText(applicationContext, getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show()
                                                    this@MyCardsActivity.finish()
                                                }
                                                is Resource.Failure -> {
                                                    Toast.makeText(applicationContext, getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show()
                                                    this@MyCardsActivity.finish()
                                                }
                                            }
                                        }
                                    }

                            }.setNegativeButton(getString(R.string.no)
                            ) { newDialog, _ ->
                                newDialog.dismiss()
                            }
                                .show()
                        }

                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.addCard.setOnClickListener {
            val activity = AddCardActivity::class.java
            startNewActivityWithId(activity, userId.toInt())
            this.finish()
        }
        binding.back.setOnClickListener {
            finishActivity()
        }

    }

    override fun getViewModel(): Class<MyCardsViewModel> =
        MyCardsViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMyCardsBinding =
        ActivityMyCardsBinding.inflate(inflater)

    override fun getActivityRepository(): MyCardsRepository =
        MyCardsRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}