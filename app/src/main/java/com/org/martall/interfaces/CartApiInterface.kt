package com.org.martall.interfaces

import com.google.gson.annotations.SerializedName
import com.org.martall.models.CartResponseDTO
import com.org.martall.models.MartListResponseDTO
import com.org.martall.models.ProductDetailResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CartApiInterface {
    @GET("/item/{shopid}/{itemid}")
    fun getProductDetail(
        @Path("shopid") shopId: Int, @Path("itemid") itemId: Int,
    )
            : Call<ProductDetailResponseDTO>

    @GET("/mart/shops/search/filter")
    fun ShowAllShops(
        @Query("tag") tag: String?,
        @Query("minBookmark") minBookmark: Int?,
        @Query("maxBookmark") maxBookmark: Int?,
        @Query("minLike") minLike: Int?,
        @Query("maxLike") maxLike: Int?,
        @Query("sort") sort: String?,
    ): Call<MartListResponseDTO>

    @POST("/user/cart")
    fun addProductToCart(
        @Body body: AddToCartBody,
    ): Call<Unit>

    @GET("/user/cart")
    fun getCartList(): Call<CartResponseDTO>

    @HTTP(method = "DELETE", path = "/user/cart", hasBody = true)
    fun deleteItemFromCart(
        @Body body: DeleteBody,
    ): Call<Unit>

    data class AddToCartBody(
        @SerializedName("itemId") val itemId: Int,
        @SerializedName("count") val count: Int = 1,
    )

    data class DeleteBody(
        val cartItemList: List<DeleteItem>,
    )

    data class DeleteItem(
        val cartItemId: Int,
    )
}