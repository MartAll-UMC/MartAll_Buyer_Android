package com.org.martall.model

import com.org.martall.R

data class MartDTO(
    val imageUrl : Int? = null,
    val name: String = "",
    val hashTag: String = "",
    val followerCount: Int? = null,
    val visitorCount: Int? = null,
    val post : List<ItemSimpleDTO>
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
        imageUrl = R.drawable.img_item_banana_360dp, "회원1", "#aa#bb",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.iv_spam, "회원2", "#aa#bb",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.img_item_banana_360dp, "회원3", "#aa#bb",
        1, 2, dummyPosts
    ),
    MartDTO(
        imageUrl = R.drawable.iv_spam, "회원4", "#aa#bb",
        1, 2, dummyPosts
    ),
)