package com.org.martall.view.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.org.martall.databinding.ActivitySearchBinding
import com.org.martall.utils.ListToDataStoreUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "pref"
)

class SearchActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        init(intent.getBooleanExtra("isProductSearch", true))

        setContentView(binding.root)
    }

    private fun init(isProductSearch: Boolean) {
        initFrame(isProductSearch)
        binding.searchToolbar.toolbarBackBtn.setOnClickListener {
            finish()
        }
        binding.searchBar.searchBarEt.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                GlobalScope.launch {
                    search(isProductSearch)
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun initFrame(isProductSearch: Boolean) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()

        binding.searchToolbar.toolbarTitleTv.text = if (isProductSearch) "상품 검색" else "마트 검색"
        val searchBeforeFragment = SearchBeforeFragment(isProductSearch = isProductSearch)
        fragmentTransaction.replace(binding.searchContentFl.id, searchBeforeFragment)
        fragmentTransaction.commit()
    }

    suspend fun search(isProductSearch: Boolean) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()

        saveKeyword(isProductSearch, binding.searchBar.searchBarEt.text.toString())

        val resultFragment =
            SearchResultFragment(isProductSearch, binding.searchBar.searchBarEt.text.toString())
        fragmentTransaction.replace(binding.searchContentFl.id, resultFragment)
        fragmentTransaction.commit()
    }

    private suspend fun saveKeyword(isProductSearch: Boolean, keyword: String) {
        val dataStore = applicationContext.dataStore
        val listToDataStoreUtil = ListToDataStoreUtil()

        if (isProductSearch) {
            var keywords: MutableList<String> =
                (listToDataStoreUtil.getList(dataStore, "recentProductKeywords")).toMutableList()
            keywords.remove(keyword)
            keywords.add(0, keyword)
            listToDataStoreUtil.saveList(dataStore, "recentProductKeywords", keywords.toList())
        } else {
            var keywords: MutableList<String> =
                (listToDataStoreUtil.getList(dataStore, "recentMartKeywords")).toMutableList()
            keywords.remove(keyword)
            keywords.add(0, keyword)
            listToDataStoreUtil.saveList(dataStore, "recentMartKeywords", keywords.toList())
        }

    }
}