package com.example.onlineshoppoizon.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.FragmentPickUpBinding
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.viewmodel.PickUpViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider


class PickUpFragment : BaseFragment<PickUpViewModel, FragmentPickUpBinding, PickUpRepository>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var geoPosition = binding.map.map.mapObjects.addPlacemark(Const.START_PLACE,ImageProvider.fromBitmap(createPointIcon()))

        binding.deliveryRadio.setOnClickListener {
            FragmentHelper.openFragment(requireContext(), R.id.delivery_container, DeliveryFragment())
        }

        viewModel.getShops()
        viewModel.shopsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {

                    val shopAddresses : MutableList<String> = arrayListOf()
                    val itr = it.value.iterator()


                    while (itr.hasNext()){
                        val item = itr.next()
                        shopAddresses.add(item.shopAddressDirection)
                    }

                    val arrayAdapter =
                        ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,shopAddresses)
                    binding.addresses.adapter = arrayAdapter


                    binding.addresses.onItemSelectedListener = object : OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            var currentAddress = findCurrentAddress(it.value)
                            if (currentAddress?.shopAddressId == currentAddress?.shopAddressId
                            ){

                                geoPosition.geometry = Point(
                                    currentAddress!!.latitude.toDouble(),
                                    currentAddress.longitude.toDouble())

                                binding.map.map.move(
                                    CameraPosition(geoPosition.geometry, 16.5f, 0.0f, 0.0f),
                                    Animation(Animation.Type.LINEAR, 0.7f), null
                                )
                                geoPosition.addTapListener {
                                        p0, p1 ->
                                        currentAddress = findCurrentAddress(it.value)
                                        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomDialogTheme)

                                    dialog.setTitle(getString(R.string.shop_info)).setMessage(
                                        getString(
                                            R.string.contact_number
                                        ) +
                                            "${currentAddress?.contactNumber}\n" + getString(R.string.shop_metro) +
                                    "${currentAddress?.shopMetro}").show()

                                    true
                                }


                            }
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

    }
    private fun findCurrentAddress(it : List<ShopAddresses>) : ShopAddresses? =
        it.find { p -> p.shopAddressDirection == binding.addresses.selectedItem }

    private fun createPointIcon(): Bitmap {

        return Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                requireContext().resources,
                R.drawable.xclogo
            ), 70, 70, true
        )
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