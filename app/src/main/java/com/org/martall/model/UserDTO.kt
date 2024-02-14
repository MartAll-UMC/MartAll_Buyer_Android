package com.org.martall.model

import com.google.gson.annotations.SerializedName
import com.org.martall.R

data class RecommendedMart(
    val martId: Int,
    val name: String,
    val photo: String,
    val martcategory: List<String>,
    val location: String
)
data class ResponseMart(
    val status: Int,
    val message: String,
    val recommendedMarts: List<RecommendedMart>
)

data class MartDTO(
    val imageUrl : Int? = null,
    val name: String = "",
    val hashTag: String = "",
    val followerCount: Int? = null,
    val visitorCount: Int? = null,
    val post : List<ItemSimpleDTO>,
    var isLiked: Boolean = false,
)

data class ItemSimpleDTO(
    val imageUrl : Int = 0,
    val name: String = "",
    val price: Int = 0,
    val isLiked: Boolean = false,
)

val dummyPosts : List<ItemSimpleDTO> = listOf(
    ItemSimpleDTO(R.drawable.img_item_banana_360dp, "바나나", 5000, false),
    ItemSimpleDTO(R.drawable.iv_spam, "스팸", 4500, false),
    ItemSimpleDTO(R.drawable.img_item_banana_360dp, "바나나", 5000, false),
    ItemSimpleDTO(R.drawable.iv_spam, "스팸", 4500, false),
    ItemSimpleDTO(R.drawable.img_item_banana_360dp, "바나나", 5000, false),
)


val dummyData : List<MartDTO> = listOf(
    MartDTO(
        imageUrl = R.drawable.img_item_banana_360dp, "오렌지마트", "#정육",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.iv_spam, "하이마트", "#수산",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.img_item_banana_360dp, "맘마농가식자재", "#식품",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.iv_spam, "숲속마트", "#생활용품",
        1, 2, dummyPosts
    ),
)