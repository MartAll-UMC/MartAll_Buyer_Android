package com.org.martall.view.store.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.org.martall.R
import com.org.martall.databinding.FragmentShopUserBinding
import com.org.martall.view.store.user.bottomsheet.FilterBottomSheet

class ShopUserFragment : Fragment() {

    private lateinit var binding : FragmentShopUserBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentShopUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        initRecyclerView()
        setSpinner()
        with(binding) {
            tvFilter.setOnClickListener {
                showBottomSheet()
            }
        }
//        (binding.rvUserList.adapter as? UserAdapter)?.submitList(dummyData)
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_list,
            android.R.layout.simple_spinner_item,
            ).also { adapter->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerOrder.adapter=adapter
        }
    }

    private fun initToolBar() {
        with(binding) {
            tbShop.apply {
                ivBack.setImageResource(R.drawable.baseline_arrow_back_ios_24)
                tvTitle.text="동네샵"
                ivSearch.setImageResource(R.drawable.baseline_format_list_bulleted_24)
            }
        }
    }

    private fun showBottomSheet() {
        FilterBottomSheet().show(
            childFragmentManager,
            null
        )
    }

    private fun initRecyclerView() {
//        with(binding) {
//            rvUserList.apply {
//                layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
//                adapter=UserAdapter()
//            }
//        }
    }
}