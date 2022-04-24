package com.qyl.petmarket.data.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qyl.petmarket.data.bean.PetBean
import com.qyl.petmarket.net.repository.SystemRepository
import com.qyl.petmarket.utils.ToastUtils
import kotlinx.coroutines.launch

class PetVM : NetVM() {

    val petList = MutableLiveData<List<PetBean>>()

    fun getPetInfo() {
        viewModelScope.launch {
            fastRequest<List<PetBean>> {
                SystemRepository.findPetRequest()
            }?.let {
                petList.postValue(it)
            }
        }
    }

    fun deletePet(id: Int) {
        viewModelScope.launch {
            fastRequest<Boolean> {
                SystemRepository.deletePetRequest(id)
            }?.let {
                val newList = petList.value?.filter { it.id != id } ?: emptyList()
                petList.postValue(newList)
                ToastUtils.toastShort("删除成功")
            }
        }
    }

    fun updatePet(id: Int) {
        viewModelScope.launch {
            fastRequest<Boolean> {
                SystemRepository.deletePetRequest(id)
            }?.let {
                ToastUtils.toastShort("删除成功")
            }
        }
    }
}