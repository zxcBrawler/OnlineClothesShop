package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.DeliveryActivity
import com.example.onlineshoppoizon.activities.PaymentActivity
import com.example.onlineshoppoizon.databinding.FragmentDeliveryBinding
import com.example.onlineshoppoizon.model.Address
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.repository.DeliveryFragmentRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.MapKitInitializer
import com.example.onlineshoppoizon.utils.startNewActivityWithDeliveryHomeInfo
import com.example.onlineshoppoizon.utils.startNewActivityWithDeliveryPickUpInfo
import com.example.onlineshoppoizon.viewmodel.DeliveryFragmentViewModel


class DeliveryFragment : BaseFragment<DeliveryFragmentViewModel, FragmentDeliveryBinding, DeliveryFragmentRepository> () {

    var currentAddress : Address? = Address()
    private var userId : Long = 0
    private var sum : Double = 0.0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as DeliveryActivity

        sum = activity.getCartSum()

        userPreferences.get().asLiveData().observe(viewLifecycleOwner){
            userId = it.toLong()
            viewModel.getUserAddresses(userId)
        }
        binding.pickUpRadio.setOnClickListener {
            FragmentHelper.openFragment(requireContext(), R.id.delivery_container, PickUpFragment())
        }


        viewModel.addressResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    val nameAddresses : MutableList<String> = arrayListOf()
                    val addresses : MutableList<Address> = arrayListOf()
                    val itr = it.value.iterator()

                    while (itr.hasNext()){
                        val item = itr.next()
                        addresses.add(item.address)
                        nameAddresses.add(item.address.nameAddress)
                    }

                    val arrayAdapter =
                        ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,nameAddresses)
                    binding.address.adapter = arrayAdapter
                    binding.address.setSelection(0)

                    binding.address.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            currentAddress = findCurrentAddress(addresses)

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }

                }
                is Resource.Failure -> {

                }
            }
        }
        binding.payment.setOnClickListener {
            val activity = PaymentActivity::class.java
            startNewActivityWithDeliveryHomeInfo(
                activity,
                Const.TYPE_HOME.toLong(),
                currentAddress!!.idAddress,
                sum)
        }
    }
    private fun findCurrentAddress(it : List<Address>) : Address? =
        it.find { p -> p.nameAddress == binding.address.selectedItem }

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