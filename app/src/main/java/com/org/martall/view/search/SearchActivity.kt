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

        initFrame()

        binding.searchToolbar.toolbarBackBtn.setOnClickListener {
            finish()
        }

        binding.searchBar.searchBarEt.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                GlobalScope.launch {
                    search()
                }
                return@setOnEditorActionListener true
            }
            false
        }

        setContentView(binding.root)
    }

    private fun initFrame() {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()

        val searchBeforeFragment = SearchBeforeFragment(isProduct = true)
        fragmentTransaction.replace(binding.searchContentFl.id, searchBeforeFragment)
        fragmentTransaction.commit()
    }

    suspend fun search() {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()

        saveKeyword(binding.searchBar.searchBarEt.text.toString())

        val resultFragment =
            SearchResultFragment(true, binding.searchBar.searchBarEt.text.toString())
        fragmentTransaction.replace(binding.searchContentFl.id, resultFragment)
        fragmentTransaction.commit()
    }

    private suspend fun saveKeyword(keyword: String) {
        val dataStore = applicationContext.dataStore
        val listToDataStoreUtil = ListToDataStoreUtil()

        var keywords: MutableList<String> =
            (listToDataStoreUtil.getList(dataStore, "recentKeywords")).toMutableList()
        keywords.remove(keyword)
        keywords.add(0, keyword)
        listToDataStoreUtil.saveList(dataStore, "recentKeywords", keywords.toList())
    }
}