package com.example.onlineshoppoizon.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.DeliveryActivity
import com.example.onlineshoppoizon.activities.PaymentActivity
import com.example.onlineshoppoizon.databinding.FragmentPickUpBinding
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithDeliveryPickUpInfo
import com.example.onlineshoppoizon.viewmodel.PickUpViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider


class PickUpFragment : BaseFragment<PickUpViewModel, FragmentPickUpBinding, PickUpRepository>() {
    var currentAddress : ShopAddresses? = ShopAddresses()
    private var sum : Double = 0.0
    private var token = ""
    private var userId = 0
    private var userCart : List<Cart> = mutableListOf()
    private val shopsList: MutableList<ShopAddresses> = mutableListOf()
    var isDialogShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getUserId = userPreferences.get().asLiveData()
        val getUserToken = userPreferences.getToken().asLiveData()
        //getting userID from userPreferences
        getUserId.observe(viewLifecycleOwner) {
            userId = it
            Log.e("123", userId.toString())
        }

        getUserToken.observe(viewLifecycleOwner){ userToken ->
            token = userToken
            Log.e("123", token)
            viewModel.getCart("Bearer $token", userId.toLong()) // getting user cart info
        }

        val activity = requireActivity() as DeliveryActivity

        sum = activity.getCartSum()

        val geoPosition = binding.map.map.mapObjects.addPlacemark(Const.START_PLACE,ImageProvider.fromBitmap(createPointIcon()))

        binding.deliveryRadio.setOnClickListener {
            FragmentHelper.openFragment(requireContext(), R.id.delivery_container, DeliveryFragment())
        }

        viewModel.cartResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    userCart = it.value
                    for (item in userCart){
                        viewModel.getItemAvailability("Bearer $token", item.sizeClothes.id.toLong(), item.colorClothesCart.id.toLong())
                        viewModel.itemAvailabilityResponse.observe(viewLifecycleOwner){ its ->
                            when(its){
                                is Resource.Success -> {
                                        for (shop in its.value){
                                            if(shopsList.find { address -> address == shop.shopAddressesGarnish } == null)
                                                shopsList.add(shop.shopAddressesGarnish)
                                        }
                                        val shopAddresses : MutableList<String> = arrayListOf()
                                        val itr = shopsList.iterator()

                                        while (itr.hasNext()){
                                            val newItem = itr.next()
                                            shopAddresses.add(newItem.shopAddressDirection)
                                        }
                                        val arrayAdapter =
                                            ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, shopAddresses)
                                        binding.addresses.adapter = arrayAdapter
                                        binding.addresses.setSelection(0)


                                }
                                is Resource.Failure -> {
                                    Toast.makeText(requireContext(), "cannot obtain cart", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "cannot obtain cart", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.addresses.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                currentAddress = findCurrentAddress(shopsList)
                geoPosition.geometry = Point(
                    currentAddress!!.latitude.toDouble(),
                    currentAddress!!.longitude.toDouble())

                binding.map.map.move(
                    CameraPosition(geoPosition.geometry, 16.5f, 0.0f, 0.0f),
                    Animation(Animation.Type.LINEAR, 0.7f), null
                )
                geoPosition.addTapListener {
                        _, _ ->
                    currentAddress = findCurrentAddress(shopsList)
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

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }




        binding.payment.setOnClickListener {
            val paymentActivity = PaymentActivity::class.java
            startNewActivityWithDeliveryPickUpInfo(
                paymentActivity,
                Const.TYPE_PICK_UP.toLong(),
                currentAddress!!.shopAddressId,
                sum)
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