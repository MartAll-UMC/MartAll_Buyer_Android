package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.org.martall.R
import com.org.martall.databinding.ItemMartListBinding
import com.org.martall.models.FollowResponseDTO
import com.org.martall.models.MartDataDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartRVAdapter(
    private var martList: List<MartDataDTO>,
    private val onItemClick: (MartDataDTO) -> Unit,
    private var api: ApiService,
) :
    RecyclerView.Adapter<MartRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    init {
        itemClickListener = object : OnItemClickListener {
            override fun onClick(selectedMart: MartDataDTO) {
                onItemClick(selectedMart)
            }
        }

        Log.d("[MART/ADAPTER]", "Initial martList: $martList")
    }

    // setData 함수 추가
    fun setData(newMartList: List<MartDataDTO>) {
        Log.d("[MART/PRINT]", "setData: $newMartList")
        martList = newMartList
        notifyDataSetChanged()
    }

    // 아이템 클릭 리스너
    private interface OnItemClickListener {
        fun onClick(selectedMart: MartDataDTO)
    }

    private fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MartRVAdapter.ViewHolder {
        val binding: ItemMartListBinding =
            ItemMartListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        Log.d("[MART/PRINT]", "MartRVAdapter$martList")
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MartRVAdapter.ViewHolder, position: Int) {
        holder.bind(martList[position])
        holder.itemView.setOnClickListener {
            Log.d("MartRVAdapter", "Item clicked at position: $position")
            val selectedMart = martList[position]
            onItemClick(selectedMart)
        }
    }

    override fun getItemCount(): Int {
        return martList.size
    }

    inner class ViewHolder(val binding: ItemMartListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemClickListener.onClick(martList[adapterPosition])
            }

            binding.bookmarkBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedMart = martList[position]
                    // selectedMart의 martId를 사용하여 서버와 통신
                    handleBookmarkButtonClick(selectedMart.martId, selectedMart.bookmarkYn)
                }
            }
        }

        fun bind(mart: MartDataDTO) {
            // Access views using binding
            binding.martNameTv.text = mart.name
            binding.bookmarkCountTv.text = mart.followersCount.toString()
            binding.likeCountTv.text = mart.likeCount.toString()
            binding.martProfileIv.text = mart.name.toString()
            setCategories(mart.categories)
            Log.d("[MART/PRINT]", "${mart.name} - bookmarkYn: ${mart.bookmarkYn}")

            Log.d("[MART/PRINT]", mart.name + " - 북마크 여부: " + mart.bookmarkYn.toString())
            updateUIAfterFollow(mart.martId, mart.bookmarkYn)

            // martId 저장
            val martId = mart.martId
            // MartPostAdapter 초기화
            val itemAdapter = MartPostAdapter(mart.items, martId, api)
            binding.martItemRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = itemAdapter
            }
        }

        private fun setCategories(categories: List<String>) {
            // Assuming linearLayout is the LinearLayout for categories
            val linearLayout: LinearLayout = binding.martCategoriesLayout
            linearLayout.removeAllViews()

            for (category in categories) {
                val textView = TextView(binding.root.context)
                textView.text = "#$category"
                textView.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textView.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey400))
                textView.setTextAppearance(R.style.sRegular)

                // Add margin to TextViews
                val marginParams = LinearLayout.LayoutParams(textView.layoutParams)
                marginParams.setMargins(
                    binding.root.context.resources.getDimensionPixelSize(R.dimen.margin_right),
                    0,
                    0,
                    0
                )
                textView.layoutParams = marginParams

                linearLayout.addView(textView)
            }
        }

        private fun handleBookmarkButtonClick(martId: Int, isCurrentlyBookmarked: Boolean) {
            val newIsBookmarked = !isCurrentlyBookmarked
            updateUIAfterFollow(martId, newIsBookmarked)

            GlobalScope.launch {
                api = ApiService.createWithHeader(binding.root.context)
                if (newIsBookmarked) {
                    // 팔로우 요청
                    api.followMart(martId).enqueue(object: Callback<FollowResponseDTO> {
                        override fun onResponse(
                            call: Call<FollowResponseDTO>,
                            response: Response<FollowResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 성공: ${response.body()}")
                            } else {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 실패: ${response.code()}, ${response.message()}, ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                            Log.d("[PRINT/MART]", "취소 통신 실패: ${t.message}")
                        }
                    })
                } else {
                    // 팔로우 취소 요청
                    api.unfollowMart(martId).enqueue(object: Callback<FollowResponseDTO> {
                        override fun onResponse(
                            call: Call<FollowResponseDTO>,
                            response: Response<FollowResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 성공: ${response.body()}")
                            } else {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 실패: ${response.code()}, ${response.message()}, ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                            Log.d("[PRINT/MART]", "통신 실패: ${t.message}")
                        }
                    })
                }
            }
        }

        private fun updateUIAfterFollow(martId: Int, isBookmarked: Boolean) {
            // 해당 아이템의 위치 찾기
            val position = martList.indexOfFirst { it.martId == martId }

            // 아이템 업데이트 및 UI 갱신
            if (position != -1) {
                // 여기서 UI를 변경하거나 필요한 작업을 수행
                if (isBookmarked) {
                    // 팔로우한 경우
                    binding.bookmarkBtn.text = "단골 취소"
                    binding.bookmarkBtn.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.primary400
                        )
                    )
                    binding.bookmarkBtn.setBackgroundResource(R.drawable.background_white_r20)
                } else {
                    // 팔로우를 취소한 경우
                    binding.bookmarkBtn.text = "단골 추가"
                    binding.bookmarkBtn.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.white
                        )
                    )
                    binding.bookmarkBtn.setBackgroundResource(R.drawable.background_primary400_r20)
                }
            }
        }
    }
}
