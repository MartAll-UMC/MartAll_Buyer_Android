package com.org.martall.view.mypage.customerservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.org.martall.R
import com.org.martall.View.Likelist.DibsActivity
import com.org.martall.View.Myinfo.MartShopActivity
import com.org.martall.databinding.FragmentMyMartAllBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyMartAllFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyMartAllFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentMyMartAllBinding

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
        binding = FragmentMyMartAllBinding.inflate(inflater, container, false)

        binding.likeGoodsBtn.setOnClickListener {
            val intent = Intent(requireContext(),DibsActivity::class.java)
            startActivity(intent)
        }

        binding.likeGoodsBtn.setOnClickListener{
            val intent = Intent(requireActivity(), DibsActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        binding.privacyPolicyPannel.setOnClickListener {
            val intent = Intent(requireContext(), PrivacyPolicyActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        binding.serviceTermPannel.setOnClickListener {
            val intent = Intent(requireContext(), ServiceTermsActivity::class.java)
            Log.d("intent", "넘어감")
            startActivity(intent)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyMartAllFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyMartAllFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}