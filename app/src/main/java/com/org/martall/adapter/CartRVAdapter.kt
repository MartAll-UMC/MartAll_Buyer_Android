package com.org.martall.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.org.martall.R
import com.org.martall.databinding.ItemCartProductBinding
import com.org.martall.interfaces.CartApiInterface
import com.org.martall.models.CartProduct
import com.org.martall.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRVAdapter(private var products: List<CartProduct>, private val api: ApiService) :
    RecyclerView.Adapter<CartRVAdapter.ViewHolder>() {
    var selected = mutableListOf<CartProduct>()
    lateinit var totalPriceUpdateListener: () -> Unit
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): CartRVAdapter.ViewHolder {
        val binding: ItemCartProductBinding = ItemCartProductBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartRVAdapter.ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun getSelectedProducts(): List<CartProduct> {
        return selected
    }

    fun getProducts(): List<CartProduct> {
        return products
    }

    fun selectAll() {
        selected = products.toMutableList()
        updateSelectedProducts()
        notifyDataSetChanged()
    }

    fun deleteSelected() {
        for(product in selected) {
            deleteItem(product)
        }
        updateSelectedProducts()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemCartProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val idx = adapterPosition
            }

            binding.selectProductBtn.setOnClickListener {
                val idx = adapterPosition
                if (selected.contains(products[idx])) {
                    selected.remove(products[idx])
                } else {
                    selected.add(products[idx])
                }
                updateSelectedProducts()
                notifyDataSetChanged()
            }

            binding.deleteProductBtn.setOnClickListener {
                val idx = adapterPosition
                deleteItem(products[idx])
            }
        }

        fun bind(product: CartProduct) {
            binding.productCategoryTv.text = product.category
            binding.productNameTv.text = product.name
            binding.productPriceTv.text = "${product.price}원"
            Glide.with(itemView.context).load(product.img).into(binding.productImgIv)
            if (product in selected) {
                binding.selectProductBtn.setImageResource(R.drawable.ic_check_filled_16dp)
            } else {
                binding.selectProductBtn.setImageResource(R.drawable.ic_check_unfilled_16dp)
            }
        }
    }

    fun deleteItem(product: CartProduct) {
        GlobalScope.launch {
            api.deleteItemFromCart(
                CartApiInterface.DeleteBody(
                    listOf(CartApiInterface.DeleteItem(product.cartId)),
                )
            ).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (product in selected) {
                        selected.remove(product)
                    }
                    products = products.toMutableList().apply { remove(product) }
                    updateSelectedProducts()
                    notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.e("[ERROR/CartRVAdapter]", "API 에러 ${t.message}")
                }
            })
        }
    }

    private fun updateSelectedProducts() {
        selected = products.filter { it in selected }.toMutableList()
        totalPriceUpdateListener.invoke()
    }
}