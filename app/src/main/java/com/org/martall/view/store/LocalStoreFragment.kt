package com.org.martall.view.store

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.martall.R
import com.org.martall.adapter.MartRVAdapter
import com.org.martall.databinding.FragmentLocalStoreBinding
import com.org.martall.model.UserDTO
import com.org.martall.view.store.user.bottomsheet.FilterBottomSheet
import com.org.martall.view.store.user.bottomsheet.SortBottomSheet

class LocalStoreFragment : Fragment() {
    private lateinit var binding: FragmentLocalStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLocalStoreBinding.inflate(inflater, container, false)

        // initToolBar()
        initRecyclerView()
        showFilterBottomSheet()
        showSortBottomSheet()

        val martRVAdapter = MartRVAdapter(dummyData)

        binding.groupRecyclerView.setOnClickListener {
          val martDetailInfoFragment = MartDetailInfoFragment()

            val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()

            transaction?.replace(R.id.main_container, martDetailInfoFragment)
                ?.commitAllowingStateLoss()

            Log.d("intent", "넘어감")
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

    val dummyData: List<UserDTO> = listOf(
        UserDTO(
            imageUrl = R.drawable.img_item_banana_360dp, "회원1", "#aa#bb",
            12, 120, com.org.martall.model.dummyPosts
        ),
        UserDTO(
            imageUrl = R.drawable.iv_spam, "회원2", "#aa#bb",
            12, 120, com.org.martall.model.dummyPosts
        ),
        UserDTO(
            imageUrl = R.drawable.img_item_banana_360dp, "회원3", "#aa#bb",
            12, 120, com.org.martall.model.dummyPosts
        ),
        UserDTO(
            imageUrl = R.drawable.iv_spam, "회원4", "#aa#bb",
            12, 120, com.org.martall.model.dummyPosts
        ),
    )
}
