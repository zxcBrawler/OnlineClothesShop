package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.FragmentDeliveryBinding
import com.example.onlineshoppoizon.repository.DeliveryFragmentRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.MapKitInitializer
import com.example.onlineshoppoizon.viewmodel.DeliveryFragmentViewModel


class DeliveryFragment : BaseFragment<DeliveryFragmentViewModel, FragmentDeliveryBinding, DeliveryFragmentRepository> () {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pickUpRadio.setOnClickListener {
            FragmentHelper.openFragment(requireContext(), R.id.delivery_container, PickUpFragment())
        }
    }

    override fun getViewModel(): Class<DeliveryFragmentViewModel> =
        DeliveryFragmentViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveryBinding =
  FragmentDeliveryBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): DeliveryFragmentRepository  =
        DeliveryFragmentRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}