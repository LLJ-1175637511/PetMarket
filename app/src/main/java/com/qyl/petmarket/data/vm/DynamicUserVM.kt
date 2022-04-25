package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.data.bean.DynamicBean
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.ui.fragment.DynamicFragment
import kotlinx.coroutines.launch

class DynamicUserVM : NetVM() {

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

    fun likeDynamic(id: Int, block: () -> Unit) {
        viewModelScope.launch {
            fastRequest<Boolean> {
                SystemRepository.likeDynamicRequest(
                    SysNetConfig.buildLikeDynamic(id)
                )
            }?.let {
                block()
            }
        }
    }

    fun deleteDynamic(id: Int, block: () -> Unit) {
        viewModelScope.launch {
            fastRequest<Boolean> {
                SystemRepository.deleteDynamicRequest(
                    id
                )
            }?.let {
                block()
            }
        }
    }

    fun queryUserDynamic(author: String? = null) {
        viewModelScope.launch {
            fastRequest<List<DynamicBean>> {
                SystemRepository.findDynamicRequest(
                    SysNetConfig.buildUserDynamic(author)
                )
            }?.let {
                searchList.postValue(it)
            }
        }
    }

}