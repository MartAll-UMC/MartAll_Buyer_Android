package com.org.martall.view.store

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.martall.adapter.MartRVAdapter
import com.org.martall.databinding.FragmentLocalStoreBinding
import com.org.martall.model.dummyData
import com.org.martall.view.search.SearchActivity
import com.org.martall.view.store.user.bottomsheet.FilterBottomSheet
import com.org.martall.view.store.user.bottomsheet.SortBottomSheet

class LocalStoreFragment : Fragment() {
    private lateinit var binding: FragmentLocalStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocalStoreBinding.inflate(inflater, container, false)

        initRecyclerView()
        showFilterBottomSheet()
        showSortBottomSheet()

        binding.tbShop.ivSearch.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isProductSearch", false)
            startActivity(intent)
        }

        val martRVAdapter = MartRVAdapter(dummyData)
        binding.groupRecyclerView.adapter = martRVAdapter

        binding.groupRecyclerView.setOnClickListener {
//            startActivity(this.intent, MartDetailInfoActivity::class.java)
//          val martDetailInfoFragment = MartDetailInfoFragment()
//
//            val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()
//
//            transaction?.replace(R.id.main_container, martDetail)
//                ?.commitAllowingStateLoss()
//
//            Log.d("intent", "넘어감")
        }

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            groupRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = MartRVAdapter(dummyData)
            }
        }
    }

    private fun showFilterBottomSheet() {
        binding.filterTv.setOnClickListener {
            FilterBottomSheet().show(
                childFragmentManager,
                null
            )
        }
    }

    private fun showSortBottomSheet() {
        binding.sortTv.setOnClickListener {
            SortBottomSheet().show(
                childFragmentManager,
                null
            )
        }
    }
}
