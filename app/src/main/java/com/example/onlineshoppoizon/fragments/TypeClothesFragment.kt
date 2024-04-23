package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.FilteredCatalogueActivity
import com.example.onlineshoppoizon.adapters.CategoriesAdapter
import com.example.onlineshoppoizon.adapters.TypeClothesAdapter
import com.example.onlineshoppoizon.databinding.FragmentTypeClothesBinding
import com.example.onlineshoppoizon.repository.FilteredCatalogueRepository
import com.example.onlineshoppoizon.repository.TypeClothesRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.TypeClothesViewModel


class TypeClothesFragment : BaseFragment<TypeClothesViewModel, FragmentTypeClothesBinding, TypeClothesRepository>() {
    private lateinit var adapter : TypeClothesAdapter
    private var token = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id")
        val getUserToken = userPreferences.getToken().asLiveData()
        getUserToken.observe(viewLifecycleOwner){ userToken ->
            token = userToken
            viewModel.getTypeClothes("Bearer $token", id!!)
        }


        viewModel.typeClothesResponse.observe((viewLifecycleOwner)){
            when(it){
                is Resource.Success -> {
                    binding.typeClothes.layoutManager = LinearLayoutManager(view.context)
                    adapter = TypeClothesAdapter(it.value)
                    binding.typeClothes.adapter = adapter
                    adapter.setOnItemClickListener(object: TypeClothesAdapter.OnItemClickListener{
                        override fun onItemClick(position: Long) {
                            val activity = FilteredCatalogueActivity::class.java
                            startNewActivityWithId(activity, position.toInt())

                        }

                    })

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.back.setOnClickListener {
            FragmentHelper.backToLoginFragment(requireContext(), R.id.fragmentMainMenu, CatalogueFragment())
        }
    }

    override fun getViewModel(): Class<TypeClothesViewModel>  =
        TypeClothesViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTypeClothesBinding =
        FragmentTypeClothesBinding.inflate(layoutInflater)

    override fun getFragmentRepository(): TypeClothesRepository =
        TypeClothesRepository(requestBuilder.buildRequest(ApiInterface::class.java))

}