package com.example.onlineshoppoizon.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.onlineshoppoizon.databinding.ActivityChangeProfileBinding
import com.example.onlineshoppoizon.repository.ChangeProfileRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.RealPathUtil
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.ChangeProfileViewModel


class ChangeProfileActivity : BaseActivity<ChangeProfileViewModel, ActivityChangeProfileBinding, ChangeProfileRepository> () {
    private lateinit var path : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent.getIntExtra("id", 0)

        viewModel.getUserById(intent.toLong())

        viewModel.profileResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    binding.email.setText(it.value.email)
                    binding.gender.setSelection(it.value.gender.id.toInt())
                    binding.username.setText(it.value.username)
                    binding.phoneNum.setText(it.value.phoneNumber)
                    path = it.value.profilePhoto
                    binding.logo.setImageURI(path.toUri())
                }
                is Resource.Failure -> {

                }
            }
        }
        binding.back.setOnClickListener {
            finishActivity()
        }
        binding.logo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            resultLauncher.launch(intent)
        }

        binding.additionalInfoButton.setOnClickListener {
            viewModel.changeProfile(intent.toLong(),
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.gender.selectedItemId,
                binding.phoneNum.text.toString(),
                path,
                binding.username.text.toString())
        }
        viewModel.userResponse.observe(this){ user ->
            when(user){
                is Resource.Success -> {
                    Toast.makeText(this, "+", Toast.LENGTH_SHORT).show()
                    this.finish()

                }
                is Resource.Failure -> {
                    Toast.makeText(this, "-", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getViewModel(): Class<ChangeProfileViewModel> =
        ChangeProfileViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityChangeProfileBinding =
        ActivityChangeProfileBinding.inflate(inflater)

    override fun getActivityRepository(): ChangeProfileRepository =
        ChangeProfileRepository(requestBuilder.buildRequest(ApiInterface::class.java))

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data

            path = RealPathUtil.getRealPathFromURI(applicationContext, uri!!)!!
            binding.logo.setImageURI(uri)

        }
    }
}