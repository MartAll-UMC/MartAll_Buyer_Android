package com.org.martall.model

import android.graphics.PostProcessor
import com.org.martall.R

data class UserDTO(
    val imageUrl : Int? = null,
    val name: String = "",
    val hashTag: String = "",
    val followerCount: Int? = null,
    val visitorCount: Int? = null,
    val post : List<Post>
)

data class Post(
    val imageUrl: Int,
)

val dummyPosts : List<Post> = listOf(
    Post(R.drawable.banana),
    Post(R.drawable.iv_spam),
    Post(R.drawable.banana),
    Post(R.drawable.iv_spam),
    Post(R.drawable.banana),
)


val dummyData : List<UserDTO> = listOf(
    UserDTO(
        imageUrl = R.drawable.banana, "회원1", "#aa#bb",
        1, 2, dummyPosts
    ),
    UserDTO(
        imageUrl = R.drawable.iv_spam, "회원2", "#aa#bb",
        1, 2, dummyPosts
    ),
    UserDTO(
        imageUrl = R.drawable.banana, "회원3", "#aa#bb",
        1, 2, dummyPosts
    ),
    UserDTO(
        imageUrl = R.drawable.iv_spam, "회원4", "#aa#bb",
        1, 2, dummyPosts
    ),
)