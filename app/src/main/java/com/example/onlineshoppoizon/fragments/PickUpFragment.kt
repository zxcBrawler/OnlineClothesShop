package com.example.onlineshoppoizon.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.DeliveryActivity
import com.example.onlineshoppoizon.databinding.FragmentPickUpBinding
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.PickUpViewModel
import com.yandex.mapkit.MapKitFactory


class PickUpFragment : BaseFragment<PickUpViewModel, FragmentPickUpBinding, PickUpRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deliveryRadio.setOnClickListener {
            FragmentHelper.openFragment(requireContext(), R.id.delivery_container, DeliveryFragment())
        }

    }

    override fun getViewModel(): Class<PickUpViewModel>  =
        PickUpViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPickUpBinding =  FragmentPickUpBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): PickUpRepository =
    PickUpRepository(requestBuilder.buildRequest(ApiInterface::class.java))

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}