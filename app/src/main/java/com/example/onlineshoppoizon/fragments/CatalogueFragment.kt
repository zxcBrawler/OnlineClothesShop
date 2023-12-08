package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.CartAdapter
import com.example.onlineshoppoizon.adapters.CategoriesAdapter
import com.example.onlineshoppoizon.databinding.FragmentCatalogueBinding
import com.example.onlineshoppoizon.repository.CatalogueRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.CatalogueViewModel


class CatalogueFragment : BaseFragment<CatalogueViewModel, FragmentCatalogueBinding, CatalogueRepository>() {
    private lateinit var adapter : CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategories()
        viewModel.catalogueResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(view.context)
                    adapter = CategoriesAdapter(it.value)
                    binding.categoriesRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : CategoriesAdapter.OnItemClickListener{
                        override fun onItemClick(position: Long) {
                           val bundle = Bundle()
                            bundle.putLong("id", position)
                            val fragment = TypeClothesFragment()
                            fragment.arguments = bundle
                            FragmentHelper.openRegisterFragment(requireContext(), R.id.fragmentMainMenu, fragment)
                        }

                    })

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getViewModel(): Class<CatalogueViewModel> =
        CatalogueViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCatalogueBinding =
        FragmentCatalogueBinding.inflate(layoutInflater)

    override fun getFragmentRepository(): CatalogueRepository =
        CatalogueRepository(requestBuilder.buildRequest(ApiInterface::class.java))

}