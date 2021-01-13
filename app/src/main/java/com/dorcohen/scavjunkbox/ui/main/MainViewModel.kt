package com.dorcohen.scavjunkbox.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.repository.IRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository:IRepository) : ViewModel() {
    val appList:LiveData<List<AppInfo>> = repository.appList

    val placeHolderVisibility: LiveData<Boolean> = Transformations.map(appList){
        val res = it.isEmpty()
        res
    }

    fun toggleApp(appInfo: AppInfo) = viewModelScope.launch {
        repository.toggleApp(appInfo)
    }

    fun navigateToTestFragment(view: View){
        view.findNavController().navigate(R.id.testFragment)
    }

    fun navigateToAddAppFragment(view:View){
        view.findNavController().navigate(R.id.appPickerFragment)
    }
}