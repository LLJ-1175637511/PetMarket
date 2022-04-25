package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.fragment.DynamicFragment
import kotlinx.coroutines.launch

class DynamicSquareVM : NetVM() {

    val freshState = MutableLiveData<Boolean>()

    val bigUrl = MutableLiveData<String>()

    val normalList = MutableLiveData<List<DynamicBean>>()
    val scienceList = MutableLiveData<List<DynamicBean>>()
    val goodThingList = MutableLiveData<List<DynamicBean>>()

    val searchList = MutableLiveData<List<DynamicBean>>()

    fun queryData(type: DynamicFragment.DynamicType) {
        viewModelScope.launch {
            fastRequest<List<DynamicBean>> {
                SystemRepository.findDynamicRequest(
                    SysNetConfig.buildQueryDynamicMap(null, type.name,null)
                )
            }?.let {
                when (type) {
                    DynamicFragment.DynamicType.日常 -> normalList.postValue(it)
                    DynamicFragment.DynamicType.好物 -> goodThingList.postValue(it)
                    DynamicFragment.DynamicType.科普 -> scienceList.postValue(it)
                }
            }
            freshState.postValue(true)
        }
    }

    fun querySearchData(content:String) {
        viewModelScope.launch {
            fastRequest<List<DynamicBean>> {
                SystemRepository.findDynamicRequest(
                    SysNetConfig.buildQueryDynamicMap(null, null,content)
                )
            }?.let {
                searchList.postValue(it)
            }
        }
    }

}