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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocalStoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocalStoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLocalStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocalStoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LocalStoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
