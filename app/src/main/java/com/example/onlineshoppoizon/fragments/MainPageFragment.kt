package com.example.onlineshoppoizon.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.onlineshoppoizon.ItemDetailsActivity
import com.example.onlineshoppoizon.adapters.ClothesAdapter
import com.example.onlineshoppoizon.databinding.FragmentMainPageBinding
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.viewmodel.MainPageViewModel

class MainPageFragment : BaseFragment<MainPageViewModel, FragmentMainPageBinding,MainPageRepository >() {
    private lateinit var adapter : ClothesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getClothes()

        viewModel.clothesResponse.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Success -> {
                    binding.itemsRecycler.layoutManager = GridLayoutManager(view.context, 2)
                    adapter = ClothesAdapter(it.value)
                    binding.itemsRecycler.adapter = adapter
                    adapter.setOnItemClickListener(object: ClothesAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireContext(),ItemDetailsActivity::class.java)
                            intent.putExtra("id",it.value[position].idClothes)
                            startActivity(intent)
                        }

                    })
                    Toast.makeText(requireContext(), it.value.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        } )



    }

    override fun getViewModel(): Class<MainPageViewModel>
        = MainPageViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainPageBinding =
        FragmentMainPageBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): MainPageRepository {
        val api = requestBuilder.buildRequest(ApiInterface::class.java)
        return MainPageRepository(api)
    }
}