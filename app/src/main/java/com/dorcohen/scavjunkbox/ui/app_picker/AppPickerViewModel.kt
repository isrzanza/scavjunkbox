package com.dorcohen.scavjunkbox.ui.app_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorcohen.scavjunkbox.app_picker.AppPicker
import com.dorcohen.scavjunkbox.app_picker.IAppPicker
import com.dorcohen.scavjunkbox.data.model.AppInfo
import com.dorcohen.scavjunkbox.data.repository.IRepository
import kotlinx.coroutines.launch

class AppPickerViewModel(private val repository: IRepository) : ViewModel(),IAppPicker by AppPicker {
    fun addApp(appInfo:AppInfo) = viewModelScope.launch {
        repository.addNewApp(appInfo)
    }
}