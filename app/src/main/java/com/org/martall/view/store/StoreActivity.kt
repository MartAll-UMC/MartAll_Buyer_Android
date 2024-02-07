package com.org.martall.view.store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.martall.R
import com.org.martall.databinding.ActivityStoreBinding
import com.org.martall.view.store.user.ShopUserFragment

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_fragment, ShopUserFragment())
            .commit()

    }

}