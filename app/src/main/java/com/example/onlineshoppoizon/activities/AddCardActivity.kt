package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityAddCardBinding
import com.example.onlineshoppoizon.repository.AddCardRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.AddCardViewModel

class AddCardActivity : BaseActivity<AddCardViewModel, ActivityAddCardBinding, AddCardRepository>() {
    private var userId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()
        }
        binding.addCard.setOnClickListener {
            val cardNum = binding.cardNum.text.toString()
            val cvv = binding.cvv.text.toString()
            val expDate = binding.expDate.text.toString()
            //implement null check on fields
            viewModel.addUserCard(userId,cardNum,expDate,cvv)
            viewModel.cardResponse.observe(this){
                when(it){
                    is Resource.Success -> {
                        finishActivity()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        binding.back.setOnClickListener {
            finishActivity()
        }
    }

    override fun getViewModel(): Class<AddCardViewModel> =
        AddCardViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityAddCardBinding =
        ActivityAddCardBinding.inflate(inflater)

    override fun getActivityRepository(): AddCardRepository =
        AddCardRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}