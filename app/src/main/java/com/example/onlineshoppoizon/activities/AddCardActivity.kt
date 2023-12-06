package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityAddCardBinding
import com.example.onlineshoppoizon.repository.AddCardRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.AddCardViewModel

class AddCardActivity : BaseActivity<AddCardViewModel, ActivityAddCardBinding, AddCardRepository>() {
    private var userId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.getIntExtra("id", 0)
        userId = intent.toLong()
        binding.addCard.setOnClickListener {
            val cardNum = binding.cardNum.text.toString()
            val cvv = binding.cvv.text.toString()
            val expDate = binding.expDate.text.toString()
            //implement null check on fields
            viewModel.addUserCard(userId,cardNum,expDate,cvv)
            viewModel.cardResponse.observe(this){
                when(it){
                    is Resource.Success -> {
                        Toast.makeText(this, "+", Toast.LENGTH_SHORT).show()
                        this.finish()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(this, "-", Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun getViewModel(): Class<AddCardViewModel> =
        AddCardViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityAddCardBinding =
        ActivityAddCardBinding.inflate(inflater)

    override fun getActivityRepository(): AddCardRepository =
        AddCardRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}