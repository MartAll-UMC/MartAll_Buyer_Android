import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.adapter.HomeAdViewPagerAdapter
import com.org.martall.adapter.HomeMartRVAdapter
import com.org.martall.adapter.ProductSimpleRVAdapter
import com.org.martall.databinding.FragmentHomeBinding
import com.org.martall.models.Response
import com.org.martall.models.ResponseMart
import com.org.martall.services.ApiService
import com.org.martall.view.cart.CartActivity
import com.org.martall.view.home.HomeAdFragment
import com.org.martall.view.home.NewMerchActivity
import com.org.martall.view.search.SearchActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainBinding: MainActivity
    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mainBinding = requireActivity() as MainActivity

        binding.homeMartListTv.visibility = View.GONE
        binding.homeMartMoreTv.visibility = View.GONE

        binding.tbHome.ivCart.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        binding.tbHome.btnAlarm.setOnClickListener {
            val intent = Intent(
                context,
                com.org.martall.view.home.notification.NotificationActivity::class.java
            )
            startActivity(intent)
        }

        // 데이터 로딩 전 스켈레톤 뷰 표시
        showMerchandiseData(isLoading = true)
        showMartData(isLoading = true)

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getRecommendMart().enqueue(object : Callback<ResponseMart> {
                override fun onResponse(
                    call: Call<ResponseMart>,
                    response: retrofit2.Response<ResponseMart>,
                ) {
                    if (response.isSuccessful) {
                        val recommendMartResponse = response.body()
                        recommendMartResponse?.recommendedMarts?.let { recommendedMarts ->
                            val adapter = HomeMartRVAdapter(recommendedMarts)
                            binding.homeRecommendRv.adapter = adapter
                            binding.homeRecommendRv.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }
                        Log.d(
                            "Retrofit",
                            "Recommend mart response received: $recommendMartResponse"
                        )
                    } else {
                        Log.e(
                            "Retrofit",
                            "Failed to get recommend mart. Server error: ${response.code()}"
                        )
                    }
                    showMartData(isLoading = false)
                }

                override fun onFailure(call: Call<ResponseMart>, t: Throwable) {
                    Log.e("Retrofit", "Failed to get recommend mart. Network error: ${t.message}")
                    showMartData(isLoading = false)
                }
            })

            api.getNewItem().enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: RetrofitResponse<Response>,
                ) {
                    if (response.isSuccessful) {
                        val newItemResponse = response.body()
                        newItemResponse?.result?.let { items ->
                            // 리싸이클러뷰 어댑터 생성 및 설정
                            val adapter = ProductSimpleRVAdapter(items.subList(0, 5), api)
                            binding.homeMerchandiseRv.adapter = adapter
                            binding.homeMerchandiseRv.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        }
                        Log.d("Retrofit", "New item response received: $newItemResponse")
                    } else {
                        Log.e(
                            "Retrofit",
                            "Failed to get new item. Server error: ${response.code()}"
                        )
                    }
                    showMerchandiseData(isLoading = false)
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Log.e("Retrofit", "Failed to get new item. Network error: ${t.message}")
                    showMerchandiseData(isLoading = false)
                }
            })
        }

        val adAdapter = HomeAdViewPagerAdapter(this)
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_1_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_2_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_3_360dp))
        adAdapter.addFragment(HomeAdFragment(R.drawable.img_ad_4_360dp))
        binding.homeAdVp.adapter = adAdapter
        binding.homeAdVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeSearchTv.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("isProduct", true)
            startActivity(intent)
        }

        binding.homeMerchandiseMoreTv.setOnClickListener {
            startActivity(Intent(context, NewMerchActivity::class.java))
        }

        binding.homeMartMoreTv.setOnClickListener {
            (binding.root.context as MainActivity).selectBottomNavigationItem(R.id.menu_localMart)
        }

        return binding.root
    }

    /* 스켈레톤 뷰 함수*/
    private fun showMerchandiseData(isLoading: Boolean) {
        if (isLoading) {
            binding.homeShimmerFl.startShimmer()
            binding.homeShimmerFl.visibility = View.VISIBLE
            binding.homeMerchandiseRv.visibility = View.GONE
        } else {
            binding.homeShimmerFl.stopShimmer()
            binding.homeShimmerFl.visibility = View.GONE
            binding.homeMerchandiseRv.visibility = View.VISIBLE
        }
    }

    private fun showMartData(isLoading: Boolean) {
        if (isLoading) {
            binding.homeMartShimmerFl.startShimmer()
            binding.homeMartShimmerFl.visibility = View.VISIBLE
            binding.homeRecommendRv.visibility = View.GONE
        } else {
            binding.homeMartShimmerFl.stopShimmer()
            binding.homeMartShimmerFl.visibility = View.GONE
            binding.homeRecommendRv.visibility = View.VISIBLE
        }
    }
}
