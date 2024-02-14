package com.org.martall.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.org.martall.model.MartDataDTO

class SharedMartViewModel : ViewModel() {
    private var martList: MutableLiveData<List<MartDataDTO>> = MutableLiveData()
    private var selectedMart: MutableLiveData<MartDataDTO> = MutableLiveData()


    fun setMartList(list: List<MartDataDTO>) {
        martList.value = list
    }

    fun getMartList(): MutableLiveData<List<MartDataDTO>> {
        return martList
    }

    fun setSelectedMart(mart: MartDataDTO) {
        selectedMart.postValue(mart)
    }


    fun getSelectedMart(): LiveData<MartDataDTO> {
        return selectedMart
    }
}