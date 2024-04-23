package com.example.onlineshoppoizon.activities


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityAddAddressBinding
import com.example.onlineshoppoizon.repository.AddAddressRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.Const
import com.example.onlineshoppoizon.utils.MapKitInitializer
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.AddAddressViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.places.panorama.PanoramaService.SearchSession
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider


import retrofit2.http.Query

class AddAddressActivity : BaseActivity<AddAddressViewModel, ActivityAddAddressBinding, AddAddressRepository>(), Session.SearchListener {
    private var userId : Long = 0
    private var token = ""
    lateinit var searchManager: SearchManager
    lateinit var searchSession : Session


    private fun submitQuery(query: String) {
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        searchSession = searchManager.submit(query, VisibleRegionUtils.toPolygon(binding.map.map.visibleRegion), SearchOptions(), this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitInitializer.initialize(Const.MAPKIT_API_KEY, this)
        val getUserToken = userPreferences.getToken().asLiveData()

        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()

        }
        getUserToken.observe(this){ userToken ->
            token = userToken
        }
        binding.map.map.move(
            CameraPosition(Point(55.7558267, 37.6172999), 10.0f, 0.0f, 0.0f),

        )

        binding.directionAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                submitQuery(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        //implement null check on fields
        binding.addAddress.setOnClickListener {
            val city = binding.city.text.toString()
            val nameAddress = binding.nameAddress.text.toString()
            val directionAddress = binding.directionAddress.text.toString()
            getUserToken.observe(this){ userToken ->
                token = userToken
                viewModel.addAddress("Bearer $token", userId, city, nameAddress, directionAddress)
            }

            viewModel.addressResponse.observe(this){
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
        binding.back.setOnClickListener {
            finishActivity()
        }

    }

    override fun getViewModel(): Class<AddAddressViewModel> =
        AddAddressViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityAddAddressBinding =
        ActivityAddAddressBinding.inflate(inflater)

    override fun getActivityRepository(): AddAddressRepository =
        AddAddressRepository(requestBuilder.buildRequest(ApiInterface::class.java))


    override fun onSearchResponse(response: Response) {

        val mapObjects = binding.map.map.mapObjects

        mapObjects.clear()

            val point = response.collection.children[0].obj!!.geometry[0].point
            if (point != null) {
                mapObjects.addPlacemark(point, ImageProvider.fromBitmap(createPointIcon()))
                binding.map.map.move(
                    CameraPosition(Point(point.latitude, point.longitude), 15.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.LINEAR, 0.7f), null
                    )
            }


    }

    override fun onSearchError(p0: Error) {
        TODO("Not yet implemented")
    }

    private fun createPointIcon(): Bitmap {

        return Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.location
            ), 70, 70, true
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()

    }

    override fun onStop() {

        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


}