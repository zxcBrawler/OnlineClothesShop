package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.FragmentCartBinding
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.viewmodel.CartViewModel

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding, CartRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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