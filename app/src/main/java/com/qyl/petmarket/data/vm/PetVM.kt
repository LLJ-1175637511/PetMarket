package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.net.config.SysNetConfig
import com.qyl.petmarket.net.repository.SystemRepository
import kotlinx.coroutines.launch

class PetVM : NetVM() {

    val petList = MutableLiveData<List<PetBean>>()

    fun getPetInfo(author: String? = null) {
        viewModelScope.launch {
            fastRequest<List<PetBean>> {
                if (author == null){
                    SystemRepository.findPetRequest(SysNetConfig.getUserName())
                }else{
                    SystemRepository.findPetRequest(author)
                }
            }?.let {
                petList.postValue(it)
            }
        }
    }

    fun deletePet(id: Int,block:()->Unit) {
        viewModelScope.launch {
            fastRequest<Boolean> {
                SystemRepository.deletePetRequest(id)
            }?.let {
                block()
            }
        }
    }

}