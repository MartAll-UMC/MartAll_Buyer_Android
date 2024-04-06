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
    private var isBookmarked: Boolean = false

    init {
        itemClickListener = object : OnItemClickListener {
            override fun onClick(selectedMart: MartDataDTO) {
                onItemClick(selectedMart)
            }
        }
    }

    // setData 함수 추가
    fun setData(newMartList: List<MartDataDTO>) {
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
        private var isBookmarked: Boolean = false

        init {
            binding.root.setOnClickListener {
                itemClickListener.onClick(martList[adapterPosition])
            }

            binding.bookmarkBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedMart = martList[position]
                    // selectedMart의 martId를 사용하여 서버와 통신
                    handleBookmarkButtonClick(selectedMart.martId)
//                    handleBookmarkButtonClick(selectedMart.martId, selectedMart.isFollowed)
//                    Log.d("follow", selectedMart.isBookmarked.toString())
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

            isBookmarked = mart.bookmarkYn
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

        private fun handleBookmarkButtonClick(martId: Int) {

            isBookmarked = !isBookmarked
            updateUIAfterFollow(martId, isBookmarked)

            GlobalScope.launch {
                api = ApiService.createWithHeader(binding.root.context)
                if (isBookmarked) {
                    // 이미 팔로우한 경우 팔로우 취소 요청
                    api.unfollowMart(martId).enqueue(object: Callback<FollowResponseDTO> {
                        override fun onResponse(
                            call: Call<FollowResponseDTO>,
                            response: Response<FollowResponseDTO>,
                        ) {
                            if (response.isSuccessful) {
                                Log.d("[PRINT/MART]", "마트 팔로우 취소 성공")

                            } else {
                                // 서버 오류 발생
                                // Handle server error
                            }
                        }

                        override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
                            // 통신 실패
                            // Handle failure
                            Log.d("check", "마트 팔로우 실패")
                        }
                    })
                }

//                call.enqueue(object : Callback<FollowResponseDTO> {
//                    override fun onResponse(
//                        call: Call<FollowResponseDTO>,
//                        response: Response<FollowResponseDTO>,
//                    ) {
//                        if (response.isSuccessful) {
////                        val followResponse = response.body()
////                        if (followResponse != null) {
////                            // 성공적으로 팔로우/팔로우 취소 시 + isFollowed 값 업데이트
////                            val position = martList.indexOfFirst { it.martId == martId }
////                            if (position != -1) {
////                                isFollowed = !isFollowed
////                                updateUIAfterFollow(martId, isFollowed)
////                            }
////                        } else {
////                            // 서버 응답이 유효하지 않음
////                            // Handle accordingly
////                        }
//                        } else {
//                            // 서버 오류 발생
//                            // Handle server error
//                        }
//                    }
//
//                    override fun onFailure(call: Call<FollowResponseDTO>, t: Throwable) {
//                        // 통신 실패
//                        // Handle failure
//                        Log.d("check", "마트 팔로우 실패")
//                    }
//                })
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
                // notifyItemChanged(position)
            }
        }
    }
}
