import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.org.martall.BuildConfig
import com.org.martall.MainActivity
import com.org.martall.R
import com.org.martall.adapter.HomeAdViewPagerAdapter
import com.org.martall.adapter.HomeMartRVAdapter
import com.org.martall.adapter.ProductSimpleRVAdapter
import com.org.martall.databinding.FragmentHomeBinding
import com.org.martall.interfaces.MartItemService
import com.org.martall.interfaces.MartItemdibs
import com.org.martall.interfaces.MartRecommendService
import com.org.martall.model.ResponseMart
import com.org.martall.model.RecommendedMart
import com.org.martall.model.Response
import com.org.martall.view.home.HomeAdFragment
import com.org.martall.view.home.NewMerchActivity
import com.org.martall.view.search.SearchActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainBinding: MainActivity
    private lateinit var martItemdibs: MartItemdibs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mainBinding = requireActivity() as MainActivity

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

        // Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("${BuildConfig.MOCK_ITEM_URL}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofit2 = Retrofit.Builder()
            .baseUrl("${BuildConfig.MOCK_MART_URL}")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Retrofit 인터페이스 생성
        martItemdibs = retrofit.create(MartItemdibs::class.java)
        val service = retrofit.create(MartItemService::class.java)
        val service2 = retrofit2.create(MartRecommendService::class.java)
        val call = service.getNewItem()
        val callMart = service2.getRecommendMart()

        callMart.enqueue(object : Callback<ResponseMart> {
            override fun onResponse(call: Call<ResponseMart>, response: retrofit2.Response<ResponseMart>) {
                if (response.isSuccessful) {
                    val recommendMartResponse = response.body()
                    recommendMartResponse?.recommendedMarts?.let { recommendedMarts ->
                        val adapter = HomeMartRVAdapter(recommendedMarts)
                        binding.homeRecommendRv.adapter = adapter
                        binding.homeRecommendRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                    Log.d("Retrofit", "Recommend mart response received: $recommendMartResponse")
                } else {
                    Log.e("Retrofit", "Failed to get recommend mart. Server error: ${response.code()}")
                }
                showMartData(isLoading = false)
            }

            override fun onFailure(call: Call<ResponseMart>, t: Throwable) {
                Log.e("Retrofit", "Failed to get recommend mart. Network error: ${t.message}")
                showMartData(isLoading = false)
            }
        })

// Retrofit 호출 실행
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: RetrofitResponse<Response>) {
                if (response.isSuccessful) {
                    val newItemResponse = response.body()
                    newItemResponse?.data?.let { items ->
                        // 리싸이클러뷰 어댑터 생성 및 설정
                        val adapter = ProductSimpleRVAdapter(items, martItemdibs)
                        binding.homeMerchandiseRv.adapter = adapter
                        binding.homeMerchandiseRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }
                    Log.d("Retrofit", "New item response received: $newItemResponse")
                } else {
                    Log.e("Retrofit", "Failed to get new item. Server error: ${response.code()}")
                }
                showMerchandiseData(isLoading = false)
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("Retrofit", "Failed to get new item. Network error: ${t.message}")
                showMerchandiseData(isLoading = false)
            }
        })

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
