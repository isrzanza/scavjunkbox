package com.dorcohen.scavjunkbox.ui

import androidx.lifecycle.ViewModel
import com.dorcohen.scavjunkbox.data.repository.IRepository

class MainViewModel(repository:IRepository) : ViewModel() {
    val systemMessage = repository.systemUtil.getSystemMessage()
}