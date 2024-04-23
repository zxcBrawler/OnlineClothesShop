package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityForgotPasswordBinding
import com.example.onlineshoppoizon.repository.ChangePasswordRepository
import com.example.onlineshoppoizon.repository.FilteredCatalogueRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.ChangePasswordViewModel

class ForgotPasswordActivity : BaseActivity<ChangePasswordViewModel, ActivityForgotPasswordBinding, ChangePasswordRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.back.setOnClickListener {
            finishActivity()
        }

        binding.changePasswordButton.setOnClickListener {


            if (binding.username.text.isEmpty() || binding.confirmPassword.text.isEmpty() || binding.password.text.isEmpty()){
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else if (binding.confirmPassword.text.toString() != binding.password.text.toString()){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.changePassword(binding.username.text.toString(), binding.password.text.toString())
                viewModel.changePasswordResponse.observe(this){
                    when(it){
                        is Resource.Success -> {
                            Toast.makeText(this,
                                "Password changed successfully", Toast.LENGTH_SHORT).show()
                            finishActivity()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                            finishActivity()
                        }
                    }
                }
            }
        }
    }

    override fun getViewModel(): Class<ChangePasswordViewModel> =
        ChangePasswordViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityForgotPasswordBinding =
        ActivityForgotPasswordBinding.inflate(inflater)

    override fun getActivityRepository(): ChangePasswordRepository =
        ChangePasswordRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}