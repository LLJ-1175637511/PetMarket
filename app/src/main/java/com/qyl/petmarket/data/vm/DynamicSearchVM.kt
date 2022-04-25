package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.fragment.DynamicFragment
import kotlinx.coroutines.launch

class DynamicSearchVM : NetVM() {

    val bigUrl = MutableLiveData<String>()

    val searchList = MutableLiveData<List<DynamicBean>>()

    fun querySearchData(content: String) {
        viewModelScope.launch {
            fastRequest<List<DynamicBean>> {
                SystemRepository.findDynamicRequest(
                    SysNetConfig.buildQueryDynamicMap(null, null, content)
                )
            }?.let {
                searchList.postValue(it)
            }
        }
    }

}