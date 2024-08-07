package com.dorcohen.scavjunkbox.ui.notifications

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.dorcohen.scavjunkbox.R
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.repository.IRepository
import kotlinx.coroutines.launch

class NotificationsFragmentViewModel(private val repository:IRepository) : ViewModel() {
    val appList:LiveData<List<AppInfo>> = repository.appList

    val appListEmpty: LiveData<Boolean> = appList.map{
        val res = it.isEmpty()
        res
    }

    fun toggleApp(appInfo: AppInfo) = viewModelScope.launch {
        repository.toggleApp(appInfo)
    }

    fun navigateToAddAppFragment(view:View){
        view.findNavController().navigate(R.id.action_notificationsFragment_to_appPickerFragment)
    }
}