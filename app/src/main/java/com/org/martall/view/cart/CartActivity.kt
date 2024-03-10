package com.org.martall.view.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.org.martall.adapter.CartRVAdapter
import com.org.martall.databinding.ActivityCartBinding
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.models.CartProduct
import com.org.martall.models.CartResponseDTO
import com.org.martall.models.MartCategory
import com.org.martall.models.OrderRequestDTO
import com.org.martall.models.OrderResponseDTO
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        binding.toolbarBackBtn.setOnClickListener {
            finish()
        }

        binding.martLayout.bookmarkBtn.visibility = View.GONE

        GlobalScope.launch {
            api = ApiService.createWithHeader(applicationContext)

            api.getCartList().enqueue(object : Callback<CartResponseDTO> {
                override fun onResponse(
                    call: Call<CartResponseDTO>,
                    response: Response<CartResponseDTO>,
                ) {
                    if (response.isSuccessful) {
                        val cartRes = response.body()
                        val products = cartRes?.result!!.products
                        val mart = cartRes.result.mart

                        if (products.isEmpty()) {
                            binding.apply {
                                cartEmptyLl.visibility = View.VISIBLE
                                cartSv.visibility = View.GONE
                                cartOrderCl.visibility = View.GONE
                            }
                        } else {
                            binding.apply {
                                cartEmptyLl.visibility = View.GONE
                                cartSv.visibility = View.VISIBLE
                                cartOrderCl.visibility = View.VISIBLE

                                martLayout.martNameTv.text = mart.name
                                makeHashTag(mart.category!!)
                                martLayout.followerCountTv.text = mart.bookmarkCnt.toString()
                                martLayout.dibsCountTv.text = mart.likeCnt.toString()

                                val adapter = CartRVAdapter(products, api)
                                adapter.totalPriceUpdateListener = {
                                    updateTotalPrice(
                                        adapter.getSelectedProducts(),
                                        adapter.getProducts()
                                    )
                                }
                                cartRv.adapter = adapter

                                productCntTv.text = adapter.getSelectedProducts().size.toString()
                                productCntSuffixTv.text = "개 / ${products.size}개"
                                totalPriceTv.text =
                                    "${adapter.getSelectedProducts().sumOf { it.price }}원"

                                binding.selectAllBtn.setOnClickListener { adapter.selectAll() }
                                binding.deleteSelectedBtn.setOnClickListener { adapter.deleteSelected() }

                                binding.orderBtn.setOnClickListener {
                                    order(mart.id!!, adapter.getSelectedProducts())
                                }
                            }
                        }
                    } else {
                        Log.e("[ERROR/CartActivity]", "장바구니 조회 API 에러")
                    }
                }

                override fun onFailure(call: Call<CartResponseDTO>, t: Throwable) {
                    Log.e("[ERROR/CartActivity]", "API 에러 ${t.message}")
                }
            })
        }
    }

    fun makeHashTag(category: List<MartCategory>) {
        var hashtag = ""
        for (i in category.indices) {
            hashtag += "#${category[i].name} "
        }
        binding.apply {
            martLayout.martHashtagTv1.text = hashtag
            martLayout.martHashtagTv2.text = ""
            martLayout.martHashtagTv3.text = ""
        }
    }

    private fun updateTotalPrice(selectedProducts: List<CartProduct>, products: List<CartProduct>) {
        binding.productCntTv.text = selectedProducts.size.toString()
        binding.productCntSuffixTv.text = "개 / ${products.size}개"
        binding.totalPriceTv.text =
            "${selectedProducts.sumOf { it.price }}원"
    }

    private fun order(martId: Int, products: List<CartProduct>) {
        GlobalScope.launch {
            api.orderProduct(
                OrderRequestDTO(
                    totalPrice = binding.totalPriceTv.text.toString().split("원")[0].toInt(),
                    martId = martId,
                    carts = products.map { CartApiInterface.DeleteItem(it.cartId) }.toList()
                )
            )
                .enqueue(object : Callback<OrderResponseDTO> {
                    override fun onResponse(
                        call: Call<OrderResponseDTO>,
                        response: Response<OrderResponseDTO>,
                    ) {
                        Log.d("[SUCCESS/CartActivity]", "주문 성공")
                        Toast.makeText(
                            applicationContext,
                            "주문 되었습니다. 알림창을 확인해보세요!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        finish()
                    }

                    override fun onFailure(call: Call<OrderResponseDTO>, t: Throwable) {
                        Log.e("[ERROR/CartActivity]", "API 에러 ${t.message}")
                    }
                })
        }
    }
}