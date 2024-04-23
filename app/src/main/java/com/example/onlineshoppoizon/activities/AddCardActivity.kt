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
import com.example.onlineshoppoizon.utils.CreditCardNumberInputFilter
import com.example.onlineshoppoizon.utils.CvvInputFilter
import com.example.onlineshoppoizon.utils.ExpDateInputFilter
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.AddCardViewModel

class AddCardActivity : BaseActivity<AddCardViewModel, ActivityAddCardBinding, AddCardRepository>() {
    private var userId : Long = 0
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getUserToken = userPreferences.getToken().asLiveData()
        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()
            getUserToken.observe(this){ userToken ->
                token = userToken
            }
        }
        binding.cardNum.addTextChangedListener(CreditCardNumberInputFilter(binding.cardNum))
        binding.expDate.addTextChangedListener(ExpDateInputFilter(binding.expDate))
        binding.cvv.addTextChangedListener(CvvInputFilter(binding.cvv))
        binding.addCard.setOnClickListener {

            if(binding.cardNum.text.toString().isEmpty() || binding.expDate.text.toString().isEmpty() || binding.cvv.text.toString().isEmpty()){
                Toast.makeText(this,getString(R.string.enter_all_fields), Toast.LENGTH_SHORT).show()
            }
            if (binding.cardNum.text.length < 19 || binding.cvv.text.length < 2 || binding.expDate.text.length < 4) {
                Toast.makeText(this,getString(R.string.enter_valid_data), Toast.LENGTH_SHORT).show()
            }
            else {
                val cardNum = binding.cardNum.text.toString()
                val cvv = binding.cvv.text.toString()
                val expDate = binding.expDate.text.toString()

                getUserToken.observe(this){ userToken ->
                    token = userToken
                    viewModel.addUserCard("Bearer $token", userId,cardNum,expDate,cvv)
                }

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